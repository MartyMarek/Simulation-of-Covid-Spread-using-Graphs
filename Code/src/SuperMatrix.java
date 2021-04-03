
public class SuperMatrix<Obj> {
	
	//a matrix is an array of arrays (ie rows and columns)
	
	SuperArray<SuperArray<Obj>> rows;
	
	//keeps track of how many rows we have in the matrix
	private int rowCount;
	
	//keeps track of how many columns we have 
	private int columnCount;
	
	//the default constructor will create a matrix of default array size (ie. 10 by 10)
	public SuperMatrix() {
		rows = new SuperArray<SuperArray<Obj>>();
		
		//for default matrix we start with no rows but column size will be 10
		//Please Note: This is just the allocated storage for the matrix
		//NOT the actual Matrix size
		rowCount = 0;
		columnCount = 10;		
		
	}
	
	//constructor to create an asymmetrical matrix
	public SuperMatrix(int numRows, int numColumns) throws NegativeArraySizeException {
		rows = new SuperArray<SuperArray<Obj>>(numRows);
		
		rowCount = numRows;
		columnCount = numColumns;
	}
	
	//returns the current row index
	public int getCurrentRowIndex() {
		return rows.getCurrentIndex();
	}
	
	//returns the current column index (same as rows for symmetrical matrices) 
	public int getCurrentColumnIndex() {
		return rows.getObject(0).getCurrentIndex(); //use the first row to get the column index 
	}
	
	
	//returns the current object at given index
	//rows and columns start at 0
	public Obj getObject(int row, int column) throws ArrayIndexOutOfBoundsException {
		return rows.getObject(row).getObject(column);
	}
	
	//sets the object at a given row/column
	//rows and columns start at 0
	public void setObject(int row, int column, Obj obj) throws ArrayIndexOutOfBoundsException {
		rows.getObject(row).setObject(column, obj);
		
		return;
	}
	
	//add default sized row (10) to the Matrix 
	public void addRow() {
		//adding a row is simple just create a new array and add it
		SuperArray<Obj> newRow = new SuperArray<Obj>();
		rows.add(newRow);
	}
	
	//add a row of given size to the matrix 
	public void addRow(int size) {
		//adding a row is simple just create a new array and add it
		SuperArray<Obj> newRow = new SuperArray<Obj>(size);
	}
	
	
	
	//Please note: this is for testing purposes only, don't run this with a large matrix
	public void printMatrix() {
		
		int i, j = 0;
		
		for (i = 0; i < rows.getTotalItems(); i++) {
			for (j = 0; j < rows.getObject(i).getLength(); j++) {
				if (rows.getObject(i).getObject(j) == null ||
						(Boolean)rows.getObject(i).getObject(j) == false) {
					System.out.print("0 ");
					
				}
				else {
					System.out.print("1 ");
				}
			}
			System.out.println();
		}
	}

}
