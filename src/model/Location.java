package model;

import java.sql.Timestamp;

public class Location {

	private Timestamp dateRendue;
	private int plein;
	private String etat;
	private Client client;
	private Vehicule vehicle;

	public Location(Timestamp dateRendue, int plein, String etat, Client client, Vehicule vehicle) {
		this.dateRendue = dateRendue;
		this.plein = plein;
		this.etat = etat;
		this.client = client;
		this.vehicle = vehicle;
	}

}
