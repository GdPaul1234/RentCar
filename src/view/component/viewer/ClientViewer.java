package view.component.viewer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import model.Client;

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
			ProgrammeFideliteViewer fidBox = new ProgrammeFideliteViewer(client.getProgrammeFidelite(),
					Calendar.getInstance().getTime());
			fidelitePanel.add(fidBox);
		} else {
			JLabel emptyLabel = new JLabel("<html>Aucun programme de fidélité</html>");
			emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			emptyLabel.setFont(UIManager.getFont("Viewport.font"));
			fidelitePanel.add(emptyLabel);
		}

	}

}
