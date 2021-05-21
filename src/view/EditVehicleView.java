package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextField;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.enums.TypeBoite;
import model.enums.TypeCarburant;
import model.enums.TypeCategorie;
import view.component.EditRessourceUtils;
import javax.swing.SwingConstants;

public class EditVehicleView extends JDialog {
	
	private static final long serialVersionUID = 8614996964673985215L;
	private final JPanel contentPanel = new JPanel();
	private static Dimension dimensionSelect = new Dimension(120, 20);
	private static int textFieldWidth = 18;

	private JTextField matriculeTextField = new JTextField();
	private JTextField marqueTextField = new JTextField();
	private JTextField modeleTextField = new JTextField();

	private JComboBox<TypeBoite> boiteSelect = new JComboBox(TypeBoite.getValues());
	private JComboBox<TypeCarburant> carburantSelect = new JComboBox(TypeCarburant.getValues());
	private JComboBox<TypeCategorie> categorieSelect = new JComboBox(TypeCategorie.getValues());
	private JTextField kilometrageTextField = new JTextField();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EditVehicleView dialog = new EditVehicleView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EditVehicleView() {
		setTitle("Edition véhicule");
		setResizable(false);
		// setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		FlowLayout flowLayout = new FlowLayout(FlowLayout.TRAILING);
		FlowLayout flowLeftLayout = new FlowLayout(FlowLayout.LEADING);
		
		
		InputVerifier checkNotEmpty = EditRessourceUtils.getEmptyInputVerifier();

		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
		{
			Box indentifiantPanel = Box.createVerticalBox();
			indentifiantPanel.setBorder(BorderFactory.createTitledBorder("Identification"));
			contentPanel.add(indentifiantPanel);
			{
				JPanel nomPanel = new JPanel(flowLayout);

				indentifiantPanel.add(nomPanel);
				{
					JLabel matriculeLabel = new JLabel("matricule");
					nomPanel.add(matriculeLabel);
					matriculeTextField.setColumns(textFieldWidth);
					matriculeTextField.setInputVerifier(checkNotEmpty);
					nomPanel.add(matriculeTextField);
				}

				JPanel marquePanel = new JPanel(flowLayout);
				indentifiantPanel.add(marquePanel);
				{
					JLabel marqueLabel = new JLabel("marque");
					marquePanel.add(marqueLabel);
					marqueTextField.setColumns(textFieldWidth);
					marqueTextField.setInputVerifier(checkNotEmpty);
					marquePanel.add(marqueTextField);
				}

				JPanel modelePanel = new JPanel(flowLayout);
				indentifiantPanel.add(modelePanel);
				{
					JLabel modeleLabel = new JLabel("modèle");
					modelePanel.add(modeleLabel);
					modeleTextField.setColumns(textFieldWidth);
					modeleTextField.setInputVerifier(checkNotEmpty);
					modelePanel.add(modeleTextField);
				}
			}

			Box caracteristiquePanel = Box.createVerticalBox();
			caracteristiquePanel.setBorder(BorderFactory.createTitledBorder("Caractéristique"));
			contentPanel.add(caracteristiquePanel);
			{
				JPanel boitePanel = new JPanel(flowLeftLayout);
				caracteristiquePanel.add(boitePanel);
				{
					Component rigidArea = Box.createRigidArea(new Dimension(19, 15));
					boitePanel.add(rigidArea);
					JLabel boiteLabel = new JLabel("boîte");
					boitePanel.add(boiteLabel);
					boiteSelect.setPreferredSize(dimensionSelect);
					boitePanel.add(boiteSelect);
				}

				JPanel carburantPanel = new JPanel(flowLeftLayout);
				caracteristiquePanel.add(carburantPanel);
				{
					JLabel carburantLabel = new JLabel("carburant");
					carburantPanel.add(carburantLabel);
					carburantSelect.setPreferredSize(dimensionSelect);
					carburantPanel.add(carburantSelect);
				}
				
				JPanel categoriePanel = new JPanel(flowLeftLayout);
				caracteristiquePanel.add(categoriePanel);
				{
					JLabel categorieLabel = new JLabel("catégorie");
					categoriePanel.add(categorieLabel);
					categorieSelect.setPreferredSize(dimensionSelect);
					categoriePanel.add(categorieSelect);
				}
				
				JPanel kilometragePanel = new JPanel(flowLeftLayout);
				caracteristiquePanel.add(kilometragePanel);
				{
					Component rigidArea = Box.createRigidArea(new Dimension(30, 15));
					kilometragePanel.add(rigidArea);
					JLabel kilometrageLabel = new JLabel("km");
					kilometragePanel.add(kilometrageLabel);
					kilometrageTextField.setColumns(12);
					kilometrageTextField.setInputVerifier(checkNotEmpty);
					kilometragePanel.add(kilometrageTextField);
				}
				
				JPanel climatisationPanel = new JPanel(flowLeftLayout);
				caracteristiquePanel.add(climatisationPanel);
				
				Component rigidArea = Box.createRigidArea(new Dimension(30, 15));
				climatisationPanel.add(rigidArea);
				{
					JCheckBox climatisationLabel = new JCheckBox("climatisation");
					climatisationPanel.add(climatisationLabel);
				}
			}
		}

		{
			JPanel buttonPane = new JPanel(flowLayout);
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
