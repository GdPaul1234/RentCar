package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import controller.VehiculeDAO;
import model.Vehicule;
import model.enums.TypeBoite;
import model.enums.TypeCarburant;
import model.enums.TypeCategorie;
import view.component.EditRessourceUtils;
import view.component.WaitingDialog;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class EditVehicleView extends JDialog implements ActionListener {

	private static final long serialVersionUID = 8614996964673985215L;
	private final JPanel contentPanel = new JPanel();
	private static final Dimension dimensionSelect = new Dimension(120, 20);
	private static final Dimension dimensionMatricule = new Dimension(75, 20);

	private JFormattedTextField matriculeTextField = new JFormattedTextField(
			EditRessourceUtils.createFormatter("UU'-###'-UU"));
	private JTextField marqueTextField = new JTextField();
	private JTextField modeleTextField = new JTextField();

	private JComboBox<TypeBoite> boiteSelect = new JComboBox<>(TypeBoite.getValues());
	private JComboBox<TypeCarburant> carburantSelect = new JComboBox<>(TypeCarburant.getValues());
	private JComboBox<TypeCategorie> categorieSelect = new JComboBox<>(TypeCategorie.getValues());
	private JSpinner kilometrageSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 999999, 1));
	private JCheckBox climatisationCheckBox = new JCheckBox("climatisation");

	private JDialog frame = this;
	private JButton okButton;
	private JButton cancelButton;

	private Task task;
	private WaitingDialog waiting;
	private boolean createVehicule = true;

	class Task extends SwingWorker<Void, Void> {
		private boolean error = false;

		/**
		 * Vérifier si tous les champs ont été remplis
		 * 
		 * @return
		 */
		private boolean validate() {
			String[] textFieldValues = { matriculeTextField.getText(), marqueTextField.getText(),
					modeleTextField.getText() };

			for (String textFieldValue : textFieldValues) {
				if (textFieldValue.strip().equals("")) {
					return false;
				}
			}
			return true;
		}

		/*
		 * Main task. Executed in background thread.
		 * https://docs.oracle.com/javase/tutorial/uiswing/examples/components/
		 * ProgressBarDemo2Project/src/components/ProgressBarDemo2.java
		 */
		@Override
		protected Void doInBackground() {
			// Create vehicle
			if (validate()) {
				Vehicule newVehicule = new Vehicule(matriculeTextField.getText(), marqueTextField.getText(),
						modeleTextField.getText(), new BigDecimal(kilometrageSpinner.getValue().toString()),
						(TypeBoite) boiteSelect.getSelectedItem(), (TypeCarburant) carburantSelect.getSelectedItem(),
						climatisationCheckBox.isSelected(), (TypeCategorie) categorieSelect.getSelectedItem());
				VehiculeDAO vehiculeDAO = new VehiculeDAO();

				try {
					if (createVehicule) {
						vehiculeDAO.addVehicule(newVehicule);
					} else {
						vehiculeDAO.editVehicule(newVehicule);
					}

					error = false;
				} catch (SQLException e) {
					error = true;
					e.printStackTrace();
					JOptionPane.showMessageDialog(frame, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
				}

			} else {
				error = true;
				JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs du formulaire", "",
						JOptionPane.ERROR_MESSAGE);
			}

			return null;
		}

		/*
		 * Executed in event dispatch thread
		 */
		@Override
		protected void done() {
			okButton.setEnabled(true);
			cancelButton.setEnabled(true);
			waiting.close();

			if (!error)
				dispose();
		}
	}

	/**
	 * Launch the application.
	 */
	public CompletableFuture<Void> run(Component frame) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(frame);
		setModal(true);
		setVisible(true);

		CompletableFuture<Void> finishEditing = new CompletableFuture<>();

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent we) {
				System.out.println("Finish editing");
				finishEditing.complete(null);
			}
		});
		return finishEditing;
	}

	/**
	 * Default constructor<br>
	 * Create vehicule
	 */
	public EditVehicleView() {
		createUI();
	}

	/**
	 * Edit vehicule
	 * 
	 * @param matricule id of vehicule to edit
	 * @throws SQLException
	 */
	public EditVehicleView(String matricule) throws SQLException {
		VehiculeDAO vehiculeDB = new VehiculeDAO();
		Vehicule vehicule = vehiculeDB.getVehicule(matricule);

		// pré-remplir les champs
		if (vehicule != null) {
			createVehicule = false;

			matriculeTextField.setText(vehicule.getMatricule());
			marqueTextField.setText(vehicule.getMarque());
			modeleTextField.setText(vehicule.getModele());

			boiteSelect.setSelectedItem(vehicule.getTypeBoite());
			carburantSelect.setSelectedItem(vehicule.getTypeCarburant());
			categorieSelect.setSelectedItem(vehicule.getCategorie());
			kilometrageSpinner.setValue(vehicule.getKilometrage());
			climatisationCheckBox.setSelected(vehicule.isClimatisation());
		}

		// passer en mode edition

		createUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		switch (action) {
		/**
		 * Create or edit vehicle
		 */
		case "OK":
			// Launch wait UI
			waiting = new WaitingDialog(frame);

			okButton.setEnabled(false);
			cancelButton.setEnabled(false);
			// and create new user
			task = new Task();
			task.execute();
			break;

		/**
		 * Cancel edit
		 */
		case "Cancel":
			dispose();
			break;

		default:

		}

	}

	/**
	 * Create the dialog.
	 */
	private void createUI() {
		setTitle("Edition véhicule");
		setResizable(false);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		FlowLayout flowLayout = new FlowLayout(FlowLayout.TRAILING);
		FlowLayout flowLeftLayout = new FlowLayout(FlowLayout.LEADING);
		InputVerifier checkNotEmpty = EditRessourceUtils.getEmptyInputVerifier();
		getContentPane().setLayout(new BorderLayout(0, 0));

		getContentPane().add(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			Box indentifiantPanel = Box.createVerticalBox();
			indentifiantPanel.setBorder(BorderFactory.createTitledBorder("Véhicule"));
			contentPanel.add(indentifiantPanel, BorderLayout.WEST);
			{
				JPanel marquePanel = new JPanel(flowLayout);
				indentifiantPanel.add(marquePanel);
				{
					Component rigidArea = Box.createRigidArea(new Dimension(2, 15));
					marquePanel.add(rigidArea);
					JLabel marqueLabel = new JLabel("marque");
					marquePanel.add(marqueLabel);
					marqueTextField.setPreferredSize(dimensionSelect);
					marqueTextField.setInputVerifier(checkNotEmpty);
					marquePanel.add(marqueTextField);
				}

				JPanel modelePanel = new JPanel(flowLayout);
				indentifiantPanel.add(modelePanel);
				{
					Component rigidArea = Box.createRigidArea(new Dimension(2, 15));
					modelePanel.add(rigidArea);
					JLabel modeleLabel = new JLabel("modèle");
					modelePanel.add(modeleLabel);
					modeleTextField.setPreferredSize(dimensionSelect);
					modeleTextField.setInputVerifier(checkNotEmpty);
					modelePanel.add(modeleTextField);
				}

				JPanel matriculePanel = new JPanel(flowLeftLayout);
				indentifiantPanel.add(matriculePanel);
				{
					JLabel matriculeLabel = new JLabel("matricule");
					matriculePanel.add(matriculeLabel);
					matriculeTextField.setHorizontalAlignment(SwingConstants.CENTER);
					matriculeTextField.setPreferredSize(dimensionMatricule);
					matriculeTextField.setInputVerifier(checkNotEmpty);
					matriculePanel.add(matriculeTextField);
				}

				JPanel kilometragePanel = new JPanel(flowLeftLayout);
				indentifiantPanel.add(kilometragePanel);
				{
					Component rigidArea = Box.createRigidArea(new Dimension(30, 15));
					kilometragePanel.add(rigidArea);
					JLabel kilometrageLabel = new JLabel("km");
					kilometragePanel.add(kilometrageLabel);
					kilometrageSpinner.setPreferredSize(dimensionMatricule);
					kilometragePanel.add(kilometrageSpinner);
				}

			}

			Box caracteristiquePanel = Box.createVerticalBox();
			caracteristiquePanel.setBorder(BorderFactory.createTitledBorder("Caractéristique"));
			contentPanel.add(caracteristiquePanel, BorderLayout.EAST);
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

				JPanel climatisationPanel = new JPanel(flowLeftLayout);
				caracteristiquePanel.add(climatisationPanel);

				Component rigidArea = Box.createRigidArea(new Dimension(30, 15));
				climatisationPanel.add(rigidArea);
				{
					climatisationPanel.add(climatisationCheckBox);
				}
			}
		}

		{
			JPanel buttonPane = new JPanel(flowLayout);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}

		pack();
	}
}
