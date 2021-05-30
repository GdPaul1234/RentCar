package view.component.viewer;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import model.ProgrammeFidelite;

public class ProgrammeFideliteViewer extends JPanel {

	private static final long serialVersionUID = -5896799807298529661L;

	private ProgrammeFidelite prgmFidelite;
	private JLabel expirationLabel = new JLabel();

	/**
	 * Create the panel.
	 */
	public ProgrammeFideliteViewer(ProgrammeFidelite prgmFidelite) {
		this.prgmFidelite = prgmFidelite;
		createUI();
	}

	public void setExpirationDate(Date dateExpiration) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/YYYY");
		expirationLabel.setText("<html><center><strong>Expire le : " + dateFormatter.format(dateExpiration)
				+ "</strong></center></html>");
	}

	private void createUI() {
		setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 211, 0 };
		gridBagLayout.rowHeights = new int[] { 16, 16, 32, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		{
			JLabel descLabel = new JLabel("<html><center>" + prgmFidelite.getDescription() + "</center></html>");
			descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			descLabel.setFont(UIManager.getFont("Viewport.font"));
			GridBagConstraints gbc_descLabel = new GridBagConstraints();
			gbc_descLabel.insets = new Insets(0, 0, 5, 0);
			gbc_descLabel.gridx = 0;
			gbc_descLabel.gridy = 0;
			add(descLabel, gbc_descLabel);
		}

		{
			expirationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			expirationLabel.setText("<html><strong>Expire le : ... </strong><html>");
			expirationLabel.setFont(UIManager.getFont("Viewport.font"));
			GridBagConstraints gbc_expirationLabel = new GridBagConstraints();
			gbc_expirationLabel.insets = new Insets(0, 0, 5, 0);
			gbc_expirationLabel.gridx = 0;
			gbc_expirationLabel.gridy = 1;
			add(expirationLabel, gbc_expirationLabel);
		}

		{
			JLabel prixLabel = new JLabel(String.format(
					"<html><center><strong>Prix : </strong><em>%.2f €</em></center></html>", prgmFidelite.getPrix()));
			prixLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			prixLabel.setFont(UIManager.getFont("Viewport.font"));
			GridBagConstraints gbc_prixLabel = new GridBagConstraints();
			gbc_prixLabel.insets = new Insets(0, 0, 5, 0);
			gbc_prixLabel.gridx = 0;
			gbc_prixLabel.gridy = 2;
			add(prixLabel, gbc_prixLabel);
		}

		{
			JLabel reductionLabel = new JLabel(String.format(
					"<html><center>Offre une réduction de <em>%.0f %%</em> sur les locations</center></html>",
					prgmFidelite.getReduction().multiply(new BigDecimal(100))));
			reductionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			reductionLabel.setFont(UIManager.getFont("Viewport.font"));
			GridBagConstraints gbc_reductionLabel = new GridBagConstraints();
			gbc_reductionLabel.gridx = 0;
			gbc_reductionLabel.gridy = 3;
			add(reductionLabel, gbc_reductionLabel);
		}
	}

}
