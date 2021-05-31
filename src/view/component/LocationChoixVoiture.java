package view.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import model.Client;
import model.Vehicule;
import view.component.viewer.ClientViewer;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class LocationChoixVoiture extends JDialog {

	private static final long serialVersionUID = 8404941819774905251L;

	private final JPanel contentPanel = new JPanel();
	private TitledBorder titledBorder = BorderFactory.createTitledBorder("Choisir voiture <Catégorie>");
	private RessourceSelector ressourceSelector = new RessourceSelector(Vehicule.getHeader());
	private JLabel dureeLabel = new JLabel("-- jours");

	public void run(Component frame) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(frame);
		setResizable(false);
		setModal(true);
		setVisible(true);
	}

	/**
	 * Create the dialog.
	 */
	public LocationChoixVoiture(Client client) {
		setTitle("Nouvelle location");
		
		setBounds(100, 100, 800, 480);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			ClientViewer clientViewer = new ClientViewer((Client) client);
			contentPanel.add(clientViewer, BorderLayout.EAST);
		}
		{
			ressourceSelector = new RessourceSelector((String[]) null);
			ressourceSelector.setBorder(titledBorder);
			contentPanel.add(ressourceSelector, BorderLayout.CENTER);
			{
				JPanel panel = new JPanel();
				ressourceSelector.add(panel, BorderLayout.NORTH);
				{
					JLabel dateStartLabel = new JLabel("Début location");
					panel.add(dateStartLabel);
				}
				{
					JSpinner dateStartSpinner = new JSpinner();
					dateStartSpinner.setModel(new SpinnerDateModel());
					panel.add(dateStartSpinner);
				}
				{
					panel.add(dureeLabel);
				}
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
