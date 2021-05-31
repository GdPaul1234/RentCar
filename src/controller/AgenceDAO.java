package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Adresse;
import model.Agence;
import model.Vehicule;
import model.enums.TypeBoite;
import model.enums.TypeCarburant;
import model.enums.TypeCategorie;

/**
 * Agence Database Access Object
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class AgenceDAO {
	private DataAccess instance;

	public AgenceDAO() {
		// obtenir les accès à la BDD
		instance = DataAccess.getInstance();
	}

	/**
	 * Obtenir la liste de toutes les agences
	 * 
	 * @return liste des agences
	 * @throws SQLException
	 */
	public List<Agence> getAgenceList() throws SQLException {
		Statement stmt = instance.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select * from Agence natural join Adresse order by id_agence;");

		ArrayList<Agence> result = new ArrayList<>(rs.getFetchSize());
		while (rs.next()) {
			Agence agence = new Agence(rs.getInt("id_agence"), rs.getString("nom"), rs.getString("telephone"),
					rs.getString("geolocalisation"), rs.getInt("occupation"), rs.getInt("capacite"),
					new Adresse(rs.getString("rue"), rs.getString("ville"), rs.getString("CP")));
			result.add(agence);
		}

		rs.close();
		stmt.close();

		return result;
	}

	/**
	 * Retourner la liste des vehicules stationés dans une agence donnée
	 * 
	 * @param id_agence
	 * @return
	 * @throws SQLException
	 */
	public List<Vehicule> getVehiculeByAgence(int id_agence) throws SQLException {
		PreparedStatement stmtVehicule = instance.getConnection()
				.prepareStatement("select * from vehicule natural join agence where id_agence=?;");
		stmtVehicule.setInt(1, id_agence);
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
	 * renvoie la liste des agences ayant + de 80% de leur capacité occupé
	 *
	 * @return liste d'agence à +80% capacité
	 * @throws SQLException
	 */
	public List<Agence> getAgencePleineList() throws SQLException {
		Statement stmt = instance.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(
				"select * from agence natural join adresse where occupation >= capacite*0.80 order by id_agence;");
		ArrayList<model.Agence> result = new ArrayList<>(rs.getFetchSize());
		while (rs.next()) {
			model.Agence agence = new model.Agence(rs.getInt("id_agence"), rs.getString("nom"),
					rs.getString("telephone"), rs.getString("geolocalisation"), rs.getInt("occupation"),
					rs.getInt("capacite"), new Adresse(rs.getString("rue"), rs.getString("ville"), rs.getString("CP")));
			result.add(agence);
		}
		rs.close();
		stmt.close();
		return result;
	}

	public void moveVehiculeTo(String matricule, int id_agence) throws SQLException {
		instance.getConnection().setAutoCommit(false);

		PreparedStatement stmt = instance.getConnection()
				.prepareStatement("update agence set occupation = occupation+1 where id_agence = ?;");
		stmt.setInt(1, id_agence);
		stmt.executeUpdate();

		stmt = instance.getConnection().prepareStatement(
				"update agence set occupation = occupation-1 where id_agence = (select id_agence from Vehicule where matricule=?);");
		stmt.setString(1, matricule);
		stmt.executeUpdate();

		stmt = instance.getConnection().prepareStatement("update Vehicule set id_agence = 2 where matricule=?;");
		stmt.setString(1, matricule);
		stmt.executeUpdate();
		instance.getConnection().commit();

		instance.getConnection().setAutoCommit(true);
		stmt.close();
	}

}
