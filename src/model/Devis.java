package model;

import java.sql.Time;
import java.sql.Timestamp;

public class Devis {

	private Timestamp debutLocation;
	private Time dureeLocation;
	private boolean assurance;

	private Client client;
	private Vehicule voiture;

	public Devis(Timestamp debutLocation, Time dureeLocation, boolean assurance, Client client, Vehicule voiture) {
		this.debutLocation = debutLocation;
		this.dureeLocation = dureeLocation;
		this.assurance = assurance;
		this.client = client;
		this.voiture = voiture;
	}

}
