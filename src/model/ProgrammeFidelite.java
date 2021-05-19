package model;

public class ProgrammeFidelite {

	private int fideliteID;
	private int description;
	private float prix;
	private float reduction;

	public ProgrammeFidelite(int fideliteID, int description, float prix, float reduction) {
		this.fideliteID = fideliteID;
		this.description = description;
		this.prix = prix;
		this.reduction = reduction;
	}

}
