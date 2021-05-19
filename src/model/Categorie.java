package model;

import java.math.BigDecimal;

public class Categorie {
	private String categorie;
	private BigDecimal tarif;
	private BigDecimal caution;

	public Categorie(String categorie, BigDecimal tarif, BigDecimal caution) {
		this.categorie = categorie;
		this.tarif = tarif;
		this.caution = caution;
	}

}
