package model;

import java.sql.Timestamp;

public class Devis {

	private Timestamp debutLocation;
	private int dureeLocation;
	private Asssurance assurance;

	private Client client;
	private Vehicule voiture;

	public Devis(Timestamp debutLocation, int dureeLocation, Asssurance assurance, Client client, Vehicule voiture) {
		this.debutLocation = debutLocation;
		this.dureeLocation = dureeLocation;
		this.assurance = assurance;
		this.client = client;
		this.voiture = voiture;
	}

}
