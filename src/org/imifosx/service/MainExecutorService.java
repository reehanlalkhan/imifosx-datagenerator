package org.imifosx.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

public class MainExecutorService {

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI(Map<String, List<String>> result,
			String joiningDate, int interval, int amount, int duration,
			String loanDate) {
		// Create and set up the window.
		JFrame frame = new JFrame("iMifosX Data Generator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		DisplayService newContentPane = new DisplayService(result, joiningDate,
				interval, amount, duration, loanDate);
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void displayGUI(final int amount, final String loanDate) {
		final Map<String, List<String>> result = new HashMap<String, List<String>>();

		try {
			// Schedule a job for the event-dispatching thread:
			// creating and showing this application's GUI.
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createAndShowGUI(result, null, 1, amount, 10, loanDate);
				}
			});
		} catch (Exception ioex) {
			ioex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		displayGUI(0, null);
	}

}
