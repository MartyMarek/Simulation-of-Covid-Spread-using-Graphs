/*************************************************************************************************************
 * Generic Class: SuperMatrix
 * Description:
 * SuperMatrix is a generic class that leverages SuperArray to build a 2 dimensional array (matrix). The Matrix can be
 * symmetrical or asymmetrical - this is set by using the corresponding constructor of this class. 
 * The class includes a test method that prints the contents of the backend storage (Which is bigger than the actual matrix itself),
 * however, this method should not be used for other than testing purposes. 
 * 
 * @author Team #23
 *
 * @param <Obj>
 */
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
	
	//returns the current row index in the array (this is to add to the end of the current array)
	public int getCurrentRowIndex() {
		return rows.getCurrentIndex();
	}
	
	//returns the pointer value to the next index add() will add to
	public int getCurrentRowIndexPointer() {
		return rows.getCurrentIndexPointer();
	}
	
	//returns where the next column item will go when added 
	public int getCurrentColumnIndexPointer() {
		
		//we need to find the first row that has values and return its current column index
		for (int i = 0; i < rows.getLength(); i++) {
			if (rows.getObject(i) != null) {
				return rows.getObject(i).getCurrentIndexPointer();
			}
		}
		return 0;
	}
	
	//get the column index value of the first not null row in the matrix 
	public int getCurrentColumnIndex() {
		
		//we need to find the first row that has values and return its current column index
		for (int i = 0; i < rows.getLength(); i++) {
			if (rows.getObject(i) != null) {
				return rows.getObject(i).getCurrentIndex();
			}
		}
		return 0;
	}
	
	//will return the current length of the matrix column
	public int getCurrentColumnLength() {
		
		//we need to find the first row that has values and return its current column length
		for (int i = 0; i < rows.getLength(); i++) {
			if (rows.getObject(i) != null) {
				return rows.getObject(i).getLength();
			}
		}
		return 0;
	}
	
	
	//returns the current object at given index
	//rows and columns start at 0
	public Obj getObject(int row, int column) throws ArrayIndexOutOfBoundsException {
		return rows.getObject(row).getObject(column);
	}
	
	//return an entire row based on a given row index
	public SuperArray<Obj> getRow(int index) {
		return rows.getObject(index);
	}
	
	//return an entire row based on a given row index
	public SuperArray<Obj> getColumn(int index) {
		SuperArray<Obj> col = new SuperArray<Obj>();
		for (int r=0; r < rowCount; r++) 
			col.add(rows.getObject(r).getObject(index));
		return col;
	}
	
	//sets the object at a given row/column
	//rows and columns start at 0
	public void setObject(int row, int column, Obj obj) throws ArrayIndexOutOfBoundsException {
		rows.getObject(row).setObject(column, obj);
	}
	
	//This will add rows and keep the column expansion symmetrical to the row expansion
	//(ie. 10 x 10 to expand to 20 x 20)
	public void addSymmetricalRow() throws NullPointerException {
		
		//if the current array is full we need to expand it both for row and for column at the same time
		if (rows.getLength() == rowCount) {
			//create a new column size (double it)
			columnCount *= 2;
			
			//then we need to expand every row to the new length
			for (int i = 0; i < rowCount; i++) {
				if (rows.getObject(i) != null) {
					rows.getObject(i).reSize(columnCount);  
				}		
				
			}
		}

		SuperArray<Obj> newRow = new SuperArray<Obj>(columnCount); //new column count if we reach row limit
		rows.add(newRow);

		rowCount++;
	}
	
	
	//adds a row to the matrix and sync deleted index numbers to the new row as well
	//this is used by the incidence matrix (or any matrix that is not symmetrical)
	public void addRow() {
		
		//when we add a row in the middle 
		if(getCurrentRowIndexPointer() < rowCount) {
			rowCount--;	
		}
		
		//adding a row with the length of the current column
		SuperArray<Obj> newRow;
		
		if (getCurrentColumnLength() < 10) {
			newRow = new SuperArray<Obj>(10);  //minimum of 10 columns to start
		}
		else {
			newRow = new SuperArray<Obj>(getCurrentColumnLength());
		}
		
		//initialise the row
		newRow.setCurrentIndex(getCurrentColumnIndex());
		
		//we also needs to assign the current deleted index list to the new row 
		//so that it's in sync with all other rows. 
		LinkedList<Integer> list = getDeletedColumnIndexList();
		
		if (list == null) {
			rows.add(newRow);
		}
		else {
			newRow.setDeletedIndexList(list);
			rows.add(newRow);
		}
		rowCount++;
	}
	
	//returns the deleted (stored) column index list
	private LinkedList<Integer> getDeletedColumnIndexList() {
		
		for (int i = 0; i < rowCount; i++) {
			if (rows.getObject(i) != null) {				
				return rows.getObject(i).getDelIndexList();
			}
		}
		return null;
	} 

	//deletes an entire row from the matrix (doesn't actually delete it)
	//it saves it for use by the next added vertex 
	public void deleteRow(int rowNum) {
		rows.deleteAtIndex(rowNum);
		
	}
	
	//adds the given object to each row in the matrix 
	public void addColumn(Obj column) throws NullPointerException {
		
		for (int i = 0; i < rowCount; i++) {
			if (rows.getObject(i) != null) {				
				rows.getObject(i).add(column);
			}
		}
	}
		
	//deletes a column from the matrix 
	public void deleteColumn(int columnNum) throws NullPointerException {
		//iterate through each row array and delete the given index
		for (int i = 0; i < rowCount; i++) {
			if (rows.getObject(i) != null) {
				rows.getObject(i).deleteAtIndex(columnNum);
			}
		}
	}
		

}
