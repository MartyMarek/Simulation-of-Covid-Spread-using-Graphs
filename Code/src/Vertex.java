/**************************************************************************************************************
 * Class: Vertex
 * Description:
 * Holds the vertex details of a SIR Model Graph (ie. an index pointer and the SIRSTATE)
 * 
 * @author Team #23
 *
 */

public class Vertex {
	
	//holds the index pointer value for the edges of this vertex
	//this works with all given types of graphs (ie. adjacency list, adjacency matrix and incidence matrix)
	private int indexPointer; //for adj. matrix we use this value both for column and row of the matrix 
	
	//we also store the SIR State in each Vertex
	private SIRState state;
	
	//default constructor 
	public Vertex() {
		
		//default index is 0
		indexPointer = 0;
		
		//our default state value is S - susceptible 
		state =  SIRState.S;

	}
	
	public Vertex(int index) throws IndexOutOfBoundsException {
		this();
		//Validation of index
		
		//index value must be 0 or higher
		if (index < 0) {
			throw new IndexOutOfBoundsException("Array index must be 0 or higher.");
		}
		else {
			indexPointer = index;
		}
		
	}
	
	//get methods
	public SIRState getState() {
		return state;
	}
	
	public void setState(SIRState state) {
		this.state = state;
	}
	
	public int getIndexPointer() {
		return indexPointer;
	}
	
	//this method toggles the state enum from S to I to R. 
	//once on R it stays on R. It will return the updated state value 
	public SIRState toggleState() {
		
		if (state == SIRState.S) {
			state = SIRState.I;
		}
		else if (state == SIRState.I) {
			state = SIRState.R;
		}
		
		return state;
	}

}
