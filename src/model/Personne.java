package model;

import java.util.ArrayList;
import java.util.List;

import model.interfaces.TabularObjectBuilder;

public abstract class Personne extends TabularObjectBuilder {
	protected int personneID;
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

	/* Getters */
	public int getPersonneID() {
		return personneID;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getEmail() {
		return email;
	}

	public String getTelephone() {
		return telephone;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	/* Setters */
	public void setPersonneID(int personneID) {
		this.personneID = personneID;
	}

	@Override
	public Object[] toArray() {
		Object[] pArray = { personneID, nom, prenom, email, telephone };
		Object[] aArray = adresse.toArray();

		// merge pArray and aArray
		Object[] array = new Object[pArray.length + aArray.length];
		System.arraycopy(pArray, 0, array, 0, pArray.length);
		System.arraycopy(aArray, 0, array, pArray.length, aArray.length);
		return array;
	}

	public static String[] getHeader() {
		String[] pHeader = { "id", "nom", "prénom", "email", "téléphone" };
		String[] aHeader = Adresse.getHeader();

		// merge pArray and aArray
		String[] header = new String[pHeader.length + aHeader.length];
		System.arraycopy(pHeader, 0, header, 0, pHeader.length);
		System.arraycopy(aHeader, 0, header, pHeader.length, aHeader.length);
		return header;
	}

	public static List<Integer> getColumnsWidth() {
		List<Integer> result = new ArrayList<>();
		result.addAll(List.of(0, 100, 100, 225, 200));
		result.addAll(Adresse.getColumnsWidth());
		return result;
	}

}
