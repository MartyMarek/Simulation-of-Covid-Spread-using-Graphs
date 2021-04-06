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
	
	//keeps track of the last row index 
	private int lastRow;
	
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
		
		//lastRow = 0;
		
	}
	
	//constructor to create an asymmetrical matrix
	public SuperMatrix(int numRows, int numColumns) throws NegativeArraySizeException {
		rows = new SuperArray<SuperArray<Obj>>(numRows);
		
		rowCount = numRows;
		columnCount = numColumns;
		
		lastRow = 0;
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
	
	//return an entire row based on a given row index
	public SuperArray<Obj> getRow(int index) {
		return rows.getObject(index);
	}
	
	//sets the object at a given row/column
	//rows and columns start at 0
	public void setObject(int row, int column, Obj obj) throws ArrayIndexOutOfBoundsException {
		rows.getObject(row).setObject(column, obj);
	}
	
	//add default sized row (10) to the Matrix 
	public void addRow() {
		
		//adding a row is simple just create a new array and add it
		SuperArray<Obj> newRow = new SuperArray<Obj>();
		rows.add(newRow);
		
		//if the index we added this last row is larger than our existing last row
		//need a method in SuperArray to return the last index
		
		rowCount++;
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
	
	
	
	/**************** DANGEROUS CODE *******************/
	//use with care
	//add a row of given size to the matrix 
	public void addRow(int size) {
		//adding a row is simple just create a new array and add it
		SuperArray<Obj> newRow = new SuperArray<Obj>(size);
		
		rows.add(newRow);
		
		rowCount++;
	}
	
	/****************************************************/
	
	//deletes an entire row from the matrix (doesn't actually delete it)
	//it saves it for use by the next added vertex 
	public void deleteRow(int rowNum) {
		rows.deleteAtIndex(rowNum);
		//rowCount--;
	}
	
	public void addColumn(Obj column) {
		for (int i = 0; i < rowCount; i++) {
			if (rows.getObject(i) != null) {
				rows.getObject(i).add(column);
			}
		}
		
	}
	
	/*************** MARK FOR DELETION *******************/
	/* current depenency - IncidenceMatrixOriginal.addEdge() */
	public void addColumn() throws NullPointerException {
		// Needed for incidence matrix and need to add to ensure array resize
		// occurs when we hit allocated limit.		
		for (int i = 0; i < rowCount; i++) {
			if (rows.getObject(i) != null) {
				rows.getObject(i).add(null);
			}
		}
		columnCount++; 
	}
	
	/****************************************************/
	
	
	//deletes a column from the matrix 
	public void deleteColumn(int columnNum) throws NullPointerException {
		//iterate through each row array and delete the given index
		for (int i = 0; i < rowCount; i++) {
			if (rows.getObject(i) != null) {
				rows.getObject(i).deleteAtIndex(columnNum);
			}
		}
	}
	
	
	//Please note: this is for testing purposes only, don't run this with a large matrix
	public void printMatrix() {
		
		int i = 0;
		int j = 0;
		
		for (i = 0; i < rowCount; i++) {
			if (rows.getObject(i) != null) {
				for (j = 0; j < rows.getObject(i).getLength(); j++) {
					if (rows.getObject(i).getObject(j) == null) {
						System.out.print("n  ");
					}
					else if( (Boolean)rows.getObject(i).getObject(j) == false) {
						System.out.print("0  ");
					}
					else {
						System.out.print("1  ");
					}
				}
				System.out.println();
			}
			else {
				System.out.println("null"); //print this for null rows 
			}
		}
	}
	

}
