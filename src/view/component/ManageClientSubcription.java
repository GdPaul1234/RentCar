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

public class ManageClientSubcription {
	private Component frame;
	private int clientID;
	private Integer fideliteID;

	public ManageClientSubcription(Component frame, int clientID) {
		this.clientID = clientID;
		this.frame = frame;
	}

	/**
	 * Launch the application
	 * 
	 */
	public CompletableFuture<Void> run() {
		CompletableFuture<Void> task = CompletableFuture.runAsync(new ManageSubcriptionTask())
				.thenRun(new ApplySubcription(fideliteID));
		task.complete(null);
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

					synchronized (fideliteID) {
						fideliteID = response.getFideliteID();
					}
				}

			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}

		}
	}

	class ApplySubcription extends SwingWorker<Void, Void> {
		private int fideliteID;

		public ApplySubcription(int fideliteID) {
			this.fideliteID = fideliteID;
		}

		@Override
		protected Void doInBackground() throws Exception {
			// Apply client subcription in DB
			SouscriptionDAO souscriptionDAO = new SouscriptionDAO();
			souscriptionDAO.subcribeClientToProgrammeFidelite(clientID, fideliteID);
			return null;
		}

	}

}
