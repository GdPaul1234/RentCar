package view.component;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public class WaitingDialog extends JDialog {
	
	private static final long serialVersionUID = 7578532940661029282L;
	private JProgressBar progressBar;

	/**
	 * Create the dialog.
	 */
	public WaitingDialog(Component frame) {
		Box contentPane = Box.createVerticalBox();
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);
		
		Component verticalGlue = Box.createVerticalGlue();
		contentPane.add(verticalGlue);

		progressBar = new JProgressBar();
		contentPane.add(progressBar);
		progressBar.setIndeterminate(true);
		progressBar.setPreferredSize(new Dimension(175,25));
		
		Component verticalStrut = Box.createVerticalStrut(10);
		contentPane.add(verticalStrut);
		JLabel waitLabel = new JLabel("Veuillez patienter...");
		waitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		waitLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		contentPane.add(waitLabel);
		
		pack();
		setResizable(false);
		setLocationRelativeTo(frame);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}
	
	
	public void close() {
		progressBar.setValue(100);
		dispose();
	}

}
