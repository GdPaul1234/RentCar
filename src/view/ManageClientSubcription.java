package view;

import java.awt.Component;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import controller.SouscriptionDAO;
import model.ProgrammeFidelite;

public class ManageClientSubcription {
	private Component frame;

	public static void main(String arg[]) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new ManageClientSubcription(null, 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public ManageClientSubcription(Component frame, int clientID) {
		this.frame = frame;
		new ManageSubcriptionTask().execute();

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
					JOptionPane.showInputDialog(frame, "Choisir programme fidélité", "", JOptionPane.QUESTION_MESSAGE,
							null, prgmFideliteList.toArray(), prgmFideliteList.get(0));

				}

			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}

		}
	}

}
