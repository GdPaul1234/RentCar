package view.component.viewer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import model.Vehicule;

public class VehicleViewer extends JPanel {
	private static final long serialVersionUID = 8456087274597262693L;

	/**
	 * Create the panel.
	 */
	public VehicleViewer(Vehicule vehicle) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		JPanel vehiculePanel = new JPanel();
		vehiculePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		vehiculePanel.setBorder(BorderFactory.createTitledBorder("Véhicule"));
		add(vehiculePanel, BorderLayout.CENTER);
		GridBagLayout gbl_vehiculePanel = new GridBagLayout();
		gbl_vehiculePanel.columnWidths = new int[] { 0, 0 };
		gbl_vehiculePanel.columnWeights = new double[] { 0.0, 0.0 };
		gbl_vehiculePanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		vehiculePanel.setLayout(gbl_vehiculePanel);
		{
			{
				JLabel nomLabel = new JLabel(vehicle.getMarque() + ", " + vehicle.getModele());
				GridBagConstraints gbc_nomLabel = new GridBagConstraints();
				gbc_nomLabel.gridwidth = 2;
				gbc_nomLabel.insets = new Insets(0, 0, 5, 0);
				gbc_nomLabel.gridx = 0;
				gbc_nomLabel.gridy = 0;
				vehiculePanel.add(nomLabel, gbc_nomLabel);
				nomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			}

			{
				JLabel identificationLabel = new JLabel(
						vehicle.getMatricule() + ", " + vehicle.getKilometrage().toString() + " km");
				GridBagConstraints gbc_identificationLabel = new GridBagConstraints();
				gbc_identificationLabel.gridwidth = 2;
				gbc_identificationLabel.insets = new Insets(0, 0, 5, 5);
				gbc_identificationLabel.gridx = 0;
				gbc_identificationLabel.gridy = 1;
				vehiculePanel.add(identificationLabel, gbc_identificationLabel);
				identificationLabel.setFont(UIManager.getFont("Viewport.font"));
			}

			{
				JLabel boiteLabel = new JLabel("Boîte");
				GridBagConstraints gbc_boiteLabel = new GridBagConstraints();
				gbc_boiteLabel.anchor = GridBagConstraints.EAST;
				gbc_boiteLabel.insets = new Insets(0, 0, 5, 5);
				gbc_boiteLabel.gridx = 0;
				gbc_boiteLabel.gridy = 2;
				vehiculePanel.add(boiteLabel, gbc_boiteLabel);

				JLabel typeBoiteLabel = new JLabel(vehicle.getTypeBoite().toString());
				GridBagConstraints gbc_typeBoiteLabel = new GridBagConstraints();
				gbc_typeBoiteLabel.fill = GridBagConstraints.HORIZONTAL;
				gbc_typeBoiteLabel.insets = new Insets(0, 0, 5, 0);
				gbc_typeBoiteLabel.gridx = 1;
				gbc_typeBoiteLabel.gridy = 2;
				vehiculePanel.add(typeBoiteLabel, gbc_typeBoiteLabel);
				typeBoiteLabel.setFont(UIManager.getFont("Viewport.font"));
			}

			{
				JLabel carburantLabel = new JLabel("Carburant");
				GridBagConstraints gbc_carburantLabel = new GridBagConstraints();
				gbc_carburantLabel.anchor = GridBagConstraints.EAST;
				gbc_carburantLabel.insets = new Insets(0, 0, 5, 5);
				gbc_carburantLabel.gridx = 0;
				gbc_carburantLabel.gridy = 3;
				vehiculePanel.add(carburantLabel, gbc_carburantLabel);

				JLabel typeCarburantLabel = new JLabel(vehicle.getTypeCarburant().toString());
				GridBagConstraints gbc_typeCarburantLabel = new GridBagConstraints();
				gbc_typeCarburantLabel.fill = GridBagConstraints.HORIZONTAL;
				gbc_typeCarburantLabel.insets = new Insets(0, 0, 5, 0);
				gbc_typeCarburantLabel.gridx = 1;
				gbc_typeCarburantLabel.gridy = 3;
				vehiculePanel.add(typeCarburantLabel, gbc_typeCarburantLabel);
				typeCarburantLabel.setFont(UIManager.getFont("Viewport.font"));
			}

			{
				JLabel categorieLabel = new JLabel(vehicle.getCategorie().toString());
				GridBagConstraints gbc_categorieLabel = new GridBagConstraints();
				gbc_categorieLabel.fill = GridBagConstraints.HORIZONTAL;
				gbc_categorieLabel.gridx = 1;
				gbc_categorieLabel.gridy = 5;
				vehiculePanel.add(categorieLabel, gbc_categorieLabel);
				categorieLabel.setFont(UIManager.getFont("Viewport.font"));
			}

			{
				JCheckBox climatisationCheckBox = new JCheckBox("Climatisation");
				GridBagConstraints gbc_climatisationCheckBox = new GridBagConstraints();
				gbc_climatisationCheckBox.gridwidth = 2;
				gbc_climatisationCheckBox.insets = new Insets(0, 0, 5, 0);
				gbc_climatisationCheckBox.gridx = 0;
				gbc_climatisationCheckBox.gridy = 4;
				vehiculePanel.add(climatisationCheckBox, gbc_climatisationCheckBox);
				climatisationCheckBox.setSelected(vehicle.isClimatisation());
				climatisationCheckBox.setEnabled(false);

				JLabel catLabel = new JLabel("Catégorie");
				GridBagConstraints gbc_catLabel = new GridBagConstraints();
				gbc_catLabel.anchor = GridBagConstraints.EAST;
				gbc_catLabel.insets = new Insets(0, 0, 0, 5);
				gbc_catLabel.gridx = 0;
				gbc_catLabel.gridy = 5;
				vehiculePanel.add(catLabel, gbc_catLabel);
			}

		}

