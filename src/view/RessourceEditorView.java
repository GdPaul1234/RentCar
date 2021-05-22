package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JToolBar;

import model.interfaces.TabularObjectBuilder;
import view.component.RessourceSelector;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import java.awt.Component;
import javax.swing.Box;

public class RessourceEditorView<T extends TabularObjectBuilder> extends JPanel {
	private static final long serialVersionUID = 5943840322040278648L;

	private JButton addButton =  new JButton("➕ Ajout.");
	private JButton editButton = new JButton("📝 Editer");
	private JButton delButton =  new JButton("➖ Suppr.");
	private JTextField searchTextField;

	/**
	 * Create the panel.
	 */
	public RessourceEditorView(String[] header, List<T> data) {
		setLayout(new BorderLayout(0, 0));

		{
			JToolBar searchToolBar = new JToolBar();
			add(searchToolBar, BorderLayout.NORTH);
			{
				{
					searchTextField = new JTextField();
					searchToolBar.add(searchTextField);
					//searchTextField.setColumns(10);

					JButton searchQueryButton = new JButton("🔍 Rech.");
					searchToolBar.add(searchQueryButton);

				}

				{
					searchToolBar.addSeparator();
				}

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
			RessourceSelector<T> ressourceSelector = new RessourceSelector<T>(header, data);
			add(ressourceSelector);
		}

		/* Container pour les actions sur les ressources */
		{
			JToolBar actionToolBar = new JToolBar();
			
			Component glue_left = Box.createGlue();
			actionToolBar.add(glue_left);
			actionToolBar.add(addButton);
			actionToolBar.add(editButton);
			actionToolBar.add(delButton);
			add(actionToolBar, BorderLayout.SOUTH);
			Component glue_right = Box.createGlue();
			actionToolBar.add(glue_right);
		}

	}

}
