package model;

public class Vehicule {
	private String matricule;
	private String marque;
	private String modele;
	private double kilometrage;
	private String typeBoite;
	private String typeCarburant;
	private boolean climatisation;
	private Categorie categorie;

	public Vehicule(String matricule, String marque, String modele, double kilometrage, String typeBoite,
			String typeCarburant, boolean climatisation) {
		this.matricule = matricule;
		this.marque = marque;
		this.modele = modele;
		this.kilometrage = kilometrage;
		this.typeBoite = typeBoite;
		this.typeCarburant = typeCarburant;
		this.climatisation = climatisation;
	}

}
