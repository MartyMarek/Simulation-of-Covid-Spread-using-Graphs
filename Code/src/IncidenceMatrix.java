import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Incidence matrix implementation for the GraphInterface interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2021.
 */
public class IncidenceMatrix extends AbstractGraph
{

	// Matrix of boolean to track edges. 
	SuperMatrix<Boolean> incMatrix;
	
	// Map that stores the edge pairs and their column index 
	HashMap<Edge, Integer> edgeMap = new HashMap<Edge, Integer>();

	/* EdgeMap DESIGN DECISION. putting edge as the key instead of the 
	 * index value, will allow faster edge additions and deletions
	 * at the cost of slower edge deletions. I'm making the assumption
	 * that edge looks ups and additions will be executed more frequently.
	 */

	/**
	 * Contructs empty graph.
	 */
    public IncidenceMatrix() {
    	super();
    	incMatrix  = new SuperMatrix<Boolean>(); 

    } // end of IncidenceMatrix()


    public void addVertex(String vertLabel) {
    	// Create and add a new vertex to the map if vertLabel does not already exist.
    	if (!map.containsKey(vertLabel)) {
    		map.put(vertLabel, new Vertex(incMatrix.getCurrentRowIndex()));
    		incMatrix.addSymmetricalRow();
    	}
    } // end of addVertex()


    public void addEdge(String srcLabel, String tarLabel) {
    	// Don't allow self loops.
    	if (srcLabel.equals(tarLabel)) {
    		return;
    	}
		// Check if both vertices exist	
    	if (map.containsKey(srcLabel) && map.containsKey(tarLabel) ) {
    		
    		//we create a new edge 
    		Edge newEdge = new Edge(srcLabel, tarLabel);
    		
    		//we also create its reverse so we can easly look this up
    		//NOTE: we can also simplify this by created a better
    		//hashing algo in Edge.hashCode()
    		Edge newEdgeReverse = new Edge(tarLabel, srcLabel);
    		
    		//get the current column index (this is automatically updated in SuperArray (ie SuperMatrix)
    		Integer edgeIndex = incMatrix.getCurrentColumnIndex();
    		
    		//put the edge into the edge map. the reverse will also give us the same index
    		edgeMap.put(newEdge, edgeIndex);
    		edgeMap.put(newEdgeReverse, edgeIndex);
    		
    		Boolean edgeIndicator = false;
    		//add the column to the matrix (the entire column is initialised to false)
    		incMatrix.addColumn(edgeIndicator);
	    	
    		//now update the incidence matrix 
    		Vertex sourceVertex = map.get(srcLabel);
    		Vertex targetVertex = map.get(tarLabel);
    		
    		int sourceIndex = sourceVertex.getIndexPointer();
    		int targetIndex = targetVertex.getIndexPointer();
    		
    		//update the matrix with true values at specified coordinates 
    		edgeIndicator = true;
    		
    		//source and target are the rows and edge is the column
    		incMatrix.setObject(sourceIndex, edgeIndex, edgeIndicator);
    		incMatrix.setObject(targetIndex, edgeIndex, edgeIndicator);
    		
    	}

    } // end of addEdge()

    
    public void toggleVertexState(String vertLabel) {
    	if (map.containsKey(vertLabel)) {
    		//check if the vertex exists..
        	if (map.containsKey(vertLabel)) {
        		map.get(vertLabel).toggleState();
        	}
        	else {
        		//issue system error
        		System.err.println("> Vertex does not exists!");
        	}
    	}
    } // end of toggleVertexState()


    public void deleteEdge(String srcLabel, String tarLabel) {
        //deleting the edge will require the edge index to be deleted from every row
    	Edge edge = new Edge(srcLabel, tarLabel);
    	Edge edgeReverse = new Edge(tarLabel, srcLabel);

    	Integer index;
    	
    	if(edgeMap.containsKey(edge)) {
    		index = edgeMap.get(edge);
    		incMatrix.deleteColumn(index);
    	}
    	
    	//now delete the edge from the map (need to do this for both combinations)
    	edgeMap.remove(edge);
    	edgeMap.remove(edgeReverse);
    	
    } // end of deleteEdge()
    
    public void deleteVertex(String vertLabel) {
    	
    	SuperArray<String> deletionList = new SuperArray<String>();
    	
    	//before deleting a vertex, we have to delete any edge that it is part of
    	for (Edge  n: edgeMap.keySet()) {
    		
    		//get the row and column index for this edge
    		int colIndex = edgeMap.get(n);
    		Vertex vertex = map.get(vertLabel);
    		int rowIndex = vertex.getIndexPointer();
    		
    		//check if that row/ column is set to true
    		if (incMatrix.getObject(rowIndex, colIndex) != null) {
    			if (incMatrix.getObject(rowIndex, colIndex)) {
	    			//we can't delete edge inside the for loop as the returned edge 
    				//list must stay the same until the loop ends.
    				//we mark this edge for deletion later...
    				deletionList.add(n.getTarget()); //mark this for edge deletion
	    			
    			}
    		}
    	}
    	
    	String[] list = deletionList.convertToStringArray();
    	//now delete all the edges
    	for (int i = 0; i < deletionList.getTotalItems(); i++) {
    		deleteEdge(vertLabel, list[i]);
    	}
    	
    	//once we have deleted all edges, we can delete the vertex
    	incMatrix.deleteRow(map.get(vertLabel).getIndexPointer());
    	
    } // end of deleteVertex()


    public String[] kHopNeighbours(int k, String vertLabel) {
        // Implement me!

        // please update!
        return null;
    } // end of kHopNeighbours()


    public void printVertices(PrintWriter os) {
    	for (String n: map.keySet()) {
    		os.print("(" + n + "," + map.get(n).getState().toString() + ") ");
    	}
    	os.println();
    } // end of printVertices()


    public void printEdges(PrintWriter os) {

 
    } // end of printEdges()

} // end of class IncidenceMatrix
