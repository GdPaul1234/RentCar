package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;

public class EditClientView extends JDialog implements EditView {
	private static final long serialVersionUID = -7859216121456496255L;
	private final JPanel contentPanel = new JPanel();
	private static int textFieldWidth = 18;

	private JTextField nomTextField = new JTextField();
	private JTextField prenomTextField = new JTextField();
	private JTextField emailTextField = new JTextField();
	private JFormattedTextField telTextField = new JFormattedTextField(
			EditView.createFormatter("+33 (#) # ## ## ## ##"));
	private JTextField rueTextField = new JTextField();
	private JFormattedTextField cpTextField = new JFormattedTextField(EditView.createFormatter("#####"));
	private JTextField villeTextField = new JTextField();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EditClientView dialog = new EditClientView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EditClientView() {
		setTitle("Edition client");
		setResizable(false);
		// setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		FlowLayout flowLayout = new FlowLayout(FlowLayout.TRAILING);

		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
		{
			Box infoPersoPanel = new Box(BoxLayout.PAGE_AXIS);
			infoPersoPanel.setBorder(BorderFactory.createTitledBorder("Identification"));
			contentPanel.add(infoPersoPanel);
			{
				JPanel nomPanel = new JPanel(flowLayout);
				infoPersoPanel.add(nomPanel);
				{
					JLabel nomLabel = new JLabel("nom");
					nomPanel.add(nomLabel);
					nomTextField.setColumns(textFieldWidth);
					nomPanel.add(nomTextField);
				}

				JPanel prenomPanel = new JPanel(flowLayout);
				infoPersoPanel.add(prenomPanel);
				{
					JLabel prenomLabel = new JLabel("prenom");
					prenomPanel.add(prenomLabel);
					prenomTextField.setColumns(textFieldWidth);
					prenomPanel.add(prenomTextField);
				}
			}

			Box contactPanel = new Box(BoxLayout.PAGE_AXIS);
			contactPanel.setBorder(BorderFactory.createTitledBorder("Contact"));
			contentPanel.add(contactPanel);
			{
				JPanel emailPanel = new JPanel(flowLayout);
				contactPanel.add(emailPanel);
				{
					JLabel emailLabel = new JLabel("email");
					emailPanel.add(emailLabel);
					emailTextField.setColumns(textFieldWidth);
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
					telPanel.add(telTextField);
				}
			}

			Box adressePanel = new Box(BoxLayout.PAGE_AXIS);
			adressePanel.setBorder(BorderFactory.createTitledBorder("Adresse"));
			contentPanel.add(adressePanel);
			{
				JPanel ruePanel = new JPanel();
				adressePanel.add(ruePanel);
				ruePanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
				{
					JLabel rueLabel = new JLabel("rue");
					ruePanel.add(rueLabel);
					rueTextField = new JTextField(textFieldWidth);
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
					villePanel.add(villeTextField);
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
