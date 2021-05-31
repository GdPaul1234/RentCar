package model;

import java.math.BigDecimal;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class ProgrammeFidelite {

	private int fideliteID;
	private String description;
	private int duree;
	private BigDecimal prix;
	private BigDecimal reduction;

	public ProgrammeFidelite(int fideliteID, String description, int duree, BigDecimal prix, BigDecimal reduction) {
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

	public int getDuree() {
		return duree;
	}

	public BigDecimal getPrix() {
		return prix;
	}

	public BigDecimal getReduction() {
		return reduction;
	}

	@Override
	public String toString() {
		return String.format("%d an(s), %.2f €, - %.0f %% sur les locations", duree,
				prix.floatValue(), 100 * reduction.floatValue());
	}

}
