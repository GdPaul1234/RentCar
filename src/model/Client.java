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

	public Client(String nom, String prenom, String email, String telephone, Adresse adresse
			) {
		super(nom, prenom, email, telephone, adresse);
	}
	
	public boolean isSouscriptionValid() {
		return getExpirationDate().after(Calendar.getInstance().getTime());
		
	}
	
	public void addSouscription(ProgrammeFidelite fidelite) {
		// get current date
		Date now = new Date();
		dateSouscription = new java.sql.Date(now.getTime());
		prgmFidelite = fidelite;
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
	
	public Date getExpirationDate() {
		Date expirationDate = new Date();
		expirationDate.setTime(dateSouscription.getTime() + prgmFidelite.getDuree().getTime());
		return expirationDate;
	}
	
	public List<Devis> getDevis() {
		return Collections.unmodifiableList(devis);
	}
	
	public List<Location> getLocations() {
		return Collections.unmodifiableList(locations);
	}
	
	/* Setters */
	
	public void setDateSouscription(java.sql.Date dateSouscription) {
		this.dateSouscription = dateSouscription;
	}

	

}
