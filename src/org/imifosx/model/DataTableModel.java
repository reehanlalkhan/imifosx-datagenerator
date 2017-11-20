package org.imifosx.model;

import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.imifosx.utils.Constants;

/**
 * Created by reehan.a on 03-07-2015.
 */
public class DataTableModel extends AbstractTableModel implements Constants {
	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = 915865442759071119L;

	String[] columnNames;
	Object[][] data;

	public DataTableModel(Map<String, List<String>> result) {
		super();

		int columSize = result.keySet().size();
		int max = 0;
		columnNames = new String[columSize];
		Object[] objColNames = result.keySet().toArray();
		for (int i = 0; i < columSize; i++) {
			columnNames[i] = (String) objColNames[i];
		}

		for (String str : result.keySet()) {
			if (max < result.get(str).size()) {
				max = result.get(str).size();
			}
		}
		data = new Object[max][columSize];
		for (int i = 0; i < columSize; i++) {
			String key = columnNames[i];
			List<String> values = result.get(key);
			for (int j = 0; j < max; j++) {
				if (j < values.size()) {
					data[j][i] = values.get(j);
				} else {
					data[j][i] = EMPTY_STRING;
				}
			}
		}
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	/*
	 * JTable uses this method to determine the default renderer/ editor for
	 * each cell. If we didn't implement this method, then the last column would
	 * contain text ("true"/"false"), rather than a check box.
	 */
	public Class<? extends Object> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	public boolean isCellEditable(int row, int col) {
		// Note that the data/cell address is constant,
		// no matter where the cell appears onscreen.
		if (col < 2) {
			return false;
		} else {
			return true;
		}
	}

}