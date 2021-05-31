package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ProgrammeFidelite;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class SouscriptionDAO {
	private DataAccess instance;

	public SouscriptionDAO() {
		// obtenir les accès à la BDD
		instance = DataAccess.getInstance();
	}

	/**
	 * Obtenir la liste des programmes fidélité disponibles
	 * 
	 * @return la liste des programmes fidélité disponibles
	 * @throws SQLException
	 */
	public List<ProgrammeFidelite> getListPrgmFidelite() throws SQLException {
		Statement stmt = instance.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select * from ProgrammeFidelite order by fidelite_id;");

		ArrayList<ProgrammeFidelite> result = new ArrayList<>(rs.getFetchSize());
		while (rs.next()) {
			ProgrammeFidelite prgmFidelite = new ProgrammeFidelite(rs.getInt("fidelite_id"),
					rs.getString("description"), rs.getInt("duree"), rs.getBigDecimal("prix"),
					rs.getBigDecimal("reduction"));
			result.add(prgmFidelite);
		}

		rs.close();
		stmt.close();
		return result;
	}

	public void subcribeClientToProgrammeFidelite(int clientID, int fideliteID) throws SQLException {
		try {
			PreparedStatement stmt = instance.getConnection().prepareStatement(
					"insert into Souscription(pers_id,fidelite_id,date_souscription) VALUES (?,?, CURRENT_DATE());");
			stmt.setInt(1, clientID);
			stmt.setInt(2, fideliteID);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			PreparedStatement stmt = instance.getConnection().prepareStatement(
					"update Souscription set date_souscription=CURRENT_DATE(), fidelite_id=? where pers_id=? and fidelite_id=?;");
			stmt.setInt(1, fideliteID);
			stmt.setInt(2, clientID);
			stmt.setInt(3, fideliteID);
			stmt.executeUpdate();
			stmt.close();
		}

	}
}
