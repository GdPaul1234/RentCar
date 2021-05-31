package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Adresse;
import model.Agence;
import model.Client;
import model.Vehicule;
import model.enums.TypeBoite;
import model.enums.TypeCarburant;
import model.enums.TypeCategorie;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class VehiculeDAO {
	private DataAccess instance;

	public VehiculeDAO() {
		// obtenir les accès à la BDD
		instance = DataAccess.getInstance();
	}

	/**
	 * Obtenir la liste de tous les véhicules
	 * 
	 * @return liste des véhicules
	 * @throws SQLException
	 */
	public List<Vehicule> getVehiculeList() throws SQLException {
		Statement stmt = instance.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select * from Vehicule;");

		ArrayList<Vehicule> result = new ArrayList<>(rs.getFetchSize());
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
	 * Obtenir les informations sur un véhicule
	 * 
	 * @param matricule matricule du véhicule
	 * @return véhicule
	 * @throws SQLException
	 */
	public Vehicule getVehicule(String matricule) throws SQLException {
		PreparedStatement stmt = instance.getConnection().prepareStatement(
				"select * from Vehicule natural left outer join Agence natural left join Adresse where matricule=?;");
		stmt.setString(1, matricule);

		ResultSet rs = stmt.executeQuery();
		Vehicule vehicule = null;
		if (rs.next()) {
			vehicule = new Vehicule(rs.getString("matricule"), rs.getString("marque"), rs.getString("modele"),
					rs.getBigDecimal("kilometrage"), TypeBoite.get(rs.getString("type_boite")),
					TypeCarburant.get(rs.getString("type_carburant")), rs.getBoolean("climatisation"),
					TypeCategorie.get(rs.getString("categorie")));

			// set agence of vehicle if id_agence is not null
			int idAgence = rs.getInt("id_agence");
			if (!rs.wasNull()) {
				vehicule.setAgence(new Agence(idAgence, rs.getString("nom"), rs.getString("telephone"),
						rs.getString("geolocalisation"), rs.getInt("occupation"), rs.getInt("capacite"),
						new Adresse(rs.getString("rue"), rs.getString("ville"), rs.getString("CP"))));
			}

		}

		rs.close();
		stmt.close();
		return vehicule;
	}

	/**
	 * Ajouter un véhicule dans la base de données
	 * 
	 * @param matricule matricule du véhicule
	 * @throws SQLException
	 */
	public void addVehicule(Vehicule voiture) throws SQLException {
		// insertion vehicule
		PreparedStatement stmtVehicule = instance.getConnection().prepareStatement(
				"insert into Vehicule(matricule,marque,modele,kilometrage,type_boite,type_carburant,climatisation,categorie) values(?,?,?,?,?,?,?,?);");
		stmtVehicule.setString(1, voiture.getMatricule());
		stmtVehicule.setString(2, voiture.getMarque());
		stmtVehicule.setString(3, voiture.getModele());
		stmtVehicule.setBigDecimal(4, voiture.getKilometrage());
		stmtVehicule.setString(5, voiture.getTypeBoite().toString());
		stmtVehicule.setString(6, voiture.getTypeCarburant().toString());
		stmtVehicule.setBoolean(7, voiture.isClimatisation());
		stmtVehicule.setString(8, voiture.getCategorie().toString());
		stmtVehicule.executeUpdate();
		stmtVehicule.close();
	}

	/**
	 * Ajouter un véhicule dans la base de données
	 * 
	 * @param matricule matricule du véhicule
	 * @throws SQLException
	 */
	public void editVehicule(Vehicule voiture) throws SQLException {
		// insertion vehicule
		PreparedStatement stmtVehicule = instance.getConnection()
				.prepareStatement("update Vehicule set marque=?, modele=?, kilometrage=?, type_boite=?,"
						+ "type_carburant=?, climatisation=?, categorie=? where matricule=?;");
		stmtVehicule.setString(1, voiture.getMarque());
		stmtVehicule.setString(2, voiture.getModele());
		stmtVehicule.setBigDecimal(3, voiture.getKilometrage());
		stmtVehicule.setString(4, voiture.getTypeBoite().toString());
		stmtVehicule.setString(5, voiture.getTypeCarburant().toString());
		stmtVehicule.setBoolean(6, voiture.isClimatisation());
		stmtVehicule.setString(7, voiture.getCategorie().toString());
		stmtVehicule.setString(8, voiture.getMatricule());
		stmtVehicule.executeUpdate();
		stmtVehicule.close();
	}

	/**
	 * Retirer un véhicule de la base de données
	 * 
	 * @param matricule matricule du véhicule
	 * @throws SQLException
	 */
	public void removeVehicule(String matricule) throws SQLException {
		PreparedStatement stmtVehicule = instance.getConnection()
				.prepareStatement("delete from Voiture where matricule=?;");
		stmtVehicule.setString(1, matricule);
		stmtVehicule.executeUpdate();
		stmtVehicule.close();
	}

	/**
	 * Rechercher les vehicules par marque
	 * 
	 * @param name
	 * @return liste des vehicules ayant cette marque
	 * @throws SQLException
	 */
	public List<Vehicule> searchVehiculeByMarque(String marque) throws SQLException {
		PreparedStatement stmtVehicule = instance.getConnection()
				.prepareStatement("select * from Vehicule where marque like ?");
		stmtVehicule.setString(1, marque + "%");
		ResultSet rs = stmtVehicule.executeQuery();

		ArrayList<Vehicule> result = new ArrayList<>(rs.getFetchSize());
		while (rs.next()) {
			result.add(new Vehicule(rs.getString("matricule"), rs.getString("marque"), rs.getString("modele"),
					rs.getBigDecimal("kilometrage"), TypeBoite.get(rs.getString("type_boite")),
					TypeCarburant.get(rs.getString("type_carburant")), rs.getBoolean("climatisation"),
					TypeCategorie.get(rs.getString("categorie"))));
		}

		rs.close();
		stmtVehicule.close();

		return result;
	}

	/**
	 * Retourner tous les clients ayant déja loué le vehicule de matricule donné
	 *
	 * @param matricule
	 * @return
	 * @throws SQLException
	 */
	public List<Client> getClientByVehiculeLoue(String matricule) throws SQLException {
		PreparedStatement stmtClient = instance.getConnection()
				.prepareStatement("select * from client natural join adresse natural join location where matricule=?");
		stmtClient.setString(1, matricule);

		ResultSet rs = stmtClient.executeQuery();
		List<Client> result = new ArrayList<>(rs.getFetchSize());

		while (rs.next()) {
			Client client = new Client(rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),
					rs.getString("telephone"),
					new Adresse(rs.getString("rue"), rs.getString("ville"), rs.getString("CP")));
			client.setPersonneID(rs.getInt("pers_id"));
			result.add(client);
		}

		rs.close();
		stmtClient.close();

		return result;
	}
}
