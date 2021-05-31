package model;

import java.sql.Timestamp;

import model.enums.TypeCategorie;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class Devis {

	private Timestamp debutLocation;
	private int dureeLocation;
	private boolean assurance;

	private int clientID;
	private TypeCategorie categorie;

	public Devis(Timestamp debutLocation, int dureeLocation, boolean assurance, int clientID, TypeCategorie voiture) {
		super();
		this.debutLocation = debutLocation;
		this.dureeLocation = dureeLocation;
		this.assurance = assurance;
		this.clientID = clientID;
		this.categorie = voiture;
	}

	/* Getters */

	public Timestamp getDebutLocation() {
		return debutLocation;
	}

	public int getDureeLocation() {
		return dureeLocation;
	}

	public boolean isAssurance() {
		return assurance;
	}

	public int getClientID() {
		return clientID;
	}

	public TypeCategorie getCategorie() {
		return categorie;
	}

}
