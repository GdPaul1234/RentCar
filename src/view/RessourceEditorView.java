package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JToolBar;

import controller.ClientDAO;
import controller.VehiculeDAO;
import model.interfaces.TabularObjectBuilder;
import view.component.RessourceSelector;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import java.awt.Component;
import javax.swing.Box;

public class RessourceEditorView<T extends TabularObjectBuilder> extends JPanel implements ActionListener {
	private static final long serialVersionUID = 5943840322040278648L;

	private JButton addButton = new JButton("➕ Ajout.");
	private JButton editButton = new JButton("📝 Editer");
	private JButton delButton = new JButton("➖ Suppr.");

	private RessourceSelector<T> ressourceSelector;
	private JTextField searchTextField;
	private String type = "undefined";
	
	// TODO Refresh table model after database modification

	/**
	 * Create the panel.
	 */
	public RessourceEditorView(String[] header, List<T> data) {
		setLayout(new BorderLayout(0, 0));

		// infer type of data
		if (data.size() > 0) {
			type = data.get(0).getClass().toString();
			System.out.println(type);
		}

		{
			JToolBar searchToolBar = new JToolBar();
			add(searchToolBar, BorderLayout.NORTH);
			{
				{
					searchTextField = new JTextField();
					searchToolBar.add(searchTextField);
					searchTextField.setColumns(10);

					JButton searchQueryButton = new JButton("🔍 Rech.");
					searchToolBar.add(searchQueryButton);

				}

				searchToolBar.addSeparator();

				{
					JLabel lblNewLabel = new JLabel("Filt.");
					searchToolBar.add(lblNewLabel);

					JComboBox comboBox = new JComboBox();
					searchToolBar.add(comboBox);
				}
			}

		}

		/* JTable affichant la liste des ressources disponibles */
		{
			ressourceSelector = new RessourceSelector<T>(header, data);
			if (type.equals("class model.Client")) {
				// hide column clientID
				ressourceSelector.hideFirstColumn();
			}
			add(ressourceSelector);
		}

		/* Container pour les actions sur les ressources */
		{
			JToolBar actionToolBar = new JToolBar();

			Component glue_left = Box.createGlue();
			actionToolBar.add(glue_left);

			addButton.setActionCommand("add");
			addButton.addActionListener(this);
			actionToolBar.add(addButton);

			editButton.setActionCommand("edit");
			editButton.addActionListener(this);
			actionToolBar.add(editButton);

			delButton.setActionCommand("del");
			delButton.addActionListener(this);
			actionToolBar.add(delButton);

			add(actionToolBar, BorderLayout.SOUTH);
			Component glue_right = Box.createGlue();
			actionToolBar.add(glue_right);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		Object ressourceID = ressourceSelector.getSelectedRessourceID();
		System.out.println(ressourceID);

		switch (action) {
		case "add":
			switch (type) {
			case "class model.Client":
				new EditClientView().run(this);
				break;

			case "class model.Vehicule":
				new EditVehicleView().run(this);
				break;

			default:
			}
			break;

		case "edit":
			if (ressourceID != null) {
				try {
					switch (type) {
					case "class model.Client":
						new EditClientView((int) ressourceID).run(this);
						break;

					case "class model.Vehicule":
						new EditVehicleView((String) ressourceID).run(this);
						break;

					default:
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, e1.getMessage(), "", JOptionPane.ERROR_MESSAGE);
				}
				break;
			}
			break;

		case "del":
			if (ressourceID != null) {
				int choix = JOptionPane.showConfirmDialog(this, "Voulez-vous supprimer cet élément ?", "",
						JOptionPane.YES_NO_OPTION);

				if (choix == JOptionPane.YES_OPTION) {
					try {
						switch (type) {
						case "class model.Client":
								new ClientDAO().removeClient((int) ressourceID);
							break;

						case "class model.Vehicule":
								new VehiculeDAO().removeVehicule((String) ressourceID);
							break;

						default:
						}
					} catch (SQLException e2) {
						e2.printStackTrace();
						JOptionPane.showMessageDialog(this, e2.getMessage(), "", JOptionPane.ERROR_MESSAGE);
					}
				}

				break;
			}
			break;

		default:
		}
	}

}
