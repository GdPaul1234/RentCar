package view.component.viewer;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import model.Adresse;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class AdresseViewer extends JPanel {
	private static final long serialVersionUID = -7410136215189921295L;

	/**
	 * Create the panel.
	 */
	public AdresseViewer(Adresse adresse) {
		{
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

			JLabel rueLabel = new JLabel(adresse.getRue());
			rueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			rueLabel.setFont(UIManager.getFont("Viewport.font"));
			add(rueLabel);

			Box villePanel = Box.createHorizontalBox();
			add(villePanel);
			{
				JLabel cpLabel = new JLabel(adresse.getCodePostal());
				cpLabel.setFont(UIManager.getFont("Viewport.font"));
				villePanel.add(cpLabel);

				Component rigidArea = Box.createRigidArea(new Dimension(15, 15));
				villePanel.add(rigidArea);

				JLabel villeLabel = new JLabel(adresse.getVille());
				villeLabel.setFont(UIManager.getFont("Viewport.font"));
				villePanel.add(villeLabel);
			}
		}
	}

}
