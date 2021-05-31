package model;

import java.sql.Date;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class Location {
	private Date dateDebut;
	private Date dateRendue;
	private short plein;
	private String etat;
	private int clientID;
	private String matricule;

	public Location(Date dateDebut, Date dateRendue, short plein, String etat, int clientID, String matricule) {
		this.dateDebut = dateDebut;
		this.dateRendue = dateRendue;
		this.plein = plein;
		this.etat = etat;
		this.clientID = clientID;
		this.matricule = matricule;
	}

	/* Getters */
	public Date getDateDebut() {
		return dateDebut;
	}

	public Date getDateRendue() {
		return dateRendue;
	}

	public short getPlein() {
		return plein;
	}

	public String getEtat() {
		return etat;
	}

	public int getClientID() {
		return clientID;
	}

	public String getMatricule() {
		return matricule;
	}

}
