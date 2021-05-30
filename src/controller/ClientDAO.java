package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Adresse;
import model.Client;
import model.ProgrammeFidelite;

/**
 * Client Database Access Object
 * 
 * @author Paul
 *
 */
public class ClientDAO {
	private DataAccess instance;

	public ClientDAO() {
		// obtenir les accès à la BDD
		instance = DataAccess.getInstance();
	}

	/**
	 * Obtenir la liste de tous les clients
	 * 
	 * @return liste des clients
	 * @throws SQLException
	 */
	public List<Client> getClientList() throws SQLException {
		Statement stmt = instance.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select * from Client natural join Adresse order by nom, prenom;");

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

	/**
	 * Obtenir les informations sur un client
	 * 
	 * @param clientID ID du client
	 * @return client
	 * @throws SQLException
	 */
	public Client getClient(int clientID) throws SQLException {
		PreparedStatement stmt = instance.getConnection().prepareStatement("select * from Client natural join Adresse"
				+ " natural left join Souscription natural left join ProgrammeFidelite where pers_id=?;");
		stmt.setInt(1, clientID);

		ResultSet rs = stmt.executeQuery();
		Client client = null;
		if (rs.next()) {
			client = new Client(rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),
					rs.getString("telephone"),
					new Adresse(rs.getString("rue"), rs.getString("ville"), rs.getString("CP")));
			client.setPersonneID(rs.getInt("pers_id"));

			java.sql.Date dateSouscription = rs.getDate("date_souscription");
			if (!rs.wasNull())
				client.addSouscription(new ProgrammeFidelite(rs.getInt("fidelite_id"), rs.getString("description"),
						rs.getInt("duree"), rs.getBigDecimal("prix"), rs.getBigDecimal("reduction")), dateSouscription);
		}

		rs.close();
		stmt.close();
		return client;
	}

	private void insertAdresse(Adresse adresse) throws SQLException {
		PreparedStatement stmtAdresse = instance.getConnection()
				.prepareStatement("insert into Adresse(rue,ville,CP) values(?,?,?)");
		stmtAdresse.setString(1, adresse.getRue());
		stmtAdresse.setString(2, adresse.getVille());
		stmtAdresse.setString(3, adresse.getCodePostal());
		stmtAdresse.executeUpdate();
		stmtAdresse.close();
	}

	private boolean isAdresseExistante(Adresse adresse) throws SQLException {
		PreparedStatement stmtAdresse = instance.getConnection()
				.prepareStatement("select count(*) as count from Adresse where rue=? AND ville=? AND cp=?;");
		stmtAdresse.setString(1, adresse.getRue());
		stmtAdresse.setString(2, adresse.getVille());
		stmtAdresse.setString(3, adresse.getCodePostal());

		ResultSet rset = stmtAdresse.executeQuery();
		rset.next();
		int nbSameAdresse = rset.getInt("count");
		rset.close();
		stmtAdresse.close();
		return nbSameAdresse == 0;
	}

	/**
	 * Ajouter un client dans la base de données
	 * 
	 * @param client Client à ajouter
	 * @throws SQLException
	 */
	public void addClient(Client client) throws SQLException {
		// insertion adressse si inexistante
		Adresse clientAdress = client.getAdresse();
		if (isAdresseExistante(clientAdress)) {
			insertAdresse(clientAdress);
		}

		// insertion client
		PreparedStatement stmtClient = instance.getConnection()
				.prepareStatement("insert into Client(nom,prenom,email,telephone,rue,ville) values(?,?,?,?,?,?);");
		stmtClient.setString(1, client.getNom());
		stmtClient.setString(2, client.getPrenom());
		stmtClient.setString(3, client.getEmail());
		stmtClient.setString(4, client.getTelephone());
		stmtClient.setString(5, client.getAdresse().getRue());
		stmtClient.setString(6, client.getAdresse().getVille());
		stmtClient.executeUpdate();
		stmtClient.close();
	}

	public void editClient(int clientID, Client client) throws SQLException {
		// insertion adressse si inexistante
		Adresse clientAdress = client.getAdresse();
		if (isAdresseExistante(clientAdress)) {
			insertAdresse(clientAdress);
		}

		PreparedStatement stmtClient = instance.getConnection().prepareStatement(
				"update Client set nom=?, prenom=?, email=?, telephone=?, rue=?, ville=? where pers_id=?;");
		stmtClient.setString(1, client.getNom());
		stmtClient.setString(2, client.getPrenom());
		stmtClient.setString(3, client.getEmail());
		stmtClient.setString(4, client.getTelephone());
		stmtClient.setString(5, client.getAdresse().getRue());
		stmtClient.setString(6, client.getAdresse().getVille());
		stmtClient.setInt(7, clientID);
		stmtClient.executeUpdate();
		stmtClient.close();
	}

	/**
	 * Retirer un client de la base de données
	 * 
	 * @param clientID
	 * @throws SQLException
	 */
	public void removeClient(int clientID) throws SQLException {
		// Asume ON DELETE CASCADE on FOREIGN KEY
		PreparedStatement stmtClient = instance.getConnection().prepareStatement("delete from Client where pers_id=?;");
		stmtClient.setInt(1, clientID);
		stmtClient.executeUpdate();
		stmtClient.close();
	}

	/**
	 * Rechercher les clients par nom
	 * 
	 * @param name
	 * @return liste des clients ayant ce nom
	 * @throws SQLException
	 */
	public List<Client> searchClientByName(String name) throws SQLException {
		PreparedStatement stmtClient = instance.getConnection()
				.prepareStatement("select * from Client natural join Adresse where nom like ?");
		stmtClient.setString(1, name + "%");
		ResultSet rs = stmtClient.executeQuery();

		ArrayList<Client> result = new ArrayList<>(rs.getFetchSize());
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
