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
import java.sql.SQLException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.ClientDAO;
import controller.LocationDAO;
import model.Client;
import model.Devis;
import model.enums.TypeCategorie;
import view.component.viewer.ClientViewer;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class CreateClientReservation extends JDialog implements ActionListener, ChangeListener {

	private static final long serialVersionUID = -2622050431594175169L;
	private JDialog frame = this;
	private WaitingDialog waiting;

	private JComboBox<?> categorieComboBox = new JComboBox<>(TypeCategorie.getValues());
	private JLabel prixCategorieLabel = new JLabel("-- €");
	private JSpinner dateStartSpinner = new JSpinner();
	private JSpinner dateEndSpinner = new JSpinner();
	private JCheckBox assuranceCheckBox = new JCheckBox("<html>Assurance dégradation<br> et accidents</html>");

	private JLabel montantLabel = new JLabel();

	private Client client;
	private float tarifCategorie;
	private float reduction;
	private long dateDiff;

	/**
	 * Create the dialog.
	 */
	public CreateClientReservation(Client client) {
		this.client = client;
		createUI();

		// update data
		new GetDataTask((TypeCategorie) categorieComboBox.getSelectedItem()).execute();
	}

	public void run(Component frame) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(frame);
		setResizable(false);
		setModal(true);
		setVisible(true);
	}

	class GetDataTask extends SwingWorker<Void, Void> {
		private TypeCategorie categorie;

		GetDataTask(TypeCategorie categorie) {
			this.categorie = categorie;
		}

		@Override
		protected Void doInBackground() throws Exception {
			BigDecimal[] response = new ClientDAO().getCategoriePrixReduction(client.getPersonneID(), categorie);
			tarifCategorie = response[0].floatValue();
			reduction = response[1].floatValue();

			// maj preview montant
			updateMontant();

			return null;
		}

	}

	class ApplyReservationTask extends SwingWorker<Void, Void> {
		private boolean error = false;

		@Override
		protected Void doInBackground() throws Exception {
			try {
				java.sql.Date dateStart = new java.sql.Date(
						((SpinnerDateModel) dateStartSpinner.getModel()).getDate().getTime());
				new LocationDAO()
						.addReservation(new Devis(dateStart, Math.toIntExact(dateDiff), assuranceCheckBox.isSelected(),
								client.getPersonneID(), (TypeCategorie) categorieComboBox.getSelectedItem()));
				error = false;
			} catch (SQLException e) {
				error = true;
				JOptionPane.showMessageDialog(frame, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void done() {
			waiting.close();

			if (!error)
				dispose();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		switch (action) {
		case "update":
			new GetDataTask((TypeCategorie) categorieComboBox.getSelectedItem()).execute();
			break;

		case "OK":
			// Launch wait UI
			waiting = new WaitingDialog(frame);
			new ApplyReservationTask().execute();
			break;

		case "Cancel":
			dispose();
			break;

		default:
			break;
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public void stateChanged(ChangeEvent e) {
		// Value changed
		Date dateStart = ((SpinnerDateModel) dateStartSpinner.getModel()).getDate();
		dateStart.setHours(0);
		dateStart.setMinutes(0);
		dateStart.setSeconds(0);

		Date dateEnd = ((SpinnerDateModel) dateEndSpinner.getModel()).getDate();
		dateEnd.setHours(0);
		dateEnd.setMinutes(0);
		dateEnd.setSeconds(1);

		dateDiff = (dateEnd.getTime() - dateStart.getTime()) / (24 * 3600000);
		// update Montant
		updateMontant();
	}

	private void updateMontant() {
		// update tarif
		prixCategorieLabel.setText(String.format("%.2f €", tarifCategorie));

		// update Montant
		double montant = (dateDiff * tarifCategorie) * (1.0 - reduction);
		if (assuranceCheckBox.isSelected())
			montant += 19.5;

		montantLabel.setText(montant >= 0 ? String.format("%.2f €", montant) : "Date invalide");

		// update UI
		invalidate();
	}

	private void createUI() {
		setTitle("Nouvelle réservation");
		getContentPane().setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel();
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(5, 5));
		{
			Box reservationPanel = Box.createVerticalBox();
			contentPanel.add(reservationPanel, BorderLayout.CENTER);
			{
				{
					JPanel reservationFormPanel = new JPanel();
					reservationFormPanel.setBorder(BorderFactory.createTitledBorder("Réservation"));
					reservationPanel.add(reservationFormPanel);
					GridBagLayout gbl_reservationFormPanel = new GridBagLayout();
					gbl_reservationFormPanel.columnWeights = new double[] { 0.0, 0.0, 0.0 };
					gbl_reservationFormPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
					reservationFormPanel.setLayout(gbl_reservationFormPanel);
					{
						JLabel categorieLabel = new JLabel("Catégorie");
						GridBagConstraints gbc_categorieLabel = new GridBagConstraints();
						gbc_categorieLabel.insets = new Insets(0, 0, 5, 5);
						gbc_categorieLabel.anchor = GridBagConstraints.EAST;
						gbc_categorieLabel.gridx = 0;
						gbc_categorieLabel.gridy = 0;
						reservationFormPanel.add(categorieLabel, gbc_categorieLabel);
					}
					{
						categorieComboBox.setActionCommand("update");
						categorieComboBox.addActionListener(this);
						GridBagConstraints gbc_categorieComboBox = new GridBagConstraints();
						gbc_categorieComboBox.insets = new Insets(0, 0, 5, 5);
						gbc_categorieComboBox.anchor = GridBagConstraints.NORTH;
						gbc_categorieComboBox.gridx = 1;
						gbc_categorieComboBox.gridy = 0;
						reservationFormPanel.add(categorieComboBox, gbc_categorieComboBox);
					}
					{
						GridBagConstraints gbc_prixCategorieLabel = new GridBagConstraints();
						gbc_prixCategorieLabel.insets = new Insets(0, 0, 5, 0);
						gbc_prixCategorieLabel.gridx = 2;
						gbc_prixCategorieLabel.gridy = 0;
						reservationFormPanel.add(prixCategorieLabel, gbc_prixCategorieLabel);
					}
					{
						JLabel startDateLabel = new JLabel("Début");
						GridBagConstraints gbc_startDateLabel = new GridBagConstraints();
						gbc_startDateLabel.anchor = GridBagConstraints.EAST;
						gbc_startDateLabel.insets = new Insets(0, 0, 5, 5);
						gbc_startDateLabel.gridx = 0;
						gbc_startDateLabel.gridy = 1;
						reservationFormPanel.add(startDateLabel, gbc_startDateLabel);
					}
					{
						dateStartSpinner.setModel(new SpinnerDateModel());
						dateStartSpinner.getModel().addChangeListener(this);
						GridBagConstraints gbc_dateStartSpinner = new GridBagConstraints();
						gbc_dateStartSpinner.insets = new Insets(0, 0, 5, 5);
						gbc_dateStartSpinner.gridx = 1;
						gbc_dateStartSpinner.gridy = 1;
						reservationFormPanel.add(dateStartSpinner, gbc_dateStartSpinner);
					}
					{
						JLabel endStartLabel = new JLabel("Fin");
						GridBagConstraints gbc_endStartLabel = new GridBagConstraints();
						gbc_endStartLabel.anchor = GridBagConstraints.EAST;
						gbc_endStartLabel.insets = new Insets(0, 0, 5, 5);
						gbc_endStartLabel.gridx = 0;
						gbc_endStartLabel.gridy = 2;
						reservationFormPanel.add(endStartLabel, gbc_endStartLabel);
					}
					{
						dateEndSpinner.setModel(new SpinnerDateModel());
						dateEndSpinner.getModel().addChangeListener(this);
						GridBagConstraints gbc_dateEndSpinner = new GridBagConstraints();
						gbc_dateEndSpinner.insets = new Insets(0, 0, 5, 5);
						gbc_dateEndSpinner.gridx = 1;
						gbc_dateEndSpinner.gridy = 2;
						reservationFormPanel.add(dateEndSpinner, gbc_dateEndSpinner);
					}
					{
						assuranceCheckBox.setActionCommand("update");
						assuranceCheckBox.addChangeListener(this);
						assuranceCheckBox.setFont(UIManager.getFont("Viewport.font"));
						GridBagConstraints gbc_AssuranceCheckBox = new GridBagConstraints();
						gbc_AssuranceCheckBox.anchor = GridBagConstraints.EAST;
						gbc_AssuranceCheckBox.gridwidth = 2;
						gbc_AssuranceCheckBox.insets = new Insets(0, 0, 0, 5);
						gbc_AssuranceCheckBox.gridx = 0;
						gbc_AssuranceCheckBox.gridy = 3;
						reservationFormPanel.add(assuranceCheckBox, gbc_AssuranceCheckBox);
					}
					{
						JLabel montantAssuranceLabel = new JLabel("19,50 €");
						GridBagConstraints gbc_montantAssuranceLabel = new GridBagConstraints();
						gbc_montantAssuranceLabel.gridx = 2;
						gbc_montantAssuranceLabel.gridy = 3;
						reservationFormPanel.add(montantAssuranceLabel, gbc_montantAssuranceLabel);
					}
				}
			}

			JPanel montantEstimePanel = new JPanel();
			montantEstimePanel.setBorder(BorderFactory.createTitledBorder("Montant estimé"));
			reservationPanel.add(montantEstimePanel);
			{
				montantLabel.setText("--,-- €");
				montantLabel.setFont(montantLabel.getFont().deriveFont(montantLabel.getFont().getSize() + 4f));
				montantEstimePanel.add(montantLabel);
			}

			{
				ClientViewer clientViewer = new ClientViewer((Client) client);
				contentPanel.add(clientViewer, BorderLayout.EAST);
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
