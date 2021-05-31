package model;

import java.sql.Date;

import model.enums.TypeCategorie;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class Devis {

	private Date debutLocation;
	private int dureeLocation;
	private boolean assurance;

	private int clientID;
	private TypeCategorie categorie;

	public Devis(Date debutLocation, int dureeLocation, boolean assurance, int clientID, TypeCategorie voiture) {
		super();
		this.debutLocation = debutLocation;
		this.dureeLocation = dureeLocation;
		this.assurance = assurance;
		this.clientID = clientID;
		this.categorie = voiture;
	}

	/* Getters */

	public Date getDebutLocation() {
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
