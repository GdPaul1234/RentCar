package model;

public abstract class Personne {
	protected String nom;
	protected String prenom;
	protected String email;
	protected String telephone;
	protected Adresse adresse;

	public Personne(String nom, String prenom, String email, String telephone, Adresse adresse) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.adresse = adresse;
	}

}
