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
import java.util.Calendar;
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
			
			JLabel nomLabel = new JLabel(client.getNom() + ", " + client.getPrenom());
			nomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			clientPanel.add(nomLabel);

			clientPanel.add(Box.createGlue());

			Box contactPanel = Box.createVerticalBox();
			contactPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
			clientPanel.add(contactPanel);
			{
				JLabel emailLabel = new JLabel(client.getEmail());
				emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				emailLabel.setFont(UIManager.getFont("Viewport.font"));
				contactPanel.add(emailLabel);

				JLabel telLabel = new JLabel(client.getTelephone());
				telLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				telLabel.setFont(UIManager.getFont("Viewport.font"));
				contactPanel.add(telLabel);
			}

			clientPanel.add(Box.createGlue());

			AdresseViewer adressePanel = new AdresseViewer(client.getAdresse());
			adressePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
			clientPanel.add(adressePanel);

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
					dateFormatter.format(Calendar.getInstance().getTime())));
			expirationLabel.setFont(UIManager.getFont("Viewport.font"));
			fidelitePanel.add(expirationLabel);

			fidelitePanel.add(Box.createGlue());

			JLabel prixLabel = new JLabel(String.format("<html><strong>Prix : </strong><em>%.2f €</em></html>",
					client.getProgrammeFidelite().getPrix()));
			prixLabel.setFont(UIManager.getFont("Viewport.font"));
			fidelitePanel.add(prixLabel);

			fidelitePanel.add(Box.createGlue());

			JLabel reductionLabel = new JLabel(
					String.format("<html>Offre une réduction de <em>%.0f %%</em><br>sur les locations</html>",
							100 * client.getProgrammeFidelite().getReduction()));
			reductionLabel.setFont(UIManager.getFont("Viewport.font"));
			fidelitePanel.add(reductionLabel);

			fidelitePanel.add(Box.createGlue());
		} else {
			JLabel emptyLabel = new JLabel("<html>Aucun programme de fidélité</html>");
			emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			emptyLabel.setFont(UIManager.getFont("Viewport.font"));
			fidelitePanel.add(emptyLabel);
		}

	}

}
