package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Time;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import view.component.viewer.ClientViewer;
import model.Adresse;
import model.Client;
import model.ProgrammeFidelite;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;

public class ManageClientView extends JDialog {
	private static final long serialVersionUID = 8605274728883691615L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			// test
			Client client = new Client("Nom", "Prenom", "nom.prenom@mail.com", "+33 (0) 1 23 45 67 89",
					new Adresse("9, rue de la Paix", "Paris", "75001"));
			client.addSouscription(new ProgrammeFidelite(0, "Test description prgm fidélité",
					new Time(2 * 365 * 24 * 3600), 35.50f, 0.25f));

			ManageClientView dialog = new ManageClientView(client);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ManageClientView(Client client) {
		setResizable(false);

		getContentPane().setLayout(new BorderLayout());
		{
			ClientViewer clientViewer = new ClientViewer(client);
			getContentPane().add(clientViewer, BorderLayout.EAST);

			Box clientManagePanel = Box.createVerticalBox();
			getContentPane().add(clientManagePanel, BorderLayout.CENTER);
			{
				JPanel gestionLocationPanel = new JPanel();
				gestionLocationPanel.setBorder(BorderFactory.createTitledBorder("Locations"));
				GridBagLayout gbl_gestionLocationPanel = new GridBagLayout();
				gbl_gestionLocationPanel.columnWidths = new int[] { 140, 140 };
				gbl_gestionLocationPanel.columnWeights = new double[] { 0.0, 0.0 };
				gbl_gestionLocationPanel.rowWeights = new double[] { 0.0, 0.0, 0.0 };
				gestionLocationPanel.setLayout(gbl_gestionLocationPanel);
				clientManagePanel.add(gestionLocationPanel);
				{
					{
						JButton reservationButton = new JButton("Réservation");
						GridBagConstraints gbc_reservationButton = new GridBagConstraints();
						gbc_reservationButton.fill = GridBagConstraints.HORIZONTAL;
						gbc_reservationButton.insets = new Insets(0, 0, 5, 5);
						gbc_reservationButton.gridx = 0;
						gbc_reservationButton.gridy = 0;
						gestionLocationPanel.add(reservationButton, gbc_reservationButton);

						JLabel reservationLabel = new JLabel("Nouveau devis");
						reservationLabel.setFont(UIManager.getFont("Viewport.font"));
						GridBagConstraints gbc_reservationLabel = new GridBagConstraints();
						gbc_reservationLabel.fill = GridBagConstraints.VERTICAL;
						gbc_reservationLabel.insets = new Insets(0, 0, 5, 5);
						gbc_reservationLabel.gridx = 1;
						gbc_reservationLabel.gridy = 0;
						gestionLocationPanel.add(reservationLabel, gbc_reservationLabel);
					}

					{
						JButton locationButton = new JButton("Location");
						GridBagConstraints gbc_locationButton = new GridBagConstraints();
						gbc_locationButton.fill = GridBagConstraints.HORIZONTAL;
						gbc_locationButton.insets = new Insets(0, 0, 5, 5);
						gbc_locationButton.gridx = 0;
						gbc_locationButton.gridy = 1;
						gestionLocationPanel.add(locationButton, gbc_locationButton);

						JLabel locationLabel = new JLabel("Choix du véhicule");
						locationLabel.setFont(UIManager.getFont("Viewport.font"));
						GridBagConstraints gbc_locationLabel = new GridBagConstraints();
						gbc_locationLabel.fill = GridBagConstraints.VERTICAL;
						gbc_locationLabel.insets = new Insets(0, 0, 5, 5);
						gbc_locationLabel.gridx = 1;
						gbc_locationLabel.gridy = 1;
						gestionLocationPanel.add(locationLabel, gbc_locationLabel);
					}

					{
						JButton factureButton = new JButton("Rendue véhicule");
						GridBagConstraints gbc_factureButton = new GridBagConstraints();
						gbc_factureButton.fill = GridBagConstraints.HORIZONTAL;
						gbc_factureButton.insets = new Insets(0, 0, 0, 5);
						gbc_factureButton.gridx = 0;
						gbc_factureButton.gridy = 2;
						gestionLocationPanel.add(factureButton, gbc_factureButton);

						JLabel rendueLabel = new JLabel("Gestion des factures");
						rendueLabel.setFont(UIManager.getFont("Viewport.font"));
						GridBagConstraints gbc_rendueLabel = new GridBagConstraints();
						gbc_rendueLabel.fill = GridBagConstraints.VERTICAL;
						gbc_rendueLabel.insets = new Insets(0, 0, 0, 5);
						gbc_rendueLabel.gridx = 1;
						gbc_rendueLabel.gridy = 2;
						gestionLocationPanel.add(rendueLabel, gbc_rendueLabel);
					}
				}

				JPanel gestionFidelite = new JPanel();
				gestionFidelite.setBorder(BorderFactory.createTitledBorder("Fidélité"));
				GridBagLayout gbl_gestionFidelite = new GridBagLayout();
				gbl_gestionFidelite.columnWidths = new int[] { 140, 140 };
				gbl_gestionFidelite.columnWeights = new double[] { 0.0, 0.0 };
				gbl_gestionFidelite.rowWeights = new double[] { 0.0 };
				gestionFidelite.setLayout(gbl_gestionFidelite);
				clientManagePanel.add(gestionFidelite);
				{
					{
						JButton souscriptionButton = new JButton("Souscrire");
						GridBagConstraints gbc_souscriptionButton = new GridBagConstraints();
						gbc_souscriptionButton.fill = GridBagConstraints.HORIZONTAL;
						gbc_souscriptionButton.insets = new Insets(0, 0, 0, 5);
						gbc_souscriptionButton.gridx = 0;
						gbc_souscriptionButton.gridy = 0;
						gestionFidelite.add(souscriptionButton, gbc_souscriptionButton);
					}

					{
						JLabel souscriptionLabel = new JLabel("<html><center>Inscription,<br>Renouvellement</center></html>");
						souscriptionLabel.setFont(UIManager.getFont("Viewport.font"));
						GridBagConstraints gbc_souscriptionLabel = new GridBagConstraints();
						gbc_souscriptionLabel.insets = new Insets(0, 0, 0, 5);
						gbc_souscriptionLabel.fill = GridBagConstraints.VERTICAL;
						gbc_souscriptionLabel.gridx = 1;
						gbc_souscriptionLabel.gridy = 0;
						gestionFidelite.add(souscriptionLabel, gbc_souscriptionLabel);
					}
				}

			}
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		pack();
		
	}

}
