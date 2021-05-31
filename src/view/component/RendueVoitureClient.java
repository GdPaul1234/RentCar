package view.component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import view.component.viewer.ClientViewer;
import model.Client;
import view.component.viewer.VehicleViewer;
import model.Vehicule;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import java.awt.Insets;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.UIManager;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JComboBox;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class RendueVoitureClient extends JDialog {
	
	private static final long serialVersionUID = -6017187287474330845L;

	private final JPanel contentPanel = new JPanel();
	private ClientViewer clientViewer;
	private VehicleViewer vehicleViewer;
	private JSpinner dateRendueSpinner = new JSpinner();
	private JLabel penaliteLabel = new JLabel("--,-- €");
	private JCheckBox accidentCheckBox = new JCheckBox("accident, dégradation");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RendueVoitureClient dialog = new RendueVoitureClient();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RendueVoitureClient() {
		setTitle("Rendre véhicule");
		setBounds(100, 100, 450, 368);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel infoPanel = new JPanel();
			contentPanel.add(infoPanel, BorderLayout.NORTH);
			{
				clientViewer = new ClientViewer((Client) null);
				infoPanel.add(clientViewer);
			}
			{
				vehicleViewer = new VehicleViewer((Vehicule) null);
				infoPanel.add(vehicleViewer);
			}
		}
		{
			JPanel renduePanel = new JPanel();
			renduePanel.setBorder(BorderFactory.createTitledBorder("Rendue véhicule"));
			contentPanel.add(renduePanel, BorderLayout.CENTER);
			GridBagLayout gbl_renduePanel = new GridBagLayout();
			gbl_renduePanel.columnWidths = new int[] {50, 100, 25};
			gbl_renduePanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
			gbl_renduePanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
			renduePanel.setLayout(gbl_renduePanel);
			{
				JLabel dateStartLabel = new JLabel("début location");
				GridBagConstraints gbc_dateStartLabel = new GridBagConstraints();
				gbc_dateStartLabel.anchor = GridBagConstraints.EAST;
				gbc_dateStartLabel.insets = new Insets(0, 0, 5, 5);
				gbc_dateStartLabel.gridx = 1;
				gbc_dateStartLabel.gridy = 0;
				renduePanel.add(dateStartLabel, gbc_dateStartLabel);
			}
			{
				JSpinner dateStartSpinner = new JSpinner();
				dateStartSpinner.setModel(new SpinnerDateModel());
				GridBagConstraints gbc_dateStartSpinner = new GridBagConstraints();
				gbc_dateStartSpinner.insets = new Insets(0, 0, 5, 5);
				gbc_dateStartSpinner.gridx = 2;
				gbc_dateStartSpinner.gridy = 0;
				renduePanel.add(dateStartSpinner, gbc_dateStartSpinner);
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
				JLabel agenceLabel = new JLabel("Agence");
				GridBagConstraints gbc_agenceLabel = new GridBagConstraints();
				gbc_agenceLabel.anchor = GridBagConstraints.EAST;
				gbc_agenceLabel.insets = new Insets(0, 0, 5, 5);
				gbc_agenceLabel.gridx = 1;
				gbc_agenceLabel.gridy = 2;
				renduePanel.add(agenceLabel, gbc_agenceLabel);
			}
			{
				JComboBox agenceComboBox = new JComboBox();
				GridBagConstraints gbc_agenceComboBox = new GridBagConstraints();
				gbc_agenceComboBox.insets = new Insets(0, 0, 5, 5);
				gbc_agenceComboBox.gridx = 2;
				gbc_agenceComboBox.gridy = 2;
				renduePanel.add(agenceComboBox, gbc_agenceComboBox);
			}
			{
				JSeparator separator = new JSeparator();
				GridBagConstraints gbc_separator = new GridBagConstraints();
				gbc_separator.anchor = GridBagConstraints.EAST;
				gbc_separator.gridwidth = 3;
				gbc_separator.insets = new Insets(0, 0, 5, 0);
				gbc_separator.gridx = 1;
				gbc_separator.gridy = 3;
				renduePanel.add(separator, gbc_separator);
			}
			{
				JLabel dateRendue = new JLabel("date rendue");
				GridBagConstraints gbc_dateRendue = new GridBagConstraints();
				gbc_dateRendue.anchor = GridBagConstraints.EAST;
				gbc_dateRendue.insets = new Insets(0, 0, 5, 5);
				gbc_dateRendue.gridx = 1;
				gbc_dateRendue.gridy = 4;
				renduePanel.add(dateRendue, gbc_dateRendue);
			}
			{
				dateRendueSpinner.setModel(new SpinnerDateModel());
				GridBagConstraints gbc_dateRendueSpinner = new GridBagConstraints();
				gbc_dateRendueSpinner.insets = new Insets(0, 0, 5, 5);
				gbc_dateRendueSpinner.gridx = 2;
				gbc_dateRendueSpinner.gridy = 4;
				renduePanel.add(dateRendueSpinner, gbc_dateRendueSpinner);
			}
			{
				penaliteLabel.setFont(UIManager.getFont("Viewport.font"));
				GridBagConstraints gbc_penaliteLabel = new GridBagConstraints();
				gbc_penaliteLabel.insets = new Insets(0, 0, 5, 0);
				gbc_penaliteLabel.gridx = 3;
				gbc_penaliteLabel.gridy = 4;
				renduePanel.add(penaliteLabel, gbc_penaliteLabel);
			}
			{
				GridBagConstraints gbc_accidentCheckBox = new GridBagConstraints();
				gbc_accidentCheckBox.insets = new Insets(0, 0, 5, 5);
				gbc_accidentCheckBox.gridwidth = 2;
				gbc_accidentCheckBox.gridx = 1;
				gbc_accidentCheckBox.gridy = 5;
				renduePanel.add(accidentCheckBox, gbc_accidentCheckBox);
			}
			{
				JLabel fraisRemiseLabel = new JLabel("--,-- €");
				fraisRemiseLabel.setFont(UIManager.getFont("Viewport.font"));
				GridBagConstraints gbc_fraisRemiseLabel = new GridBagConstraints();
				gbc_fraisRemiseLabel.insets = new Insets(0, 0, 5, 0);
				gbc_fraisRemiseLabel.gridx = 3;
				gbc_fraisRemiseLabel.gridy = 5;
				renduePanel.add(fraisRemiseLabel, gbc_fraisRemiseLabel);
			}
			{
				JLabel consommationLabel = new JLabel("consommation");
				GridBagConstraints gbc_consommationLabel = new GridBagConstraints();
				gbc_consommationLabel.anchor = GridBagConstraints.EAST;
				gbc_consommationLabel.insets = new Insets(0, 0, 0, 5);
				gbc_consommationLabel.gridx = 1;
				gbc_consommationLabel.gridy = 6;
				renduePanel.add(consommationLabel, gbc_consommationLabel);
			}
			{
				JSlider slider = new JSlider();
				slider.setValue(1);
				slider.setMaximum(4);
				GridBagConstraints gbc_slider = new GridBagConstraints();
				gbc_slider.fill = GridBagConstraints.HORIZONTAL;
				gbc_slider.insets = new Insets(0, 0, 0, 5);
				gbc_slider.gridx = 2;
				gbc_slider.gridy = 6;
				renduePanel.add(slider, gbc_slider);
			}
			{
				JLabel supplementLabel = new JLabel("--,-- €");
				supplementLabel.setFont(UIManager.getFont("Viewport.font"));
				GridBagConstraints gbc_supplementLabel = new GridBagConstraints();
				gbc_supplementLabel.gridx = 3;
				gbc_supplementLabel.gridy = 6;
				renduePanel.add(supplementLabel, gbc_supplementLabel);
			}
		}
		{
			JPanel montantPanel = new JPanel();
			JLabel montantLabel = new JLabel();
			montantPanel.setBorder(BorderFactory.createTitledBorder("Montant à régler"));
			contentPanel.add(montantPanel, BorderLayout.EAST);
			montantPanel.setLayout(new BoxLayout(montantPanel, BoxLayout.LINE_AXIS));
			{
				montantLabel.setText("--,-- €");
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
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
