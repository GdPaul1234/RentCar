package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.ClientDAO;
import controller.VehiculeDAO;
import model.Client;
import model.Vehicule;
import model.interfaces.TabularObjectBuilder;

import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class MainView extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("RentCar");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		/* Barre d'onglets pointant vers les principales vues */
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		// Dummy component
		ClientDAO clientDAO = new ClientDAO();
		List<Client> clients = null;
		try {
			clients = clientDAO.getClientList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] cHeader = Client.getHeader();

		try {
			RessourceEditorView<Client> clientsView = createRessourceView("Client");
			RessourceEditorView<Vehicule> vehiculesView = createRessourceView("Vehicule");
			JLabel text3 = new JLabel("Texte 3");

			tabbedPane.addTab("Clients", clientsView);
			tabbedPane.addTab("Véhicules", vehiculesView);
			tabbedPane.addTab("Performance", text3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public <T extends TabularObjectBuilder> RessourceEditorView<T> createRessourceView(String typeRessource)
			throws SQLException {
		switch (typeRessource) {
		case "Client":
			ClientDAO clientDAO = new ClientDAO();
			List<Client> clients = clientDAO.getClientList();
			String[] cHeader = Client.getHeader();
			return (RessourceEditorView<T>) new RessourceEditorView<Client>(cHeader, clients);

		case "Vehicule":
			VehiculeDAO vehiculeDAO = new VehiculeDAO();
			List<Vehicule> vehicules = vehiculeDAO.getVehiculeList();
			String[] vHeader = Vehicule.getHeader();
			return (RessourceEditorView<T>) new RessourceEditorView<Vehicule>(vHeader, vehicules);

		default:
		}

		return null;
	}

}
