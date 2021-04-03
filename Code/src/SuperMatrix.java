
public class SuperMatrix<Obj> {
	
	//a matrix is an array of arrays (ie rows and columns)
	
	SuperArray<SuperArray<Obj>> rows;
	
	//the default constructor will create a matrix of default array size (ie. 10 by 10)
	public SuperMatrix() {
		rows = new SuperArray<SuperArray<Obj>>();
		
		int i = 0;
		
		while (i++ < rows.getLength()) {
			rows.add(new SuperArray<Obj>());
		}
		
	}
	
	//constructor to create an asymmetrical matrix
	public SuperMatrix(int numRows, int numColumns) throws NegativeArraySizeException {
		rows = new SuperArray<SuperArray<Obj>>(numRows);
		
		int i = 0;
		
		while (i++ < numRows) {
			rows.add(new SuperArray<Obj>(numColumns));
		}
	}
	
	//returns the current object at given index
	//rows and columns start at 0
	public Obj getObject(int row, int column) throws ArrayIndexOutOfBoundsException {
		return rows.getObject(row).getObject(column);
	}
	
	//sets the object at a given row/column
	//rows and columns start at 0
	public void setObject(int row, int column, Obj obj) {
		rows.getObject(row).setObject(column, obj);
		
	}
	
	//Please note: this is for testing purposes only, don't run this with a large matrix
	public void printMatrix() {
		
		int i, j = 0;
		
		for (i = 0; i < rows.getLength(); i++) {
			for (j = 0; j < rows.getObject(i).getLength(); j++) {
				System.out.print(rows.getObject(i).getObject(j));
			}
			System.out.println();
		}
	}

}
