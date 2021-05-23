package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputVerifier;
import javax.swing.JButton;
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
import view.component.EditRessourceUtils;
import view.component.WaitingDialog;

/**
 * Dialogue édition client
 * 
 * @author Paul
 *
 */
public class EditClientView extends JDialog implements ActionListener {
	private static final long serialVersionUID = -7859216121456496255L;
	private final JPanel contentPane = new JPanel();
	private static int textFieldWidth = 18;
	private int clientID = -1;

	private JTextField nomTextField = new JTextField();
	private JTextField prenomTextField = new JTextField();
	private JTextField emailTextField = new JTextField();
	private JFormattedTextField telTextField = new JFormattedTextField(
			EditRessourceUtils.createFormatter("+33 (#) # ## ## ## ##"));
	private JTextField rueTextField = new JTextField();
	private JFormattedTextField cpTextField = new JFormattedTextField(EditRessourceUtils.createFormatter("#####"));
	private JTextField villeTextField = new JTextField();

	private JDialog frame = this;
	private JButton okButton;
	private JButton cancelButton;

	private Task task;
	private WaitingDialog waiting;
	private boolean createClient = true;

	class Task extends SwingWorker<Void, Void> {
		private boolean error = false;
		
		/**
		 * Vérifier si tous les champs ont été remplis
		 * 
		 * @return
		 */
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
		protected Void doInBackground() {
			// Create client
			if (validate()) {
				Client newClient = new Client(nomTextField.getText(), prenomTextField.getText(),
						emailTextField.getText(), telTextField.getText(),
						new Adresse(rueTextField.getText(), villeTextField.getText(), cpTextField.getText()));

				ClientDAO clientDB = new ClientDAO();
				try {
					if (createClient)
						clientDB.addClient(newClient);
					else
						clientDB.editClient(clientID, newClient);

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
	public static void main(String[] args) {
		try {
			EditClientView dialog = new EditClientView();
			//EditClientView dialog = new EditClientView(1);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public CompletableFuture<Void> run(Component frame) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(frame);
		setModal(true);
		setVisible(true);
		
		CompletableFuture<Void> finishEditing = new CompletableFuture<>();
		
		this.addWindowListener(
		new WindowAdapter() {
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
	 * Create client
	 */
	public EditClientView() {
		createUI();
	}

	/**
	 * Edit client
	 * 
	 * @param clientID clientID to edit
	 * @throws SQLException
	 */
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
		this.clientID = clientID;

		createUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		switch (action) {
		/**
		 * Create or edit client
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
		setTitle("Edition client");
		setResizable(false);
		// setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		FlowLayout flowLayout = new FlowLayout(FlowLayout.TRAILING);
		InputVerifier checkNotEmpty = EditRessourceUtils.getEmptyInputVerifier();
		InputVerifier getUpperEmptyInputVerifier = EditRessourceUtils.getUpperEmptyInputVerifier();

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
					nomTextField.setInputVerifier(getUpperEmptyInputVerifier);
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
					villeTextField.setInputVerifier(getUpperEmptyInputVerifier);
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
