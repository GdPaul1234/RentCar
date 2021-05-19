package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Agence {
	private int identifiant;
	private String nom;
	private String telephone;
	private String geolocalisation;
	private int occupation;
	private int capacite;
	private Adresse adresse;
	private List<Employe> employes = new ArrayList<>();
	private List<Vehicule> vehicules = new ArrayList<>();

	public Agence(int identifiant, String nom, String telephone, String geolocalisation, int occupation,
			int capacite, Adresse adresse) {
		this.identifiant = identifiant;
		this.nom = nom;
		this.telephone = telephone;
		this.geolocalisation = geolocalisation;
		this.occupation = occupation;
		this.capacite = capacite;
		this.adresse = adresse;
	}

	/* Gestion employes */
	public void addEmploye(Employe employe) {
		employes.add(employe);
	}

	public Employe fireEmploye(Employe employe) {
		return employes.remove(employe) ? employe : null;
	}

	public List<Employe> getEmployes() {
		return Collections.unmodifiableList(employes);
	}
	
	/* Gestion véhicules */
	public void addVehicule(Vehicule vehicule) {
		vehicules.add(vehicule);
	}
	
	public Vehicule removeVehicule(Vehicule vehicule) {
		return vehicules.remove(vehicule) ? vehicule : null;
	}

}
