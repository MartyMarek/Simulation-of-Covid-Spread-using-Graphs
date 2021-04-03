import java.io.PrintWriter;


/**
 * Adjacency matrix implementation for the GraphInterface interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2021.
 */
public class AdjacencyMatrix extends AbstractGraph
{
	
	//this is our matrix (of boolean to track edges) 
	SuperMatrix<Boolean> adjMatrix; 

	/**
	 * Contructs empty graph.
	 */
    public AdjacencyMatrix() {
    	super();
    	
    	//The adjacency matrix will use the same map coordinates to represent 
    	//rows and columns in our matrix. Therefore, we don't need a second map
    	
    	//instantiate the matrix 
    	adjMatrix  = new SuperMatrix<Boolean>(); // initial size of 10 x 10 will be created
    	
    } // end of AdjacencyMatrix()

    //complete
    public void addVertex(String vertLabel) {
    	//create and add a new vertex to the map if vertLabel does not already exist
    	if (!map.containsKey(vertLabel)) {
    		
    		//put the new vertlabel as a new key and connect it with a new vertex 
    		//the new vertex is also given the current index from our matrix
    		map.put(vertLabel, new Vertex(adjMatrix.getCurrentRowIndex()));
    		adjMatrix.addRow();

    	}
    } // end of addVertex()

    //complete
    public void addEdge(String srcLabel, String tarLabel) {
    	//don't allow self loops
    	if (srcLabel.equals(tarLabel)) {
    		return;
    	}
        
    	try {
	    	//first we need to check that both labels exist
	    	if (map.containsKey(srcLabel) && map.containsKey(tarLabel) ) {
	    		
	    		//now we get the source vertex and its index in the array
	    		Vertex sourceVertex = map.get(srcLabel);
	    		Vertex targetVertex = map.get(tarLabel);
	    		
	    		int sourceIndex = sourceVertex.getIndexPointer();
	    		int targetIndex = targetVertex.getIndexPointer();
	    		
	    		//source is the row and target is the column in our matrix 
	    		Boolean edge = true;
	    		adjMatrix.setObject(sourceIndex, targetIndex, edge);
	    		
	    		//for the adjacency matrix, we have to mirror the values
	    		adjMatrix.setObject(targetIndex, sourceIndex, edge);
	    	}
    	}
	    catch (ArrayIndexOutOfBoundsException aie) {
	    	//something went wrong with accessing the array with that index number
	    }
    	catch (NullPointerException npe) {
    		//something went wrong with accessing the linked list
    	}
	    catch (Exception e) {
	    	//Something else has gone wrong
	    }
    } // end of addEdge()


    public void toggleVertexState(String vertLabel) {
        // Implement me!
    } // end of toggleVertexState()

    //complete
    public void deleteEdge(String srcLabel, String tarLabel) {
        
    	//same as add but we set the corresponding value to false
    	
    	//self loops don't exist
    	if (srcLabel.equals(tarLabel)) {
    		return;
    	}
        
    	try {
	    	//first we need to check that both labels exist
	    	if (map.containsKey(srcLabel) && map.containsKey(tarLabel) ) {
	    		
	    		//now we get the source vertex and its index in the array
	    		Vertex sourceVertex = map.get(srcLabel);
	    		Vertex targetVertex = map.get(tarLabel);
	    		
	    		int sourceIndex = sourceVertex.getIndexPointer();
	    		int targetIndex = targetVertex.getIndexPointer();
	    		
	    		//source is the row and target is the column in our matrix 
	    		Boolean edge = false;
	    		adjMatrix.setObject(sourceIndex, targetIndex, edge);
	    		
	    		//for the adjacency matrix, we have to mirror the values
	    		adjMatrix.setObject(targetIndex, sourceIndex, edge);
	    	}
    	}
	    catch (ArrayIndexOutOfBoundsException aie) {
	    	//something went wrong with accessing the array with that index number
	    }
    	catch (NullPointerException npe) {
    		//something went wrong with accessing the linked list
    	}
	    catch (Exception e) {
	    	//Something else has gone wrong
	    }
    	
    } // end of deleteEdge()

    //complete
    public void deleteVertex(String vertLabel) {
        
    	//check if the vertex exists..
    	if (map.containsKey(vertLabel)) {
    		
    		//get the index to delete from the matrix
    		int index = map.get(vertLabel).getIndexPointer();
    		
    		//delete both the row and column reference from the matrix
    		adjMatrix.deleteColumn(index);
    		adjMatrix.deleteRow(index);

    	}
    } // end of deleteVertex()


    public String[] kHopNeighbours(int k, String vertLabel) {
        // Implement me!

        // please update!
        return null;
    } // end of kHopNeighbours()


    public void printVertices(PrintWriter os) {
    	//testing only right now
    	for (String n: map.keySet()) {
    		System.out.print(n + " ");
    		System.out.print(map.get(n).getIndexPointer() + " ");
    		System.out.println(map.get(n).getState().toString());
    		
    	}
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
        adjMatrix.printMatrix();
    } // end of printEdges()

} // end of class AdjacencyMatrix
