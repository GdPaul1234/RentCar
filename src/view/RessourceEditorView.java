package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;

import controller.AgenceDAO;
import controller.ClientDAO;
import controller.VehiculeDAO;
import model.Agence;
import model.Client;
import model.Vehicule;
import model.interfaces.TabularObjectBuilder;
import view.component.RessourceSelector;
import view.component.WaitingDialog;

public class RessourceEditorView extends JPanel implements ActionListener {
	private static final long serialVersionUID = 5943840322040278648L;

	private JButton actualiserButton = new JButton("🔃 Act.");
	private JButton searchQueryButton = new JButton("🔍 Rech.");

	private JButton addButton = new JButton("➕ Ajout.");
	private JButton editButton = new JButton("📝 Editer");
	private JButton delButton = new JButton("➖ Suppr.");
	private JTextField searchTextField;
	private RessourceSelector ressourceSelector;

	private String typeRessource;
	private JPanel frame = this;

	/**
	 * Create the panel.
	 */
	public RessourceEditorView(String typeRessource) {
		// set typeRessource
		this.typeRessource = typeRessource;

		setLayout(new BorderLayout(0, 0));
		createUI();

	}

	class RefreshTask extends SwingWorker<List<?>, Void> {

		@Override
		protected List<?> doInBackground() throws Exception {
			switch (typeRessource) {
			case "Client":
				ClientDAO clientDAO = new ClientDAO();
				List<Client> clients = clientDAO.getClientList();
				return clients;

			case "Vehicule":
				VehiculeDAO vehiculeDAO = new VehiculeDAO();
				List<Vehicule> vehicules = vehiculeDAO.getVehiculeList();
				return vehicules;

			case "Agence":
				AgenceDAO agenceDAO = new AgenceDAO();
				List<Agence> agences = agenceDAO.getAgenceList();
				return agences;

			default:
			}

			return null;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void done() {
			try {

				System.out.println("Finishing refresh table data");
				ressourceSelector.refreshTable((List<TabularObjectBuilder>) get());

				// setting and changing column widths
				switch (typeRessource) {
				case "Client":
					ressourceSelector.resizeColumns(Client.getColumnsWidth());
					break;

				case "Vehicule":
					ressourceSelector.resizeColumns(Vehicule.getColumnsWidth());
					break;

				case "Agence":
					ressourceSelector.resizeColumns(Agence.getColumnsWidth());
					break;
				default:
				}

				// hide column clientID
				if (typeRessource.equals("Client") || typeRessource.equals("Agence"))
					ressourceSelector.hideFirstColumn();

			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		Object ressourceID = ressourceSelector.getSelectedRessourceID();

		switch (action) {
		case "search":
			try {
				searchRessource();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;

		case "refresh":
			new RefreshTask().execute();
			searchTextField.setText("");
			break;

		case "add":
			addRessource();
			break;

		case "edit":
			editRessource(ressourceID);
			break;

		case "del":
			deleteRessource(ressourceID);
			break;
		default:
		}

	}

	/**
	 * Get header of ressource type
	 * 
	 * @return header of ressource type
	 */
	private String[] getRessourceSelectorHeader() {
		switch (typeRessource) {
		case "Client":
			return Client.getHeader();

		case "Vehicule":
			return Vehicule.getHeader();

		case "Agence":
			return Agence.getHeader();
		default:
		}

		return null;
	}

	/**
	 * Search Ressource
	 */
	private void searchRessource() throws SQLException {
		switch (typeRessource) {
		case "Client":
			List<Client> clientNameList = new ClientDAO().searchClientByName(searchTextField.getText());
			ressourceSelector.refreshTable(clientNameList);
			ressourceSelector.resizeColumns(Client.getColumnsWidth());
			ressourceSelector.hideFirstColumn();
			break;

		case "Vehicule":
			List<Vehicule> vehiculeMarqueList = new VehiculeDAO().searchVehiculeByMarque(searchTextField.getText());
			ressourceSelector.refreshTable(vehiculeMarqueList);
			ressourceSelector.resizeColumns(Vehicule.getColumnsWidth());
			break;
		default:
			break;
		}
	}

	/**
	 * Add ressource
	 */
	private void addRessource() {
		switch (typeRessource) {
		case "Client":
			new EditClientView().run(frame).thenRun(new RefreshTask());
			break;

		case "Vehicule":
			new EditVehicleView().run(frame).thenRun(new RefreshTask());
			break;

		default:
		}
	}

	/**
	 * Edit ressource
	 */
	private void editRessource(Object ressourceID) {
		if (ressourceID != null) {
			try {
				switch (typeRessource) {
				case "Client":
					new EditClientView((int) ressourceID).run(frame).thenRun(new RefreshTask());
					break;

				case "Vehicule":
					new EditVehicleView((String) ressourceID).run(frame).thenRun(new RefreshTask());
					break;

				default:
				}
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(frame, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Delete ressource
	 * 
	 * @param ressourceID ressource ID of ressource item to be deleted
	 */
	private void deleteRessource(Object ressourceID) {
		if (ressourceID != null) {
			int choix = JOptionPane.showConfirmDialog(frame, "Voulez-vous supprimer cet élément ?", "",
					JOptionPane.YES_NO_OPTION);

			if (choix == JOptionPane.YES_OPTION) {
				try {
					// Lancer l'animation d'attente
					WaitingDialog waiting = new WaitingDialog(frame);

					switch (typeRessource) {
					case "Client":
						new ClientDAO().removeClient((int) ressourceID);
						break;

					case "Vehicule":
						new VehiculeDAO().removeVehicule((String) ressourceID);
						break;

					default:
					}

					// refresh la table et arrêter l'animation
					new RefreshTask().execute();
					waiting.close();
				} catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
				}
			}

		}
	}

	/**
	 * Create the view.
	 */
	private void createUI() {

		{
			JToolBar searchToolBar = new JToolBar();
			searchToolBar.setFloatable(false);
			add(searchToolBar, BorderLayout.NORTH);
			{
				searchToolBar.addSeparator(new Dimension(25, 20));

				{
					searchTextField = new JTextField();
					searchTextField.setColumns(10);
					searchTextField.setActionCommand("search");
					searchTextField.addActionListener(this);
					searchToolBar.add(searchTextField);

					searchQueryButton.setActionCommand("search");
					searchQueryButton.addActionListener(this);
					searchToolBar.add(searchQueryButton);

				}

				searchToolBar.addSeparator();

				{
					JLabel lblNewLabel = new JLabel("Filt.");
					searchToolBar.add(lblNewLabel);

					JComboBox<?> comboBox = new JComboBox<Object>();
					searchToolBar.add(comboBox);
				}

				searchToolBar.addSeparator();

				{
					actualiserButton.setActionCommand("refresh");
					actualiserButton.addActionListener(this);
					searchToolBar.add(actualiserButton);
				}

				searchToolBar.addSeparator(new Dimension(25, 20));
			}

		}

		/* JTable affichant la liste des ressources disponibles */
		{

			ressourceSelector = new RessourceSelector(getRessourceSelectorHeader());
			new RefreshTask().execute();
			add(ressourceSelector);
		}

		/* Container pour les actions sur les ressources */
		if (!typeRessource.equals("Agence")) {
			JToolBar actionToolBar = new JToolBar();
			actionToolBar.setFloatable(false);

			Component glue_left = Box.createGlue();
			actionToolBar.add(glue_left);

			addButton.setActionCommand("add");
			addButton.addActionListener(this);
			actionToolBar.add(addButton);

			actionToolBar.addSeparator();

			editButton.setActionCommand("edit");
			editButton.addActionListener(this);
			actionToolBar.add(editButton);

			actionToolBar.addSeparator();

			delButton.setActionCommand("del");
			delButton.addActionListener(this);
			actionToolBar.add(delButton);

			add(actionToolBar, BorderLayout.SOUTH);
			Component glue_right = Box.createGlue();
			actionToolBar.add(glue_right);
		}
	}

}
