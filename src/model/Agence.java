package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.interfaces.TabularObjectBuilder;

public class Agence extends TabularObjectBuilder {
	private int identifiant;
	private String nom;
	private String telephone;
	private String geolocalisation;
	private int occupation;
	private int capacite;
	private Adresse adresse;
	private List<Employe> employes = new ArrayList<>();
	private List<Vehicule> vehicules = new ArrayList<>();

	public Agence(int identifiant, String nom, String telephone, String geolocalisation, int occupation, int capacite,
			Adresse adresse) {
		this.identifiant = identifiant;
		this.nom = nom;
		this.telephone = telephone;
		this.geolocalisation = geolocalisation;
		this.occupation = occupation;
		this.capacite = capacite;
		this.adresse = adresse;
	}

	/* Getters */

	public int getIdentifiant() {
		return identifiant;
	}

	public String getNom() {
		return nom;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getGeolocalisation() {
		return geolocalisation;
	}

	public int getOccupation() {
		return occupation;
	}

	public int getCapacite() {
		return capacite;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public List<Vehicule> getVehicules() {
		return Collections.unmodifiableList(vehicules);
	}

	public List<Employe> getEmployes() {
		return Collections.unmodifiableList(employes);
	}

	/* Gestion employes */
	public void addEmploye(Employe employe) {
		employes.add(employe);
	}

	public Employe fireEmploye(Employe employe) {
		return employes.remove(employe) ? employe : null;
	}

	/* Gestion véhicules */
	public void addVehicule(Vehicule vehicule) {
		vehicules.add(vehicule);
	}

	public Vehicule removeVehicule(Vehicule vehicule) {
		return vehicules.remove(vehicule) ? vehicule : null;
	}

	@Override
	public Object[] toArray() {
		Object[] agArray = { identifiant, nom, telephone, occupation, capacite, geolocalisation };
		Object[] adArray = adresse.toArray();
		
		// merge pArray and aArray
		Object[] array = new Object[agArray.length + adArray.length];
		System.arraycopy(agArray, 0, array, 0, agArray.length);
		System.arraycopy(adArray, 0, array, agArray.length, adArray.length);
		return array;
	}
	
	public static String[] getHeader() {
		String[] agHeader = { "id", "nom", "téléphone", "occupation", "capacite", "géolocalisation" };
		String[] adHeader = Adresse.getHeader();

		// merge pArray and aArray
		String[] header = new String[agHeader.length + adHeader.length];
		System.arraycopy(agHeader, 0, header, 0, agHeader.length);
		System.arraycopy(adHeader, 0, header, agHeader.length, adHeader.length);
		return header;
	}
	
	public static List<Integer> getColumnsWidth() {
		List<Integer> result = new ArrayList<>();
		result.addAll(List.of(0, 250, 150, 50, 50, 100));
		result.addAll(Adresse.getColumnsWidth());
		return result;
	}

}
