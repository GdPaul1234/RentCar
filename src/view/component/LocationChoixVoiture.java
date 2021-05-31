package view.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.AgenceDAO;
import controller.LocationDAO;
import model.Agence;
import model.Client;
import model.Devis;
import model.Vehicule;
import view.component.viewer.ClientViewer;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class LocationChoixVoiture extends JDialog implements ActionListener, ChangeListener {

	private static final long serialVersionUID = 8404941819774905251L;
	private JDialog frame = this;

	private final JPanel contentPanel = new JPanel();
	private TitledBorder titledBorder = BorderFactory.createTitledBorder("Choisir voiture <Catégorie>");
	private JCheckBox assuranceCheckBox = new JCheckBox("<html>Assurance dégradation et accidents</html>");
	private RessourceSelector ressourceSelector = new RessourceSelector(Vehicule.getHeader());
	private JSpinner dateStartSpinner = new JSpinner();
	private JLabel dureeLabel = new JLabel("-- jours");

	private Client client;
	private Devis devis;
	private List<Vehicule> vehiculesCategorie;

	public void run(Component frame) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(frame);
		setResizable(false);
		setModal(true);
		setVisible(true);
	}

	/**
	 * Create the dialog.
	 */
	public LocationChoixVoiture(Client client) {
		this.client = client;
		createUI();
		new GetDevisTask().execute();
	}

	class GetDevisTask extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			Date dateStart = new Date(((SpinnerDateModel) dateStartSpinner.getModel()).getDate().getTime());

			LocationDAO locationDAO = new LocationDAO();
			devis = locationDAO.getDevis(client.getPersonneID(), dateStart);

			if (devis != null)
				vehiculesCategorie = locationDAO.getVehiculeDispoByCategorie(devis.getCategorie());
			else
				vehiculesCategorie = new ArrayList<Vehicule>();

			return null;
		}

		@Override
		protected void done() {
			updateUI();
		}

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// update current devis view
		new GetDevisTask().execute();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
		case "OK":
			CompletableFuture.runAsync(new Runnable() {

				@Override
				public void run() {
					try {
						new LocationDAO().addLocation(client.getPersonneID(),
								new java.sql.Date(((SpinnerDateModel) dateStartSpinner.getModel()).getDate().getTime()),
								(String) ressourceSelector.getSelectedRessourceID());

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

	private void updateUI() {
		if (devis != null) {

			titledBorder.setTitle("Choisir voiture " + devis.getCategorie().toString());
			dureeLabel.setText(devis.getDureeLocation() + " jours");
			assuranceCheckBox.setSelected(devis.isAssurance());

		} else {
			// reset all
			titledBorder.setTitle("Choisir voiture <Catégorie>");
			dureeLabel.setText("-- jours");
			assuranceCheckBox.setSelected(false);

		}

		// refresh table
		ressourceSelector.refreshTable(vehiculesCategorie);
		ressourceSelector.resizeColumns(Vehicule.getColumnsWidth());

		invalidate();
		repaint();
	}

	private void createUI() {
		setTitle("Nouvelle location");

		setBounds(100, 100, 700, 380);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			ressourceSelector.setBorder(titledBorder);
			contentPanel.add(ressourceSelector, BorderLayout.CENTER);

			{
				Box infoBox = Box.createVerticalBox();
				contentPanel.add(infoBox, BorderLayout.EAST);
				{
					ClientViewer clientViewer = new ClientViewer((Client) client);
					infoBox.add(clientViewer);
				}
				{
					Box optionPanel = Box.createHorizontalBox();
					optionPanel.setBorder(BorderFactory.createTitledBorder("Options"));
					infoBox.add(optionPanel);
					{
						assuranceCheckBox.setEnabled(false);
						optionPanel.add(assuranceCheckBox);
					}
				}
			}

		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
			{
				JLabel dateStartLabel = new JLabel("Début location");
				panel.add(dateStartLabel);
			}
			{
				dateStartSpinner.setModel(new SpinnerDateModel());
				dateStartSpinner.addChangeListener(this);
				panel.add(dateStartSpinner);
			}
			{
				panel.add(dureeLabel);
			}
			{
				JSeparator separator = new JSeparator();
				panel.add(separator);
			}
			{
				JLabel agenceLabel = new JLabel("Agence");
				panel.add(agenceLabel);
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

	}

}
