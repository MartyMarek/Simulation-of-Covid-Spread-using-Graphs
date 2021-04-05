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
    		adjMatrix.addSymmetricalRow();

    	}
    	else {
    		//issue system error
    		System.err.println("> Vertex does not exists!");
    	}
    } // end of addVertex()

    //complete
    public void addEdge(String srcLabel, String tarLabel) {
    	//don't allow self loops
    	if (srcLabel.equals(tarLabel)) {
    		//issue system error
    		System.err.println("> Self loops are not accepted!");
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
	    	else {
	    		//issue system error
	    		System.err.println("> Vertex does not exists! Edge NOT added.");
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


    //complete
    public void toggleVertexState(String vertLabel) {
    	//check if the vertex exists..
    	if (map.containsKey(vertLabel)) {
    		
    		//toggle to the next SIR state
    		map.get(vertLabel).toggleState(); 
    	}
    	else {
    		//issue system error
    		System.err.println("> Vertex " + vertLabel + " does not exists!");
    	}
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
	    	else {
	    		//issue system error
	    		System.err.println("> Vertex does not exists!");
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
        
    	try {
	    	//check if the vertex exists..
	    	if (map.containsKey(vertLabel)) {
	    		
	    		//get the index to delete from the matrix
	    		int index = map.get(vertLabel).getIndexPointer();
	    		
	    		//delete both the row and column reference from the matrix
	    		adjMatrix.deleteColumn(index);
	    		adjMatrix.deleteRow(index);
	    		
	    		//now delete it from the map
	    		map.remove(vertLabel);
	
	    	}
	    	else {
	    		//issue system error
	    		System.err.println("> Vertex does not exists!");
	    	}
    	}
    	catch (NullPointerException npe) {
    		System.err.println("> Vertex does not exists!");
    		return;
    	}
    	catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
    } // end of deleteVertex()


    public String[] kHopNeighbours(int k, String vertLabel) {
        
    	try {
	    	SuperArray<String> sArray;
	    	
	    	LinkedList<String> list = new LinkedList<String>();
	    	
	    	//first, if the vertex given doesn't exist then warning to System.err should be issued
	    	if (!map.containsKey(vertLabel)) {
	    		
	    		//issue system error
	    		System.err.println("> Vertex does not exists!");
	    		
	    		//and return a small empty string array 
	    		return new String[0];
	    	}
	    	else if (k <= 0) { //if k is 0 or less return an empty array
	    		return new String[0];
	    	}
	    	else if (k >= 1) {
	    		//run the recursive function
	    		//sArray = recursiveKHop(k, vertLabel, null, sArray);
	    		
	    		recursiveHop(k, vertLabel, list);
	    		
	    		/************ POSSIBLE PERFORMANCE OPTIMISATION ************/ 
	    		//the deduplicate() will remove any duplicate vertex names and return a string[]
	    		//need to trial whether checking for duplicates in the
	    		//linkedin function before adding each node is faster
	    		//return list.convertToArray().deDuplicate();
	    		sArray = list.convertToArray();
	    		
	    		sArray.deleteAll(vertLabel);
	    		
	    		return sArray.deDuplicate();

	    	}
	    	
	    	return new String[0];
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
    	
    } // end of kHopNeighbours()
    
    private void recursiveHop(int k, String key, LinkedList<String> list) {
    	
    	if (k == 1) {
    		
    		//get the index 
    		int rowIndex = map.get(key).getIndexPointer();
    		
    		//check the value of every column item to see if there is an edge
    		for (String m: map.keySet()) {
    			
    			//exclude thyself
    			if (!m.equals(key)) {
    				
    				int colIndex = map.get(m).getIndexPointer();
    				
    				//we draw a diagonal line from (1,1) to (n,n) and only
    				//look at values above that line
    				//if (colIndex > rowIndex) {
    				
	    				//now check for edges (avoid the null values in our matrix)
		        		if(adjMatrix.getObject(rowIndex, colIndex) != null) {
		    				//if the coordinate at this row an column contains true, then we have an edge
		        			if(adjMatrix.getObject(rowIndex, colIndex)) {
		    					//add to the linkedlist
		        				list.addNode(m);
	
		    				}
		        		}
    				//}
    				
    			}
    		}
    		
    	}
    	
    	else { //if k is higher than 1

    		//get the index 
    		int rowIndex = map.get(key).getIndexPointer();
    		
    		//check the value of every column item to see if there is an edge
    		for (String m: map.keySet()) {
    			
    			//exclude thyself
    			if (!m.equals(key)) {
    				
    				int colIndex = map.get(m).getIndexPointer();
    				
    				//we draw a diagonal line from (1,1) to (n,n) and only
    				//look at values above that line
    				if (colIndex > rowIndex) {
    				
	    				//now check for edges (avoid the null values in our matrix)
		        		if(adjMatrix.getObject(rowIndex, colIndex) != null) {
		    				//if the coordinate at this row an column contains true, then we have an edge
		        			if(adjMatrix.getObject(rowIndex, colIndex)) {
		    					//add to the linkedlist recursively 
		        				list.addNode(m);
		        				recursiveHop(k - 1, m, list);
		    				}
		        		}
    				}
    			}
    		}
    	}
    	
    }
    
    //complete
    public void printVertices(PrintWriter os) {
    	
    	for (String n: map.keySet()) {
    		os.print("(" + n + "," + map.get(n).getState().toString() + ") ");
    	}
    	
    	os.println();

    } // end of printVertices()

    //complete
    public void printEdges(PrintWriter os) {
        
    	int rowIndex = 0;
		int colIndex = 0;
    	
    	try {
	    	for (String n: map.keySet()) {
	    		
	    		//get the index 
	    		rowIndex = map.get(n).getIndexPointer();
	    		
	    		for (String m: map.keySet()) {
	    			//get the index 
	        		colIndex = map.get(m).getIndexPointer();
	        		
		        	//now check for edges (avoid the null values in our matrix)
		        	if(adjMatrix.getObject(rowIndex, colIndex) != null) {
		    			if(adjMatrix.getObject(rowIndex, colIndex)) {
		    				os.println(n + " " + m);
		    			}
		    		}
	    		}
	    	}
    	}
    	catch (ArrayIndexOutOfBoundsException ae) {
    		System.err.println("> Internal Array Access Error. BAD");
    	}

    } // end of printEdges()
    
    

} // end of class AdjacencyMatrix
