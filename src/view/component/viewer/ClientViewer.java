package view.component.viewer;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import model.Client;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.awt.BorderLayout;

public class ClientViewer extends JPanel {
	private static final long serialVersionUID = 3712441655271629781L;

	/**
	 * Create the panel.
	 */
	public ClientViewer(Client client) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		Box clientPanel = Box.createVerticalBox();
		clientPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		clientPanel.setBorder(BorderFactory.createTitledBorder("Client"));
		add(clientPanel, BorderLayout.CENTER);
		{
			clientPanel.add(Box.createGlue());
			
			Box infoPersoPanel = Box.createHorizontalBox();
			infoPersoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			clientPanel.add(infoPersoPanel);
			{
				JLabel nomLabel = new JLabel(client.getNom() + ", " + client.getPrenom());
				infoPersoPanel.add(nomLabel);
			}
			
			clientPanel.add(Box.createGlue());

			Box contactPanel = Box.createVerticalBox();
			clientPanel.add(contactPanel);
			{
				JLabel emailLabel = new JLabel(client.getEmail());
				emailLabel.setFont(UIManager.getFont("Viewport.font"));
				contactPanel.add(emailLabel);

				JLabel telLabel = new JLabel(client.getTelephone());
				telLabel.setFont(UIManager.getFont("Viewport.font"));
				contactPanel.add(telLabel);
			}
			
			clientPanel.add(Box.createGlue());

			Box adressePanel = Box.createVerticalBox();
			clientPanel.add(adressePanel);
			{
				JLabel rueLabel = new JLabel(client.getAdresse().getRue());
				rueLabel.setFont(UIManager.getFont("Viewport.font"));
				adressePanel.add(rueLabel);

				Box villePanel = Box.createHorizontalBox();
				villePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
				adressePanel.add(villePanel);
				{
					JLabel cpLabel = new JLabel(client.getAdresse().getCodePostal());
					cpLabel.setFont(UIManager.getFont("Viewport.font"));
					villePanel.add(cpLabel);

					Component rigidArea = Box.createRigidArea(new Dimension(15, 15));
					villePanel.add(rigidArea);

					JLabel villeLabel = new JLabel(client.getAdresse().getVille());
					villeLabel.setFont(UIManager.getFont("Viewport.font"));
					villePanel.add(villeLabel);
				}
			}
			
			clientPanel.add(Box.createGlue());
		}

		Box fidelitePanel = Box.createVerticalBox();
		fidelitePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		fidelitePanel.setBorder(BorderFactory.createTitledBorder("Programme Fidelité"));
		add(fidelitePanel, BorderLayout.SOUTH);
		if (client.getProgrammeFidelite() != null) {
			fidelitePanel.add(Box.createGlue());
			
			JLabel descLabel = new JLabel(client.getProgrammeFidelite().getDescription());
			descLabel.setFont(UIManager.getFont("Viewport.font"));
			fidelitePanel.add(descLabel);

			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/YYYY");
			
			fidelitePanel.add(Box.createGlue());
			
			JLabel expirationLabel = new JLabel(String.format("<html><strong>Expire le :</strong> %s</html>",
					dateFormatter.format(client.getExpirationDate())));
			expirationLabel.setFont(UIManager.getFont("Viewport.font"));
			fidelitePanel.add(expirationLabel);
			
			fidelitePanel.add(Box.createGlue());

			JLabel prixLabel = new JLabel(String.format("<html><strong>Prix : </strong><em>%.2f €</em></html>",
					client.getProgrammeFidelite().getPrix()));
			prixLabel.setFont(UIManager.getFont("Viewport.font"));
			fidelitePanel.add(prixLabel);
			
			fidelitePanel.add(Box.createGlue());

			JLabel reductionLabel = new JLabel(
					String.format("<html>Offre une réduction de <em>%.0f %%</em> sur les locations</html>",
							100 * client.getProgrammeFidelite().getReduction()));
			reductionLabel.setFont(UIManager.getFont("Viewport.font"));
			fidelitePanel.add(reductionLabel);
			
			Component glue_4 = Box.createGlue();
			fidelitePanel.add(glue_4);
		} else {
			JLabel emptyLabel = new JLabel("<html>Aucun programme de fidélité</html>");
			emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			emptyLabel.setFont(UIManager.getFont("Viewport.font"));
			fidelitePanel.add(emptyLabel);
		}

	}

}
