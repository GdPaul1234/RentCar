package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import controller.ClientDAO;
import model.Adresse;
import model.Client;
import view.component.WaitingDialog;

public class EditClientView extends JDialog implements EditView, ActionListener {
	private static final long serialVersionUID = -7859216121456496255L;
	private final JPanel contentPane = new JPanel();
	private static int textFieldWidth = 18;

	private JTextField nomTextField = new JTextField();
	private JTextField prenomTextField = new JTextField();
	private JTextField emailTextField = new JTextField();
	private JFormattedTextField telTextField = new JFormattedTextField(
			EditView.createFormatter("+33 (#) # ## ## ## ##"));
	private JTextField rueTextField = new JTextField();
	private JFormattedTextField cpTextField = new JFormattedTextField(EditView.createFormatter("#####"));
	private JTextField villeTextField = new JTextField();

	private JDialog frame = this;
	private JButton okButton;
	private JButton cancelButton;

	private Task task;
	private WaitingDialog waiting;
	private boolean createClient = true;
	private boolean error = false;

	class Task extends SwingWorker<Void, Void> {
		private boolean validate() {
			String[] textFieldValues = { nomTextField.getText(), prenomTextField.getText(), emailTextField.getText(),
					telTextField.getText(), rueTextField.getText(), villeTextField.getText(), cpTextField.getText() };

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
		public Void doInBackground() {
			// Create client
			if (validate()) {
				Client newClient = new Client(nomTextField.getText(), prenomTextField.getText(),
						emailTextField.getText(), telTextField.getText(),
						new Adresse(rueTextField.getText(), villeTextField.getText(), cpTextField.getText()));

				ClientDAO clientDB = new ClientDAO();
				try {
					// TODO ajouter check dans database si doublon
					// TODO ajouter édition client
					clientDB.addClient(newClient);
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
		public void done() {
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
	public static void main(String[] args) {
		try {
			// EditClientView dialog = new EditClientView();
			EditClientView dialog = new EditClientView(1);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EditClientView() {
		createUI();
	}

	public EditClientView(int clientID) throws SQLException {
		ClientDAO clientDB = new ClientDAO();
		Client client = clientDB.getClient(clientID);

		// pré-remplir les champs
		if (client != null) {
			createClient = false;

			nomTextField.setText(client.getNom());
			prenomTextField.setText(client.getPrenom());
			emailTextField.setText(client.getEmail());
			telTextField.setText(client.getTelephone());
			rueTextField.setText(client.getAdresse().getRue());
			cpTextField.setText(client.getAdresse().getCodePostal());
			villeTextField.setText(client.getAdresse().getVille());
		}

		// passer en mode edition

		createUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		switch (action) {
		case "OK":
			// Launch wait UI
			waiting = new WaitingDialog(frame);

			okButton.setEnabled(false);
			cancelButton.setEnabled(false);
			// and create new user
			task = new Task();
			task.execute();
			break;

		case "Cancel":
			dispose();
			break;

		default:

		}

	}

	/**
	 * Create the dialog.
	 */
	public void createUI() {
		setTitle("Edition client");
		setResizable(false);
		// setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		FlowLayout flowLayout = new FlowLayout(FlowLayout.TRAILING);

		InputVerifier checkNotEmpty = new InputVerifier() {
			@Override
			public boolean verify(JComponent input) {
				return !((JTextField) input).getText().strip().equals("");
			}
		};

		getContentPane().add(contentPane, BorderLayout.CENTER);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		{
			Box infoPersoPanel = Box.createVerticalBox();
			infoPersoPanel.setBorder(BorderFactory.createTitledBorder("Identification"));
			contentPane.add(infoPersoPanel);
			{
				JPanel nomPanel = new JPanel(flowLayout);
				infoPersoPanel.add(nomPanel);
				{
					JLabel nomLabel = new JLabel("nom");
					nomPanel.add(nomLabel);
					nomTextField.setColumns(textFieldWidth);
					nomTextField.setInputVerifier(checkNotEmpty);
					nomPanel.add(nomTextField);
				}

				JPanel prenomPanel = new JPanel(flowLayout);
				infoPersoPanel.add(prenomPanel);
				{
					JLabel prenomLabel = new JLabel("prenom");
					prenomPanel.add(prenomLabel);
					prenomTextField.setColumns(textFieldWidth);
					prenomTextField.setInputVerifier(checkNotEmpty);
					prenomPanel.add(prenomTextField);
				}
			}

			Box contactPanel = Box.createVerticalBox();
			contactPanel.setBorder(BorderFactory.createTitledBorder("Contact"));
			contentPane.add(contactPanel);
			{
				JPanel emailPanel = new JPanel(flowLayout);
				contactPanel.add(emailPanel);
				{
					JLabel emailLabel = new JLabel("email");
					emailPanel.add(emailLabel);
					emailTextField.setColumns(textFieldWidth);
					emailTextField.setInputVerifier(checkNotEmpty);
					emailPanel.add(emailTextField);
				}

				JPanel telPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
				contactPanel.add(telPanel);
				{
					Component rigidArea = Box.createRigidArea(new Dimension(21, 15));
					telPanel.add(rigidArea);
					JLabel telLabel = new JLabel("tel.");
					telLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					telPanel.add(telLabel);
					telTextField.setColumns(12);
					telTextField.setInputVerifier(checkNotEmpty);
					telPanel.add(telTextField);
				}
			}

			Box adressePanel = Box.createVerticalBox();
			adressePanel.setBorder(BorderFactory.createTitledBorder("Adresse"));
			contentPane.add(adressePanel);
			{
				JPanel ruePanel = new JPanel();
				adressePanel.add(ruePanel);
				ruePanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
				{
					JLabel rueLabel = new JLabel("rue");
					ruePanel.add(rueLabel);
					rueTextField.setColumns(textFieldWidth);
					rueTextField.setInputVerifier(checkNotEmpty);
					ruePanel.add(rueTextField);
				}

				JPanel villePanel = new JPanel(flowLayout);
				adressePanel.add(villePanel);
				{
					JLabel cpLabel = new JLabel("CP");
					villePanel.add(cpLabel);
					cpTextField.setColumns(4);
					villePanel.add(cpTextField);

					JLabel villeLabel = new JLabel("Ville");
					villePanel.add(villeLabel);
					villeTextField.setColumns(10);
					villeTextField.setInputVerifier(checkNotEmpty);
					villePanel.add(villeTextField);
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
