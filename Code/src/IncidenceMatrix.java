import java.io.PrintWriter;

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
	SuperArray<String[]> incEdges;

	/**
	 * Contructs empty graph.
	 */
    public IncidenceMatrix() {
    	super();
    	// Initial size of 10 x 10 will be created (SuperMatrix default).
    	incMatrix  = new SuperMatrix<Boolean>(); 
    	// Initial empty size of 10 will be created (SuperArray default).
    	incEdges  = new SuperArray<String[]>();
    } // end of IncidenceMatrix()


    public void addVertex(String vertLabel) {
    	// Create and add a new vertex to the map if vertLabel does not already exist.
    	if (!map.containsKey(vertLabel)) {
    		
    		// Put the new vertLabel as a new key and connect it to a new vertex. 
    		// The new vertex is given the current index from our matrix.
    		map.put(vertLabel, new Vertex(incMatrix.getCurrentRowIndex()));
    		incMatrix.addRow();
    		// If edges have already been added then the column index position for
    		// the new Superarray needs to be advanced to match the other vertices.
    		int checkRow = map.get(vertLabel).getIndexPointer();
    		incMatrix.getRow(checkRow).setCurrentIndex(incMatrix.getCurrentColumnIndex());
    		
    		// REMOVE - testing.
//    		incMatrix.printMatrix();
    	}
    } // end of addVertex()


    public void addEdge(String srcLabel, String tarLabel) {
    	// Don't allow self loops.
    	if (srcLabel.equals(tarLabel)) {
    		// REMOVE - testing. Can use this to trigger a matrix print without changing anything
    		// by trying to set a vertex to be looping i.e. AE t01 t01
    		incMatrix.printMatrix();
    		return;
    	}
		// Check if both keys exist.    	
    	if (map.containsKey(srcLabel) && map.containsKey(tarLabel) ) {
    		// Check if edge already exists.
	    	if (getEdgeIndex(srcLabel, tarLabel) == -1) {	    		
		    	// Create a new column for srcLabale <> tarLabel. As it is undirected we 
		    	// only need one column.
		    	// This feels a bit delicate as it relies on column indexes in incEdges 
		    	// Superarray being identical to the incMatrix Supermatrix.
		    	// TODO - work out if there is a better way!
		    	incEdges.add(new String[] {srcLabel, tarLabel});
		    	int newColumnIndex = incMatrix.getCurrentColumnIndex();
		    	incMatrix.addColumn();
		
		    	int sourceIndex = map.get(srcLabel).getIndexPointer();
		    	int targetIndex = map.get(tarLabel).getIndexPointer();
		    			
				// With the new column update source and target to be true. 
				Boolean edge = true;
				incMatrix.setObject(sourceIndex, newColumnIndex, edge);
				incMatrix.setObject(targetIndex, newColumnIndex, edge);		 
	    	}
    	}
		// REMOVE - testing.
//		incMatrix.printMatrix();
    } // end of addEdge()


    public int getEdgeIndex(String srcLabel, String tarLabel) {
    	int[] edgeList = incEdges.getLiveIndexList();
    	for (int i=0; i<edgeList.length; i++) {
    		// Since only one column for both directions (A > B and B > A), could 
    		// be identified as Source, Target or Target, Source.
    		// REFACTOR - better way to do this? Comparator?    		
    		if ((incEdges.getObject(edgeList[i])[0].equals(srcLabel) || incEdges.getObject(edgeList[i])[0].equals(tarLabel)) && (incEdges.getObject(edgeList[i])[1].equals(srcLabel) || incEdges.getObject(edgeList[i])[1].equals(tarLabel))) {
    			// REMOVE - debug.
//    			System.out.println("Edge exists: " + edgeList[i]);
    			return edgeList[i];
    		}
    	}
    	// REMOVE - debug.
//		System.out.println("Edge does not exist");
    	return -1;
    }
    
    
    public void toggleVertexState(String vertLabel) {
    	if (map.containsKey(vertLabel)) {
    		// Toggle to the next SIR state (S > I > R).
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
        // Find the position in incEdges and use it to remove the column in 
    	// incEdge and incMatrix.    	
    	int edgeIndex = getEdgeIndex(srcLabel, tarLabel);    		
    	if (edgeIndex != -1) {
    		incEdges.deleteAtIndex(edgeIndex);
    		incMatrix.deleteColumn(edgeIndex);
    	}
    	
		// REMOVE - testing.
		incMatrix.printMatrix();
    } // end of deleteEdge()


    public void deleteVertex(String vertLabel) {
    	if (map.containsKey(vertLabel)) {
    		// REFACTOR - thinking that we should keep track of number of edges
    		// so that we can test vertex for edges == 0 to speed things up.
    		
    		// Iterate through incEdges looking for any instance of vertLabel.
    		int[] edgeList = incEdges.getLiveIndexList();
        	for (int i=0; i<edgeList.length; i++) {
        		// Since only one column for both directions (A>B and B>A), verLabel
        		// could be in first or second location. 
            	// REFACTOR - better way to do this? Comparator?    		
        		if (incEdges.getObject(edgeList[i])[0].equals(vertLabel) || incEdges.getObject(edgeList[i])[1].equals(vertLabel)) {
            		incEdges.deleteAtIndex(edgeList[i]);
            		incMatrix.deleteColumn(edgeList[i]);	
        		}
        	}

    		// Edges cleared out, delete the vertex.
        	int index = map.get(vertLabel).getIndexPointer();
    		incMatrix.deleteRow(index);
    		    		
    		//now delete it from the map
    		map.remove(vertLabel);
    	}
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
    	int[] edgeList = incEdges.getLiveIndexList();
    	// REMOVE - debug only.
    	os.println("Number of edges:" + edgeList.length);
    	for (int i=0; i<edgeList.length; i++) {
    		
    			try {
    				os.println(incEdges.getObject(edgeList[i])[0] + " " + incEdges.getObject(i)[1]);
    			}
    			catch (NullPointerException npe) {
    				os.println(npe.getMessage());
    			}
    	}
    } // end of printEdges()

} // end of class IncidenceMatrix
