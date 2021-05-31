package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CompletableFuture;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class LoginView extends JDialog {

	private static final long serialVersionUID = -574084565115639795L;

	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LoginView dialog = new LoginView();
			dialog.setModal(true);
			dialog.setResizable(false);
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

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent we) {
				finishEditing.complete(null);
			}
		});
		return finishEditing;
	}

	/**
	 * Create the dialog.
	 */
	public LoginView() {
		setBounds(100, 100, 320, 240);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0 };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel connectionLabel = new JLabel("Connexion");
			connectionLabel.setFont(connectionLabel.getFont().deriveFont(connectionLabel.getFont().getSize() + 6f));
			GridBagConstraints gbc_connectionLabel = new GridBagConstraints();
			gbc_connectionLabel.gridwidth = 2;
			gbc_connectionLabel.insets = new Insets(0, 0, 5, 0);
			gbc_connectionLabel.gridx = 0;
			gbc_connectionLabel.gridy = 0;
			contentPanel.add(connectionLabel, gbc_connectionLabel);
		}
		{
			JLabel rentcarLabel = new JLabel("Rentcar");
			GridBagConstraints gbc_rentcarLabel = new GridBagConstraints();
			gbc_rentcarLabel.gridwidth = 2;
			gbc_rentcarLabel.insets = new Insets(0, 0, 5, 0);
			gbc_rentcarLabel.gridx = 0;
			gbc_rentcarLabel.gridy = 1;
			contentPanel.add(rentcarLabel, gbc_rentcarLabel);
		}
		{
			JLabel loginLabel = new JLabel("login");
			GridBagConstraints gbc_loginLabel = new GridBagConstraints();
			gbc_loginLabel.insets = new Insets(0, 0, 5, 5);
			gbc_loginLabel.anchor = GridBagConstraints.EAST;
			gbc_loginLabel.gridx = 0;
			gbc_loginLabel.gridy = 2;
			contentPanel.add(loginLabel, gbc_loginLabel);
		}
		{
			JTextField loginTextField = new JTextField();
			GridBagConstraints gbc_loginTextField = new GridBagConstraints();
			gbc_loginTextField.insets = new Insets(0, 0, 5, 0);
			gbc_loginTextField.fill = GridBagConstraints.HORIZONTAL;
			gbc_loginTextField.gridx = 1;
			gbc_loginTextField.gridy = 2;
			contentPanel.add(loginTextField, gbc_loginTextField);
		}
		{
			JLabel passwordLabel = new JLabel("password");
			GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
			gbc_passwordLabel.anchor = GridBagConstraints.EAST;
			gbc_passwordLabel.insets = new Insets(0, 0, 5, 5);
			gbc_passwordLabel.gridx = 0;
			gbc_passwordLabel.gridy = 3;
			contentPanel.add(passwordLabel, gbc_passwordLabel);
		}
		{
			passwordField = new JPasswordField();
			GridBagConstraints gbc_passwordField = new GridBagConstraints();
			gbc_passwordField.insets = new Insets(0, 0, 5, 0);
			gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
			gbc_passwordField.gridx = 1;
			gbc_passwordField.gridy = 3;
			contentPanel.add(passwordField, gbc_passwordField);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			Dimension buttonSize = new Dimension(80, 30);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.setPreferredSize(buttonSize);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.setPreferredSize(buttonSize);
				buttonPane.add(cancelButton);
			}
		}

	}

}
