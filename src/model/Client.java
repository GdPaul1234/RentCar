package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Client extends Personne {
	private Date dateSouscription;
	private ProgrammeFidelite prgmFidelite;
	private List<Devis> devis = new ArrayList<>();
	private List<Facture> factures = new ArrayList<>();

	public Client(String nom, String prenom, String email, String telephone, Adresse adresse
			) {
		super(nom, prenom, email, telephone, adresse);
	}
	
	public void renewSouscription(ProgrammeFidelite fidelite) {
		dateSouscription = new Date();
		prgmFidelite = fidelite;
	}
	
	public void addDevis(Devis devis) {
		this.devis.add(devis);
	}
	
	public void addFacture(Facture facture) {
		factures.add(facture);
	}
	

}
