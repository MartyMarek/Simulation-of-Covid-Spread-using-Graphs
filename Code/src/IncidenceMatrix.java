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
	 * at the cost of slower vertex deletions. I'm making the assumption
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
    		map.put(vertLabel, new Vertex(incMatrix.getCurrentRowIndexPointer()));
    		incMatrix.addRow();
    	}
    } // end of addVertex()


    //REFACTOR
    public void addEdge(String srcLabel, String tarLabel) {
    	// Don't allow self loops.
    	if (srcLabel.equals(tarLabel)) {
    		return;
    	}
		// Check if both vertices exist.	
    	if (map.containsKey(srcLabel) && map.containsKey(tarLabel) ) {
    		
    		//we create a new edge 
    		Edge newEdge = new Edge(srcLabel, tarLabel);
    		
    		//we also create its reverse so we can easly look this up
    		//NOTE: we can also simplify this by created a better
    		//hashing algo in Edge.hashCode()
    		Edge newEdgeReverse = new Edge(tarLabel, srcLabel);
    		
    		//get the current column index (this is automatically updated in SuperArray (ie SuperMatrix)
    		Integer edgeIndex = incMatrix.getCurrentColumnIndexPointer();
    		
    		//Boolean falseIndicator = false;
    		
    		/* RE-STRUCTURE THIS WHOLE SECTION */
    		
    		if (incMatrix.getCurrentColumnIndexPointer() == incMatrix.getCurrentColumnIndex() ) {

    			edgeMap.put(newEdge, edgeIndex);
        		edgeMap.put(newEdgeReverse, edgeIndex);
        		
	    		//add the column to the matrix 
	    		incMatrix.addColumn(null);
    		}
    		else {

    			edgeIndex = incMatrix.getCurrentColumnIndexPointer();
    			
    			edgeMap.put(newEdge, edgeIndex);
        		edgeMap.put(newEdgeReverse, edgeIndex);
        		
        		incMatrix.addColumn(null);
    		}
    		
    		//now update the incidence matrix 
    		Vertex sourceVertex = map.get(srcLabel);
    		Vertex targetVertex = map.get(tarLabel);
    		
    		int sourceIndex = sourceVertex.getIndexPointer();
    		int targetIndex = targetVertex.getIndexPointer();
    		
    		//update the matrix with true values at specified coordinates 
    		Boolean edgeIndicator = true;
    		
    		//source and target are the rows and edge is the column
    		incMatrix.setObject(sourceIndex, edgeIndex, edgeIndicator);
    		incMatrix.setObject(targetIndex, edgeIndex, edgeIndicator);
    		
    	}

    } // end of addEdge()

    
    public void toggleVertexState(String vertLabel) {
    	if (map.containsKey(vertLabel)) {
    		//check if the vertex exists.
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
    				deletionList.add(n.getTarget()); //mark this for edge deletion by saving it to a list
	    			
    			}
    		}
    	}
    	
    	//de-dup the list : will return an exact sized array of de-duped values 
    	String[] list = deletionList.deDuplicate();
    	
    	//PLEASE NOTE: CHECK IF WE NEED TO DE-DUP (if not use below)
    	//String[] list = deletionList.convertToStringArray();
    	//now delete all the edges
    	for (int i = 0; i < list.length; i++) {
    		deleteEdge(vertLabel, list[i]);
    	}
    	
    	//once we have deleted all edges, we can delete the vertex row from the matrix
    	incMatrix.deleteRow(map.get(vertLabel).getIndexPointer());
    	
    	//finally delete the vertex from the map
    	map.remove(vertLabel);
    	
    } // end of deleteVertex()

    
    public String[] kHopNeighbours(int k, String vertLabel) {
    	
    	SuperArray<String> khopResult = new SuperArray<String>();
    	
    	try {
	    	if (map.containsKey(vertLabel)) {
	    		
	    		if (k <= 0) {
	    			return new String[0];
	    		}
	    		else { 		
	    			recursiveKhop(k, vertLabel, khopResult);
	    			
	    			khopResult.deleteAll(vertLabel);
	    			
	    			return khopResult.convertToStringArray();
	    			
	    			//return khopResult.deDuplicate();
	    		}
	    	}
	    	else {
	    		//Sys error 
	    		return new String[0];
	    	}
    	
	    }
	    catch (NullPointerException npe) {
	    	//if we are passed a null string do nothing
	    	return new String[0];
	    }
	    catch (Exception e) {
	    	//unknown error
	    	System.err.println(e.getMessage());
	    	return new String[0];
	    }
	    
	    
    }
    
    //helper function for the khop
    private void recursiveKhop(int k, String vertLabel, SuperArray<String> result) {
    	
    	if (k == 1) {
    		//get each neighbour and add it
    		for (Edge e: edgeMap.keySet()) {
    			if(e.getSource().equals(vertLabel)) {
    				result.add(e.getTarget(), true); //will skip duplicates on add
    			}
    			
    		}
    	}
    	
    	if (k > 1) {
    		for (Edge e: edgeMap.keySet()) {
    			if(e.getSource().equals(vertLabel)) {
    				result.add(e.getTarget(), true);  //will skip duplicates on add
    				recursiveKhop(k - 1, e.getTarget(), result);
    			}
    			
    		}
    	}
    }
    
     
     public void printVertices(PrintWriter os) {
    	for (String n: map.keySet()) {
    		os.print("(" + n + "," + map.get(n).getState().toString() + ") ");
    	}
    	os.println();
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
    	
    	for (Edge e: edgeMap.keySet()) {
    		os.println(e.getSource() + " " + e.getTarget());
    	}
    } // end of printEdges()

} // end of class IncidenceMatrix
