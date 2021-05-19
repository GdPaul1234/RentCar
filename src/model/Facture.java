package model;

import java.sql.Time;

public class Facture {

	private int factureID;
	private Time dureeEffective;
	private int consommationCarburant;

	public Facture(int factureID, Time dureeEffective, int consommationCarburant) {
		this.factureID = factureID;
		this.dureeEffective = dureeEffective;
		this.consommationCarburant = consommationCarburant;
	}

}
