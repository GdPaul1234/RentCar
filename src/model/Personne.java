package model;

import model.interfaces.TabularObjectBuilder;

public abstract class Personne extends TabularObjectBuilder {
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

	@Override
	public Object[] toArray() {
		Object[] pArray = { nom, prenom, email, telephone };
		Object[] aArray = adresse.toArray();

		// merge pArray and aArray
		Object[] array = new Object[pArray.length + aArray.length];
		System.arraycopy(pArray, 0, array, 0, pArray.length);
		System.arraycopy(aArray, 0, array, pArray.length, aArray.length);
		return array;
	}
	
	public static String[] getHeader() {
		String[] pHeader = { "nom","prénom","email","téléphone" };
		String[] aHeader = Adresse.getHeader();
		
		// merge pArray and aArray
		String[] header = new String[pHeader.length + aHeader.length];
		System.arraycopy(pHeader, 0, header, 0, pHeader.length);
		System.arraycopy(aHeader, 0, header, pHeader.length, aHeader.length);
		return header;
	}

}
