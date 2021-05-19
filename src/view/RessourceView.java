package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JTable;
import javax.swing.JButton;

public class RessourceView<T> extends JPanel {
	private JTable table = new JTable();
	private JButton addButton  = new JButton("Ajouter");
	private JButton editButton = new JButton("Editer");
	private JButton delButton  = new JButton("Suppr.");

	/**
	 * Create the panel.
	 */
	public RessourceView() {
		setLayout(new BorderLayout(0, 0));
		
		FlowLayout searchPanel = new FlowLayout();
		
		/* JTable qui affiche la liste des ressources disponibles */
		table.setAutoCreateRowSorter(true);
		table.setFillsViewportHeight(true);
		
		JScrollPane scrolltable = new JScrollPane(table);
		add(scrolltable, BorderLayout.CENTER);
		
		/* Container pour les actions sur les ressources */
		JPanel actionPannel = new JPanel();
		actionPannel.setLayout(new FlowLayout());
		
		actionPannel.add(addButton);
		actionPannel.add(editButton);
		actionPannel.add(delButton);
		
		add(actionPannel, BorderLayout.SOUTH);
		
		

	}

}
