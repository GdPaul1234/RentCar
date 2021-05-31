package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Adresse;
import model.Client;
import model.Devis;

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
}
