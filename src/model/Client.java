package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Client extends Personne {
	private java.sql.Date dateSouscription;
	private ProgrammeFidelite prgmFidelite;
	private List<Devis> devis = new ArrayList<>();
	private List<Location> locations = new ArrayList<>();

	public Client(String nom, String prenom, String email, String telephone, Adresse adresse) {
		super(nom, prenom, email, telephone, adresse);
	}

	public void addSouscription(ProgrammeFidelite fidelite, java.sql.Date dateSouscription) {
		this.dateSouscription = dateSouscription;
		prgmFidelite = fidelite;
	}

	public Date getDateExpiration() {
		if (prgmFidelite != null && dateSouscription != null) {
			Calendar dateExpiration = Calendar.getInstance();
			dateExpiration.setTime(dateSouscription);
			dateExpiration.add(Calendar.YEAR, prgmFidelite.getDuree());
			return dateExpiration.getTime();
		}
		return null;
	}

	public void addDevis(Devis devis) {
		this.devis.add(devis);
	}

	public void addFacture(Location facture) {
		locations.add(facture);
	}

	/* Getters */

	public ProgrammeFidelite getProgrammeFidelite() {
		return prgmFidelite;
	}

	public List<Devis> getDevis() {
		return Collections.unmodifiableList(devis);
	}

	public List<Location> getLocations() {
		return Collections.unmodifiableList(locations);
	}

	public java.sql.Date getDateSouscription() {
		return dateSouscription;
	}

}
