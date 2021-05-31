package controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Adresse;
import model.Client;
import model.Devis;
import model.Vehicule;
import model.enums.TypeBoite;
import model.enums.TypeCarburant;
import model.enums.TypeCategorie;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 * @param <Client>
 *
 */
public class LocationDAO {
	private DataAccess instance;

	public LocationDAO() {
		// obtenir les accès à la BDD
		instance = DataAccess.getInstance();
	}

	public List<Client> getClientLocationEnCours() throws SQLException {
		Statement stmt = instance.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(
				"select * from Reservation natural join Client natural join Adresse where NOW() between debut_location and date_add(debut_location,INTERVAL duree_location day);");

		ArrayList<Client> result = new ArrayList<>(rs.getFetchSize());
		while (rs.next()) {
			Client client = new Client(rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),
					rs.getString("telephone"),
					new Adresse(rs.getString("rue"), rs.getString("ville"), rs.getString("CP")));
			client.setPersonneID(rs.getInt("pers_id"));
			result.add(client);
		}

		rs.close();
		stmt.close();

		return result;
	}

	public void addReservation(Devis reservation) throws SQLException {
		PreparedStatement stmt = instance.getConnection().prepareStatement(
				"insert into Reservation (pers_id, categorie, assurance, debut_location, duree_location) values (?,?,?,?,?);");
		stmt.setInt(1, reservation.getClientID());
		stmt.setString(2, reservation.getCategorie().toString());
		stmt.setBoolean(3, reservation.isAssurance());
		stmt.setDate(4, reservation.getDebutLocation());
		stmt.setInt(5, reservation.getDureeLocation());
		stmt.executeUpdate();
		stmt.close();

	}

	public Devis getDevis(int clientID, Date dateDebutLocation) throws SQLException {
		PreparedStatement stmt = instance.getConnection()
				.prepareStatement("select * from Reservation where pers_id=? and debut_location=?");
		stmt.setInt(1, clientID);
		stmt.setDate(2, dateDebutLocation);

		ResultSet rs = stmt.executeQuery();

		Devis result = null;
		if (rs.next()) {
			result = new Devis(rs.getDate("debut_location"), rs.getInt("duree_location"), rs.getBoolean("assurance"),
					rs.getInt("pers_id"), TypeCategorie.get(rs.getString("categorie")));
		}

		rs.close();
		stmt.close();

		return result;
	}

	/**
	 * Retourne la liste des vehicule disponible au jour présent par catégorie
	 * 
	 * ATTENTION requête imparfaite car fait apparaitre un vehicule même s'il est
	 * réservé mais que la location n'a pas commencé
	 * 
	 * @param categorie
	 * @return
	 * @throws SQLException
	 */
	public List<Vehicule> getVehiculeDispoByCategorie(TypeCategorie categorie) throws SQLException {
		PreparedStatement stmt = instance.getConnection().prepareStatement(
				"select * from vehicule where matricule not IN (select matricule from reservation natural join location where CURRENT_DATE() between reservation.debut_location and date_add(reservation.debut_location,INTERVAL reservation.duree_location DAY)) and categorie=?;");
		stmt.setString(1, categorie.toString());
		ResultSet rs = stmt.executeQuery();

		List<Vehicule> result = new ArrayList<>(rs.getFetchSize());
		while (rs.next()) {
			result.add(new Vehicule(rs.getString("matricule"), rs.getString("marque"), rs.getString("modele"),
					rs.getBigDecimal("kilometrage"), TypeBoite.get(rs.getString("type_boite")),
					TypeCarburant.get(rs.getString("type_carburant")), rs.getBoolean("climatisation"),
					TypeCategorie.get(rs.getString("categorie"))));
		}

		rs.close();
		stmt.close();

		return result;
	}

	/**
	 * obtenir la liste des clients gold
	 *
	 * @return liste des clients gold
	 * @throws SQLException
	 */
	public List<Client> getClientGoldList() throws SQLException {
		// On utilise la vue gold_client !
		Statement stmt = instance.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select * from gold_client;");

		List<Client> result = new ArrayList<>(rs.getFetchSize());
		while (rs.next()) {
			Client client = new Client(rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),
					rs.getString("telephone"),
					new Adresse(rs.getString("rue"), rs.getString("ville"), rs.getString("CP")));
			client.setPersonneID(rs.getInt("pers_id"));
			result.add(client);
		}

		rs.close();
		stmt.close();

		return result;
	}

	/**
	 * liste des clients n'ayant aucune location enregistrée
	 *
	 * @return liste des clients sans historique
	 * @throws SQLException
	 */
	public List<Client> getClientNoLocationList() throws SQLException {
		Statement stmt = instance.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(
				"select * from Client natural join Adresse where pers_id not in (select distinct pers_id from Reservation) order by nom, prenom;");
		List<Client> result = new ArrayList<>(rs.getFetchSize());

		while (rs.next()) {
			Client client = new Client(rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),
					rs.getString("telephone"),
					new Adresse(rs.getString("rue"), rs.getString("ville"), rs.getString("CP")));
			client.setPersonneID(rs.getInt("pers_id"));
			result.add(client);
		}

		rs.close();
		stmt.close();

		return result;
	}

	public void addLocation(int clientID, Date dateDebut, String matricule) throws SQLException {
		instance.getConnection().setAutoCommit(false);

		// L'agence de départ perd un véhicule
		PreparedStatement stmt = instance.getConnection().prepareStatement(
				"update Agence set occupation = occupation-1 where id_agence=(select id_agence from Vehicule where matricule=?);");
		stmt.setString(1, matricule);
		stmt.executeUpdate();

		// Preter le véhicule au client
		stmt = instance.getConnection().prepareStatement("update Vehicule set id_agence=? where matricule=?;");
		stmt.setNull(1, java.sql.Types.INTEGER);
		stmt.setString(2, matricule);
		stmt.executeUpdate();

		// ajouter la location
		stmt = instance.getConnection()
				.prepareStatement("insert into Location(matricule, pers_id, debut_location) values (?,?,?)");
		stmt.setString(1, matricule);
		stmt.setInt(2, clientID);
		stmt.setDate(3, dateDebut);
		stmt.executeUpdate();

		instance.getConnection().commit();

		instance.getConnection().setAutoCommit(true);
	}
}
