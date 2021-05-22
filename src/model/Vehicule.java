package model;

import java.math.BigDecimal;

import model.enums.TypeBoite;
import model.enums.TypeCarburant;
import model.enums.TypeCategorie;

public class Vehicule {
	private String matricule;
	private String marque;
	private String modele;
	private BigDecimal kilometrage;
	private TypeBoite typeBoite;
	private TypeCarburant typeCarburant;
	private boolean climatisation;
	private TypeCategorie categorie;

	public Vehicule(String matricule, String marque, String modele, BigDecimal kilometrage, TypeBoite typeBoite,
			TypeCarburant typeCarburant, boolean climatisation, TypeCategorie categorie) {
		this.matricule = matricule;
		this.marque = marque;
		this.modele = modele;
		this.kilometrage = kilometrage;
		this.typeBoite = typeBoite;
		this.typeCarburant = typeCarburant;
		this.climatisation = climatisation;
		this.categorie = categorie;
	}

	/* Getters */
	public String getMatricule() {
		return matricule;
	}

	public String getMarque() {
		return marque;
	}

	public String getModele() {
		return modele;
	}

	public BigDecimal getKilometrage() {
		return kilometrage;
	}

	public TypeBoite getTypeBoite() {
		return typeBoite;
	}

	public TypeCarburant getTypeCarburant() {
		return typeCarburant;
	}

	public boolean isClimatisation() {
		return climatisation;
	}

	public TypeCategorie getCategorie() {
		return categorie;
	}

	
	@Override
	public String toString() {
		return "Vehicule [matricule=" + matricule + ", marque=" + marque + ", modele=" + modele + ", kilometrage="
				+ kilometrage + ", typeBoite=" + typeBoite + ", typeCarburant=" + typeCarburant + ", climatisation="
				+ climatisation + ", categorie=" + categorie + "]";
	}
	
}
