package model;

import java.util.List;

import model.interfaces.TabularObjectBuilder;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class Adresse extends TabularObjectBuilder {
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

	@Override
	public Object[] toArray() {
		Object[] array = { rue, codePostal, ville };
		return array;
	}

	public static String[] getHeader() {
		String[] header = { "rue", "CP", "ville" };
		return header;
	}
	

	public static List<Integer> getColumnsWidth() {
		return List.of(200, 75, 100);
	}

}
