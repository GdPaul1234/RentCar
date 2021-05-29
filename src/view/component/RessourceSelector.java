package view.component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import model.interfaces.TabularObjectBuilder;

public class RessourceSelector extends JPanel {
	private static final long serialVersionUID = -8933064533708360284L;

	private JLabel updateLabel = new JLabel("Mis à jour le :");

	private JTable table;
	private String[] header;

	/**
	 * Create the panel.
	 */
	public RessourceSelector(String[] header) {
		this.header = header;

		setLayout(new BorderLayout(0, 0));

		// JTable qui affiche la liste des ressources disponibles
		{
			table = new JTable();
			table.setAutoCreateRowSorter(true);
			table.setFillsViewportHeight(true);
			table.getTableHeader().setReorderingAllowed(false);
			table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			JScrollPane scrolltable = new JScrollPane(table);
			add(scrolltable, BorderLayout.CENTER);
		}

		JPanel updatePane = new JPanel();
		FlowLayout flowLayout = (FlowLayout) updatePane.getLayout();
		flowLayout.setVgap(2);
		add(updatePane, BorderLayout.SOUTH);
		{
			updateLabel.setFont(UIManager.getFont("Viewport.font"));
			updatePane.add(updateLabel);
		}

	}

	public void hideFirstColumn() {
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
	}

	public Object getSelectedRessourceID() {
		System.out.println("Selected row: " + table.getSelectedRow());
		if (table.getSelectedRow() != -1)
			return table.getValueAt(table.getSelectedRow(), 0);
		return null;
	}

	public <T extends TabularObjectBuilder> void refreshTable(List<T> data) {
		// map List<T> to T[][]
		Object[][] dataTable = data.stream().map(v -> v.toArray()).toArray(Object[][]::new);

		table.setModel(new DefaultTableModel(dataTable, header) {
			private static final long serialVersionUID = 3022428812614654606L;
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return getValueAt(0, columnIndex).getClass();
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		updateLabel.setText(
				"Mis à jour le : " + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime()));
	}

	public void resizeColumns(List<Integer> columnsWidth) {
		int nbColumns = table.getColumnModel().getColumnCount();
		if (columnsWidth != null && nbColumns == columnsWidth.size()) {
			TableColumn column = null;
			for (int i = 0; i < nbColumns; i++) {
				column = table.getColumnModel().getColumn(i);
				int columnSize = columnsWidth.get(i);

				if (columnSize != -1) {
					column.setPreferredWidth(columnSize);
				}
			}
		}

	}

}
