package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Adresse;
import model.Agence;

/**
 * Agence Database Access Object
 * 
 * @author Paul
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

}
