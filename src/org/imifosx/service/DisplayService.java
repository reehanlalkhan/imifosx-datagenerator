package org.imifosx.service;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import org.imifosx.model.DataTableModel;
import org.imifosx.utils.ArgumentsContainer;
import org.imifosx.utils.Constants;
import org.imifosx.utils.DateDataGenerator;
import org.imifosx.utils.SpringUtilities;

public class DisplayService extends JPanel implements Constants {
	/**
	 * Generated Serial Version UID 
	 */
	private static final long serialVersionUID = 521703867479733177L;

	private JTable table;
	private JTextField dateText;
	private JTextField intervalText;
	private JTextField amountText;
	private JTextField durationText;
	private JTextField loanDateText;
	private JTextField loanMaturityDateText;
	private JLabel validationMessage = new JLabel(EMPTY_STRING, SwingConstants.TRAILING);
	private JButton generateData = new JButton(BUTTON_LABEL);

	private int interval;
	private int amount;
	private int duration;

	public DisplayService(Map<String, List<String>> result) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		createUserInputFields();

		DataTableModel model = new DataTableModel(result);
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(1300, 750));
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setCellSelectionEnabled(true);
		// table.setAutoCreateRowSorter(true);

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);
		// Add the scroll pane to this panel.
		add(scrollPane);

	}

	private void createUserInputFields() {
		// Create a separate form for reading user inputs
		JPanel form = new JPanel(new SpringLayout());
		JLabel date_label = new JLabel(DATE_LABEL, SwingConstants.TRAILING);
		JLabel interval_label = new JLabel(INTERVAL_LABEL, SwingConstants.TRAILING);
		JLabel amount_label = new JLabel(AMOUNT_LABEL, SwingConstants.TRAILING);
		JLabel duration_label = new JLabel(DURATION_LABEL, SwingConstants.TRAILING);

		// Create the components and add them to the panel
		dateText = new JTextField();
		dateText.setText("2017-10-02");
		date_label.setLabelFor(dateText);
		form.add(date_label);
		form.add(dateText);

		intervalText = new JTextField();
		intervalText.setText("1");
		interval_label.setLabelFor(intervalText);
		form.add(interval_label);
		form.add(intervalText);

		amountText = new JTextField();
		amountText.setText("1000");
		amount_label.setLabelFor(amountText);
		form.add(amount_label);
		form.add(amountText);

		durationText = new JTextField();
		durationText.setText("10");
		duration_label.setLabelFor(durationText);
		form.add(duration_label);
		form.add(durationText);

		JLabel loan_label = new JLabel(LOAN_DATE, SwingConstants.TRAILING);
		loanDateText = new JTextField();
		loanDateText.setEditable(false);
		loan_label.setLabelFor(dateText);
		form.add(loan_label);
		form.add(loanDateText);

		JLabel mature_label = new JLabel(MATURED_DATE, SwingConstants.TRAILING);
		loanMaturityDateText = new JTextField();
		loanMaturityDateText.setEditable(false);
		mature_label.setLabelFor(loanMaturityDateText);
		form.add(mature_label);
		form.add(loanMaturityDateText);

		SpringUtilities.makeCompactGrid(form, 3, 4, 6, 6, 6, 6);
		add(form);
		addButton();
	}

	/**
	 * This method will add the generate button, validation error display label
	 */
	private void addButton() {
		JPanel form = new JPanel(new SpringLayout());
		generateData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String date = dateText.getText();
				if (validateData(date, intervalText.getText(), amountText.getText(), durationText.getText())) {
					DateDataGenerator generator = new DateDataGenerator();
					Map<String, List<String>> tableData = generator.generateData(interval, duration, amount, date);
					DataTableModel model = new DataTableModel(tableData);
					table.setModel(model);
					loanDateText.setText(generator.getLoanDate());
					loanMaturityDateText.setText(generator.getLoanMaturityDate());
				}
			}
		});

		form.add(validationMessage);
		form.add(generateData);
		SpringUtilities.makeCompactGrid(form, 1, 2, 6, 6, 6, 6);
		add(form);
	}

	private boolean validateData(String date, String interval, String amount, String duration) {
		if (date == null || date.isEmpty()) {
			validationMessage.setText(INVALID_DATE);
			return false;
		}
		try {
			new ArgumentsContainer(date);
		} catch (Exception ex) {
			validationMessage.setBackground(Color.RED);
			validationMessage.setText(INVALID_DATE);
			return false;
		}
		try {
			this.interval = Integer.parseInt(interval);
			this.amount = Integer.parseInt(amount);
			this.duration = Integer.parseInt(duration);
		} catch (Exception ex) {
			validationMessage.setBackground(Color.RED);
			validationMessage.setText(INVALID_INPUT);
			return false;
		}
		validationMessage.setBackground(getBackground());
		validationMessage.setText(EMPTY_STRING);
		return true;
	}

}
