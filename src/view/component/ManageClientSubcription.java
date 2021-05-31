package view.component;

import java.awt.Component;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import controller.SouscriptionDAO;
import model.ProgrammeFidelite;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class ManageClientSubcription {
	
	CompletableFuture<Void> task = new CompletableFuture<>();

	private Component frame;
	private int clientID;

	public ManageClientSubcription(Component frame, int clientID) {
		this.clientID = clientID;

		this.frame = frame;
	}

	/**
	 * Launch the application
	 * 
	 */
	public CompletableFuture<Void> run() {

		new ManageSubcriptionTask().execute();
		return task;
	}

	class ManageSubcriptionTask extends SwingWorker<List<ProgrammeFidelite>, Void> {

		/*
		 * Main task. Executed in background thread.
		 * https://docs.oracle.com/javase/tutorial/uiswing/examples/components/
		 * ProgressBarDemo2Project/src/components/ProgressBarDemo2.java
		 */
		@Override
		protected List<ProgrammeFidelite> doInBackground() throws SQLException {
			SouscriptionDAO souscriptionDAO = new SouscriptionDAO();
			return souscriptionDAO.getListPrgmFidelite();
		}

		/*
		 * Executed in event dispatch thread
		 */
		@Override
		protected void done() {

			try {
				List<ProgrammeFidelite> prgmFideliteList = get();

				if (prgmFideliteList != null && prgmFideliteList.size() > 0) {
					ProgrammeFidelite response = (ProgrammeFidelite) JOptionPane.showInputDialog(frame,
							"Choisir programme fidélité", "", JOptionPane.QUESTION_MESSAGE, null,
							prgmFideliteList.toArray(), prgmFideliteList.get(0));

					if (response != null)
						new ApplySubcription(clientID, response.getFideliteID()).execute();

				}

			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}

		}
	}

	class ApplySubcription extends SwingWorker<Void, Void> {
		private int clientID;
		private int fideliteID;

		public ApplySubcription(int clientID, int fideliteID) {
			this.clientID = clientID;
			this.fideliteID = fideliteID;
		}

		@Override
		protected Void doInBackground() {
			System.out.println("Apply client subcription in DB");
			try {
				new SouscriptionDAO().subcribeClientToProgrammeFidelite(clientID, fideliteID);
			} catch (
			SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void done() {
			task.complete(null);
		}

	}

}
