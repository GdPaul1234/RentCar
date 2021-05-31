package view.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.AgenceDAO;
import controller.ClientDAO;
import controller.LocationDAO;
import model.Agence;
import model.Devis;
import model.Location;
import model.Vehicule;
import model.enums.TypeCategorie;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class RendueVoitureClient extends JDialog implements ActionListener, ChangeListener {

	private static final long serialVersionUID = -6017187287474330845L;
	private JDialog frame = this;

	private final JPanel contentPanel = new JPanel();
	private JSpinner dateStartSpinner = new JSpinner();
	private JSpinner dateRendueSpinner = new JSpinner();
	private JLabel montantDevisLabel = new JLabel("--,-- €");
	private JLabel penaliteLabel = new JLabel("--,-- €");
	private JLabel fraisRemiseLabel = new JLabel("--,-- €");
	private JLabel supplementLabel = new JLabel("--,-- €");
	private JLabel montantLabel = new JLabel("--,-- €");
	private JSlider consommationSlider = new JSlider();
	private JCheckBox accidentCheckBox = new JCheckBox("accident, dégradation");
	private JLabel infoVehiculeLabel = new JLabel("N/A");
	private JComboBox<Agence> agenceComboBox = new JComboBox<Agence>();

	private float fraisRemiseAssurance = 200.00f;
	private float penaliteDateRendue = 75.00f;
	private float supplementEssence = 18.75f;

	private float tarifCategorie;
	private float reductionFidelite;
	private int dureeLocation;
	private Date dateDebutLocation;
	private Vehicule vehicule;

	private int clientID;

	/**
	 * Create the dialog.
	 */
	public RendueVoitureClient(int clientID) {
		this.clientID = clientID;
		new GetFinalDevisTask().execute();
		createUI();
	}

	public void run(Component frame) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(frame);
		setResizable(false);
		setModal(true);
		setVisible(true);
	}

	class GetFinalDevisTask extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			try {
				Date dateStart = new Date(((SpinnerDateModel) dateStartSpinner.getModel()).getDate().getTime());

				LocationDAO locationDAO = new LocationDAO();
				Devis devis = locationDAO.getDevis(clientID, dateStart);

				if (devis != null) {
					if (devis.isAssurance())
						fraisRemiseAssurance = 0;

					TypeCategorie categorie = devis.getCategorie();
					dateDebutLocation = devis.getDebutLocation();
					dureeLocation = devis.getDureeLocation();

					// obtenir info prix
					BigDecimal[] infoPrix = new ClientDAO().getCategoriePrixReduction(clientID, categorie);
					tarifCategorie = infoPrix[0].floatValue();
					reductionFidelite = infoPrix[1].floatValue();

					// get nom véhicule
					vehicule = locationDAO.getVoitureLoue(clientID, dateStart);
				} else {
					dateDebutLocation = null;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void done() {
			try {
				updateUI();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private class ChangeDevisListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			new GetFinalDevisTask().execute();

		}

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		updateUI();
	}

	@SuppressWarnings("deprecation")
	private void updateUI() {
		if (dateDebutLocation != null) {
			infoVehiculeLabel.setText(vehicule.getMarque() + " " + vehicule.getModele());

			// maj prix à payer
			double montant = (dureeLocation * tarifCategorie) * (1.0 - reductionFidelite);

			if (fraisRemiseAssurance == 0) {
				montant += 19.5;
			}
			montantDevisLabel.setText(String.format("%.2f €", montant));

			// ajout pénalité
			java.util.Date endRendueDate = ((SpinnerDateModel) dateRendueSpinner.getModel()).getDate();
			endRendueDate.setHours(0);
			endRendueDate.setMinutes(0);
			endRendueDate.setMinutes(1);

			java.util.Date startDevisDate = ((SpinnerDateModel) dateStartSpinner.getModel()).getDate();
			startDevisDate.setHours(0);
			startDevisDate.setMinutes(0);
			startDevisDate.setMinutes(0);

			long endDevisDateTime = startDevisDate.getTime() + dureeLocation * 24 * 3600 * 1000;

			long dateDiff = (endRendueDate.getTime() - endDevisDateTime) / (24 * 3600 * 1000);
			if (dateDiff > 0 && startDevisDate.before(endRendueDate)) {
				montant += dateDiff * penaliteDateRendue;
				penaliteLabel.setText(String.format("%.2f €", dateDiff * penaliteDateRendue));
			}

			// ajout frais remise assurance
			if (accidentCheckBox.isSelected()) {
				montant += fraisRemiseAssurance;
				fraisRemiseLabel.setText(String.format("%.2f €", fraisRemiseAssurance));
			} else {
				fraisRemiseLabel.setText("0,00 €");
			}

			// ajout supplément carburant
			float supplementConsomation = consommationSlider.getValue() * supplementEssence;
			montant += supplementConsomation;
			supplementLabel.setText(String.format("%.2f €", supplementEssence));

			// affichage montant final
			montantLabel.setText(String.format("%.2f €", montant));
		} else {
			montantDevisLabel.setText("--,-- €");
			penaliteLabel.setText("--,-- €");
			fraisRemiseLabel.setText("--,-- €");
			supplementLabel.setText("--,-- €");
			montantLabel.setText("--,-- €");
			infoVehiculeLabel.setText("N/A");
		}

		invalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		switch (action) {
		case "OK":
			CompletableFuture.runAsync(new Runnable() {

				@Override
				public void run() {
					String etat = accidentCheckBox.isSelected() ? "endommagé" : "ok";
					try {
						if (dateDebutLocation != null) {
							new LocationDAO().rendueLocation(
									new Location(dateDebutLocation,
											new Date(((SpinnerDateModel) dateRendueSpinner.getModel()).getDate()
													.getTime()),
											Integer.valueOf(consommationSlider.getValue()).shortValue(), etat, clientID,
											vehicule.getMatricule()),
									((Agence) agenceComboBox.getSelectedItem()).getIdentifiant());
						}

						dispose();
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(frame, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}

				}
			});
			break;

		case "Cancel":
			dispose();
			break;
		default:
			break;
		}
	}

	private void createUI() {
		setTitle("Rendre véhicule");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel renduePanel = new JPanel();
			renduePanel.setBorder(BorderFactory.createTitledBorder("Rendue véhicule"));
			contentPanel.add(renduePanel, BorderLayout.CENTER);
			GridBagLayout gbl_renduePanel = new GridBagLayout();
			gbl_renduePanel.columnWidths = new int[] { 100, 200, 50 };
			gbl_renduePanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
			gbl_renduePanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
			renduePanel.setLayout(gbl_renduePanel);
			{
				{
					JLabel dateStartLabel = new JLabel("début location");
					GridBagConstraints gbc_dateStartLabel = new GridBagConstraints();
					gbc_dateStartLabel.anchor = GridBagConstraints.EAST;
					gbc_dateStartLabel.insets = new Insets(0, 0, 5, 5);
					gbc_dateStartLabel.gridx = 0;
					gbc_dateStartLabel.gridy = 0;
					renduePanel.add(dateStartLabel, gbc_dateStartLabel);
				}
			}
			dateStartSpinner.setModel(new SpinnerDateModel());
			dateStartSpinner.addChangeListener(new ChangeDevisListener());
			GridBagConstraints gbc_dateStartSpinner = new GridBagConstraints();
			gbc_dateStartSpinner.insets = new Insets(0, 0, 5, 5);
			gbc_dateStartSpinner.gridx = 1;
			gbc_dateStartSpinner.gridy = 0;
			renduePanel.add(dateStartSpinner, gbc_dateStartSpinner);
			{
				GridBagConstraints gbc_montantDevisLabel = new GridBagConstraints();
				gbc_montantDevisLabel.anchor = GridBagConstraints.EAST;
				gbc_montantDevisLabel.insets = new Insets(0, 0, 5, 5);
				gbc_montantDevisLabel.gridx = 2;
				gbc_montantDevisLabel.gridy = 0;
				renduePanel.add(montantDevisLabel, gbc_montantDevisLabel);
			}
			{
				JSeparator separator = new JSeparator();
				GridBagConstraints gbc_separator = new GridBagConstraints();
				gbc_separator.anchor = GridBagConstraints.EAST;
				gbc_separator.gridwidth = 3;
				gbc_separator.insets = new Insets(0, 0, 5, 0);
				gbc_separator.gridx = 1;
				gbc_separator.gridy = 1;
				renduePanel.add(separator, gbc_separator);
			}
			{
				JLabel vehiculeLabel = new JLabel("Véhicule");
				GridBagConstraints gbc_vehiculeLabel = new GridBagConstraints();
				gbc_vehiculeLabel.anchor = GridBagConstraints.EAST;
				gbc_vehiculeLabel.insets = new Insets(0, 0, 5, 5);
				gbc_vehiculeLabel.gridx = 0;
				gbc_vehiculeLabel.gridy = 2;
				renduePanel.add(vehiculeLabel, gbc_vehiculeLabel);
			}
			{
				{

					infoVehiculeLabel.setFont(UIManager.getFont("Viewport.font"));
					GridBagConstraints gbc_infoVehiculeLabel = new GridBagConstraints();
					gbc_infoVehiculeLabel.insets = new Insets(0, 0, 5, 5);
					gbc_infoVehiculeLabel.gridx = 1;
					gbc_infoVehiculeLabel.gridy = 2;
					renduePanel.add(infoVehiculeLabel, gbc_infoVehiculeLabel);
				}
				{
					JLabel agenceLabel = new JLabel("Agence");
					GridBagConstraints gbc_agenceLabel = new GridBagConstraints();
					gbc_agenceLabel.anchor = GridBagConstraints.EAST;
					gbc_agenceLabel.insets = new Insets(0, 0, 5, 5);
					gbc_agenceLabel.gridx = 0;
					gbc_agenceLabel.gridy = 3;
					renduePanel.add(agenceLabel, gbc_agenceLabel);
				}

			}
			List<Agence> listAgence;
			try {
				listAgence = new AgenceDAO().getAgenceList();
				agenceComboBox = new JComboBox<>(listAgence.toArray(new Agence[0]));
			} catch (SQLException e) {
				e.printStackTrace();
			}

			GridBagConstraints gbc_agenceComboBox = new GridBagConstraints();
			gbc_agenceComboBox.insets = new Insets(0, 0, 5, 5);
			gbc_agenceComboBox.gridx = 1;
			gbc_agenceComboBox.gridy = 3;
			renduePanel.add(agenceComboBox, gbc_agenceComboBox);
			{
				JSeparator separator = new JSeparator();
				GridBagConstraints gbc_separator = new GridBagConstraints();
				gbc_separator.anchor = GridBagConstraints.EAST;
				gbc_separator.gridwidth = 3;
				gbc_separator.insets = new Insets(0, 0, 5, 0);
				gbc_separator.gridx = 1;
				gbc_separator.gridy = 4;
				renduePanel.add(separator, gbc_separator);
			}
			{
				{
					JLabel dateRendue = new JLabel("date rendue");
					GridBagConstraints gbc_dateRendue = new GridBagConstraints();
					gbc_dateRendue.anchor = GridBagConstraints.EAST;
					gbc_dateRendue.insets = new Insets(0, 0, 5, 5);
					gbc_dateRendue.gridx = 0;
					gbc_dateRendue.gridy = 5;
					renduePanel.add(dateRendue, gbc_dateRendue);
				}
			}
			dateRendueSpinner.setModel(new SpinnerDateModel());
			dateRendueSpinner.addChangeListener(this);
			GridBagConstraints gbc_dateRendueSpinner = new GridBagConstraints();
			gbc_dateRendueSpinner.insets = new Insets(0, 0, 5, 5);
			gbc_dateRendueSpinner.gridx = 1;
			gbc_dateRendueSpinner.gridy = 5;
			renduePanel.add(dateRendueSpinner, gbc_dateRendueSpinner);
			{
				accidentCheckBox.addChangeListener(this);
				{
					penaliteLabel.setFont(UIManager.getFont("Viewport.font"));
					GridBagConstraints gbc_penaliteLabel = new GridBagConstraints();
					gbc_penaliteLabel.anchor = GridBagConstraints.EAST;
					gbc_penaliteLabel.insets = new Insets(0, 0, 5, 5);
					gbc_penaliteLabel.gridx = 2;
					gbc_penaliteLabel.gridy = 5;
					renduePanel.add(penaliteLabel, gbc_penaliteLabel);
				}
				GridBagConstraints gbc_accidentCheckBox = new GridBagConstraints();
				gbc_accidentCheckBox.insets = new Insets(0, 0, 5, 5);
				gbc_accidentCheckBox.gridwidth = 2;
				gbc_accidentCheckBox.gridx = 0;
				gbc_accidentCheckBox.gridy = 6;
				renduePanel.add(accidentCheckBox, gbc_accidentCheckBox);
			}
			{

				fraisRemiseLabel.setFont(UIManager.getFont("Viewport.font"));
				GridBagConstraints gbc_fraisRemiseLabel = new GridBagConstraints();
				gbc_fraisRemiseLabel.anchor = GridBagConstraints.EAST;
				gbc_fraisRemiseLabel.insets = new Insets(0, 0, 5, 5);
				gbc_fraisRemiseLabel.gridx = 2;
				gbc_fraisRemiseLabel.gridy = 6;
				renduePanel.add(fraisRemiseLabel, gbc_fraisRemiseLabel);
			}
			{
				{
					JLabel consommationLabel = new JLabel("consommation");
					GridBagConstraints gbc_consommationLabel = new GridBagConstraints();
					gbc_consommationLabel.anchor = GridBagConstraints.EAST;
					gbc_consommationLabel.insets = new Insets(0, 0, 0, 5);
					gbc_consommationLabel.gridx = 0;
					gbc_consommationLabel.gridy = 7;
					renduePanel.add(consommationLabel, gbc_consommationLabel);
				}
			}
			consommationSlider.addChangeListener(this);
			consommationSlider.setValue(1);
			consommationSlider.setMaximum(4);
			GridBagConstraints gbc_consommationSlider = new GridBagConstraints();
			gbc_consommationSlider.fill = GridBagConstraints.HORIZONTAL;
			gbc_consommationSlider.insets = new Insets(0, 0, 0, 5);
			gbc_consommationSlider.gridx = 1;
			gbc_consommationSlider.gridy = 7;
			renduePanel.add(consommationSlider, gbc_consommationSlider);
			{
				supplementLabel.setFont(UIManager.getFont("Viewport.font"));
				GridBagConstraints gbc_supplementLabel = new GridBagConstraints();
				gbc_supplementLabel.insets = new Insets(0, 0, 0, 5);
				gbc_supplementLabel.anchor = GridBagConstraints.EAST;
				gbc_supplementLabel.gridx = 2;
				gbc_supplementLabel.gridy = 7;
				renduePanel.add(supplementLabel, gbc_supplementLabel);
			}
		}
		{
			JPanel montantPanel = new JPanel();
			montantPanel.setBorder(BorderFactory.createTitledBorder("Montant à régler"));
			contentPanel.add(montantPanel, BorderLayout.SOUTH);
			montantPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				montantLabel.setFont(montantLabel.getFont().deriveFont(montantLabel.getFont().getSize() + 4f));
				montantPanel.add(montantLabel);
			}

		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}

		pack();
	}

}
