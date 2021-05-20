package model;

public class Adresse {
	private String rue;
	private String ville;
	private String codePostal;

	public Adresse(String rue, String ville, String codePostal) {

		this.rue = rue;
		this.ville = ville;
		this.codePostal = codePostal;
	}

	/* Getters */
	public String getRue() {
		return rue;
	}

	public String getVille() {
		return ville;
	}

	public String getCodePostal() {
		return codePostal;
	}

}
