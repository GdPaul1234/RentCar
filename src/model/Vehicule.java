package model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import model.enums.TypeBoite;
import model.enums.TypeCarburant;
import model.enums.TypeCategorie;
import model.interfaces.TabularObjectBuilder;

public class Vehicule extends TabularObjectBuilder {
	private String matricule;
	private String marque;
	private String modele;
	private BigDecimal kilometrage;
	private TypeBoite typeBoite;
	private TypeCarburant typeCarburant;
	private boolean climatisation;
	private TypeCategorie categorie;
	private Agence agence;

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

	// TODO copie défensive
	public void setAgence(Agence agence) {
		this.agence = agence;
	}

	public Agence getAgence() {
		return agence;
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

	/* Tabular Object Builder */

	@Override
	public Object[] toArray() {
		Object[] array = { matricule, marque, modele, kilometrage, typeBoite, typeCarburant, climatisation, categorie };
		return array;
	}

	public static String[] getHeader() {
		String[] header = { "matricule", "marque", "modèle", "kilométrage", "boîte", "carburant", "climatisation",
				"catégorie" };
		return header;
	}

	public static List<Integer> getColumnsWidth() {
		return List.of(60, 75, 100, 50, 75, 75, 20, 75);
	}

	public static List<String> getFacets() {
		List<String> facets = TabularObjectBuilder.getFacets();
		facets.add("En cours de location");
		facets.addAll(Arrays.asList(Arrays.stream(TypeCategorie.getValues())
				.map((v) -> v.toString().concat(" disponible")).toArray(String[]::new)));

		return facets;

	}

}
