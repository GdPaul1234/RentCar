package view.component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controller.ClientDAO;
import controller.VehiculeDAO;
import model.Client;
import model.Vehicule;
import model.interfaces.TabularObjectBuilder;
import view.ManageClientView;
import view.ManageVehicleView;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class RessourceSelector extends JPanel {
	private static final long serialVersionUID = -8933064533708360284L;
	private JPanel frame = this;

	private JLabel updateLabel = new JLabel("Mis à jour le :");

	private JTable table;
	private String[] header;
	private String ressourceType;

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

			// add double click lister https://stackoverflow.com/a/19586049
			table.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent mouseEvent) {
					JTable table = (JTable) mouseEvent.getSource();
					Point point = mouseEvent.getPoint();
					int row = table.rowAtPoint(point);
					if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
						new ManageTask(row).execute();
					}
				}
			});

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
		if (table.getSelectedRow() != -1)
			return table.getValueAt(table.getSelectedRow(), 0);
		return null;
	}

	public Object getRowRessourceID(int row) {
		if (row != -1)
			return table.getModel().getValueAt(row, 0);
		return null;
	}

	public <T extends TabularObjectBuilder> void refreshTable(List<T> data) {
		if (data != null && data.size() > 0) {
			// map List<T> to T[][]
			Object[][] dataTable = data.stream().map(v -> v.toArray()).toArray(Object[][]::new);
			ressourceType = data.size() > 0 ? data.get(0).getClass().getSimpleName() : null;
			System.out.println(ressourceType);

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
		} else {
			table.setModel(new DefaultTableModel(null, header));
		}

		updateLabel.setText(
				"Mis à jour le : " + new SimpleDateFormat("dd-MM-yyyy à HH:mm").format(Calendar.getInstance().getTime()));

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

	class ManageTask extends SwingWorker<Object, Void> {
		private int row;

		public ManageTask(int row) {
			this.row = row;
		}

		/*
		 * Main task. Executed in background thread.
		 * https://docs.oracle.com/javase/tutorial/uiswing/examples/components/
		 * ProgressBarDemo2Project/src/components/ProgressBarDemo2.java
		 */
		@Override
		protected Object doInBackground() throws SQLException {
			if (getRowRessourceID(row) != null) {
				switch (ressourceType) {
				case "Client":
					ClientDAO clientDAO = new ClientDAO();
					return clientDAO.getClient((int) getRowRessourceID(row));

				case "Vehicule":
					VehiculeDAO vehiculeDAO = new VehiculeDAO();
					return vehiculeDAO.getVehicule((String) getRowRessourceID(row));

				default:
					break;
				}
			}

			return null;
		}

		/*
		 * Executed in event dispatch thread
		 */
		@Override
		protected void done() {
			try {
				Object ressource = get();
				if (ressource != null) {
					switch (ressourceType) {
					case "Client":
						new ManageClientView((Client) ressource).run(frame);
						break;

					case "Vehicule":
						new ManageVehicleView((Vehicule) ressource).run(frame);

					default:
					}
				}

			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
