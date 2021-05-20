package model;

public class Employe extends Personne {

	private String login;
	private String password;

	public Employe(String nom, String prenom, String email, String telephone, Adresse adresse, String login,
			String password) {
		super(nom, prenom, email, telephone, adresse);
		this.login = login;
		this.password = password;
	}

}
