package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import controller.DataAccess;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class MainView extends JFrame {
	private static final long serialVersionUID = -7153177848312153616L;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					DataAccess dataAccess = DataAccess.getInstance();

					MainView frame = new MainView();

					// Ask if user wants to quit our application
					frame.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent we) {
							int choice = JOptionPane.showConfirmDialog(frame, "Fermer cette session ?",
									"Fermeture de Rentcar", JOptionPane.YES_NO_OPTION);

							if (choice == JOptionPane.YES_OPTION) {
								try {
									// close DB connection
									dataAccess.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
								frame.dispose();
							}
						}
					});
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
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		setTitle("RentCar");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		/* Barre d'onglets pointant vers les principales vues */
		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		RessourceEditorView clientsView = new RessourceEditorView("Client");
		RessourceEditorView vehiculesView = new RessourceEditorView("Vehicule");
		RessourceEditorView agenceView = new RessourceEditorView("Agence");

		tabbedPane.addTab("Clients", clientsView);
		tabbedPane.addTab("Véhicules", vehiculesView);
		tabbedPane.addTab("Agences", agenceView);
	}

}
