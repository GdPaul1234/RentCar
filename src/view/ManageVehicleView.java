package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import controller.LocationDAO;
import controller.VehiculeDAO;
import model.Client;
import model.Vehicule;
import view.component.RessourceSelector;
import view.component.viewer.VehicleViewer;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class ManageVehicleView extends JDialog implements ActionListener {

	private static final long serialVersionUID = -4135815382938078333L;
	private JDialog frame = this;

	private Vehicule vehicule;

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
	public ManageVehicleView(Vehicule vehicule) {
		this.vehicule = vehicule;

		setTitle("Gestion véhicule");
		getContentPane().setLayout(new BorderLayout(0, 0));

		Box contentPanel = Box.createVerticalBox();
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			{
				VehicleViewer vehicleViewer = new VehicleViewer(vehicule);
				vehicleViewer.setAlignmentX(Component.CENTER_ALIGNMENT);
				contentPanel.add(vehicleViewer);
			}
			{
				Box gestionVehiculePanel = Box.createVerticalBox();
				gestionVehiculePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
				gestionVehiculePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
				contentPanel.add(gestionVehiculePanel);
				{
					JButton clientVehiculeButton = new JButton("Clients ayant loué ce véhicule");
					clientVehiculeButton.setActionCommand("get clients");
					clientVehiculeButton.addActionListener(this);
					clientVehiculeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
					clientVehiculeButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
						}
					});
					gestionVehiculePanel.add(clientVehiculeButton);
					{
						Component verticalStrut = Box.createVerticalStrut(5);
						gestionVehiculePanel.add(verticalStrut);
					}

					JButton moveVehicleButton = new JButton("Déplacer ce véhicule");
					moveVehicleButton.setActionCommand("deplacer");
					moveVehicleButton.addActionListener(this);
					moveVehicleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
					gestionVehiculePanel.add(moveVehicleButton);

				}

			}
		}

		pack();
	}

	/**
	 * Respond to user interactions
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
		case "get clients":
			try {
				List<Client> clientLocation = new VehiculeDAO().getClientByVehiculeLoue(vehicule.getMatricule());
				RessourceSelector listclientLocationView = new RessourceSelector(Client.getHeader());
				listclientLocationView.refreshTable((List<Client>) clientLocation);
				listclientLocationView.resizeColumns(Client.getColumnsWidth());
				listclientLocationView.setPreferredSize(new Dimension(500, 240));
				JOptionPane.showMessageDialog(frame, listclientLocationView, "Clients ayant loué ce véhicule",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			break;

		case "deplacer":

			break;

		default:
			break;
		}

	}

}
