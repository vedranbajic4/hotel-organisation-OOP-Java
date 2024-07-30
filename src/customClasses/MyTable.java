package customClasses;

import javax.swing.table.AbstractTableModel;


public class MyTable extends AbstractTableModel  {
	private static final long serialVersionUID = -5519372712630599241L;
		
	private String[] columnNames = {"Dodatna usluga", "Uzimate?"};
	
	//private Object[][] data;

    private Object[][] data;
    
	public MyTable() {
	}
	public MyTable(Object[][] data) {
		this.data = data;
	}
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    @Override
    public Class<?> getColumnClass(int c) {
        if (c == 1) {
            return Boolean.class;  // Druga kolona će biti checkbox
        }
        return String.class;  // Prva kolona je string
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        if(col == 0) return false;
        else return true;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
    	data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
