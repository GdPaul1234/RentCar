package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import model.Vehicule;
import view.component.viewer.VehicleViewer;

public class ManageVehicleView extends JDialog implements ActionListener {

	private static final long serialVersionUID = -4135815382938078333L;

	
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
				gestionVehiculePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
				gestionVehiculePanel.setBorder(BorderFactory.createTitledBorder("Gestion véhicule"));
				contentPanel.add(gestionVehiculePanel);
				{
					JButton clientVehiculeButton = new JButton("Clients ayant loué ce véhicule");
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

					JButton btnNewButton = new JButton("Déplacer ce véhicule");
					btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
					gestionVehiculePanel.add(btnNewButton);

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

		pack();
	}

	

	/**
	 * Respond to user interactions
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
