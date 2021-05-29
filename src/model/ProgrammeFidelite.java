package model;

import java.sql.Time;

public class ProgrammeFidelite {

	private int fideliteID;
	private String description;
	private Time duree;
	private float prix;
	private float reduction;

	public ProgrammeFidelite(int fideliteID, String description, Time duree, float prix, float reduction) {
		this.fideliteID = fideliteID;
		this.description = description;
		this.duree = duree;
		this.prix = prix;
		this.reduction = reduction;
	}
	
	
	/* Getters */
	public int getFideliteID() {
		return fideliteID;
	}

	public String getDescription() {
		return description;
	}
	
	public Time getDuree() {
		return duree;
	}

	public float getPrix() {
		return prix;
	}

	public float getReduction() {
		return reduction;
	}
	
	

}