		JPanel agencePanel = new JPanel();
		agencePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		agencePanel.setBorder(BorderFactory.createTitledBorder("Agence"));
		add(agencePanel, BorderLayout.SOUTH);
		GridBagLayout gbl_agencePanel = new GridBagLayout();
		gbl_agencePanel.columnWeights = new double[] { };
		gbl_agencePanel.rowWeights = new double[] { };
		agencePanel.setLayout(gbl_agencePanel);
		if (vehicle.getAgence() != null) {
			JLabel nomLabel = new JLabel(vehicle.getAgence().getNom());
			GridBagConstraints gbc_nomLabel = new GridBagConstraints();
			gbc_nomLabel.insets = new Insets(0, 0, 5, 0);
			gbc_nomLabel.gridx = 0;
			gbc_nomLabel.gridy = 0;
			agencePanel.add(nomLabel, gbc_nomLabel);

			JLabel telLabel = new JLabel(vehicle.getAgence().getTelephone());
			telLabel.setFont(UIManager.getFont("Viewport.font"));
			GridBagConstraints gbc_telLabel = new GridBagConstraints();
			gbc_telLabel.insets = new Insets(0, 0, 5, 0);
			gbc_telLabel.gridx = 0;
			gbc_telLabel.gridy = 1;
			agencePanel.add(telLabel, gbc_telLabel);

			AdresseViewer adresseViewer = new AdresseViewer(vehicle.getAgence().getAdresse());
			GridBagConstraints gbc_adresseViewer = new GridBagConstraints();
			gbc_adresseViewer.gridx = 0;
			gbc_adresseViewer.gridy = 2;
			agencePanel.add(adresseViewer, gbc_adresseViewer);
		} else {
			JLabel enLocationLabel = new JLabel("Non disponible");
			GridBagConstraints gbc_enLocationLabel = new GridBagConstraints();
			gbc_enLocationLabel.insets = new Insets(0, 0, 5, 0);
			gbc_enLocationLabel.gridx = 0;
			gbc_enLocationLabel.gridy = 0;
			agencePanel.add(enLocationLabel, gbc_enLocationLabel);
		}

	}

}
