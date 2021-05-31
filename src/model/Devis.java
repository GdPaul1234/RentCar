package model;

import java.sql.Timestamp;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class Devis {

	private Timestamp debutLocation;
	private int dureeLocation;
	private boolean assurance;

	private Client client;
	private Vehicule voiture;

	public Devis(Timestamp debutLocation, int dureeLocation, boolean assurance, Client client, Vehicule voiture) {
		this.debutLocation = debutLocation;
		this.dureeLocation = dureeLocation;
		this.assurance = assurance;
		this.client = client;
		this.voiture = voiture;
	}

}
