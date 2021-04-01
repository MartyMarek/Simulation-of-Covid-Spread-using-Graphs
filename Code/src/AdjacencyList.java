import java.io.PrintWriter;


/**
 * Adjacency list implementation for the AssociationGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2019.
 */
public class AdjacencyList extends AbstractGraph
{
	
	//this array will store a linked list for each vertex
	SuperArray<LinkedList<String>> adjList;
	
    /**
	 * Constructs empty graph.
	 */
    public AdjacencyList() {
    	super();
    	
    	adjList = new SuperArray<LinkedList<String>>();

    } // end of AdjacencyList()


    public void addVertex(String vertLabel) {
    
    	//create and add a new vertex to the map if vertLabel does not already exist
    	if (!map.containsKey(vertLabel)) {
    		
    		//put the new vertlabel as a new key and connect it with a new vertex 
    		//the new vertex is also given the current index for the adjList (our array)
    		map.put(vertLabel, new Vertex(adjList.getCurrentIndex()));
    		
    		//now add a new linked list to the array
    		adjList.add(new LinkedList<String>());	
    	}

    } // end of addVertex()


    public void addEdge(String srcLabel, String tarLabel) {
        
    	try {
	    	//first we need to check that both labels exist
	    	if (map.containsKey(srcLabel) && map.containsKey(tarLabel) ) {
	    		
	    		//now we get the source vertex and its index in the array
	    		Vertex vertex = map.get(srcLabel);
	    		if (vertex != null) {
	    			//now we can find the linkedlist (Adj list for this vertex)
	    			LinkedList<String> list = adjList.getObject(vertex.getIndexPointer());
	    			
	    			//now add the target label if it doesn't already exists
	    			if(!list.find(tarLabel)) {
	    				list.addNode(tarLabel);
	    			}
	    			
	    		}		    		
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

    //could have bugs: in some rare cases edges are not removed from list when vertex is deleted.
    public void deleteEdge(String srcLabel, String tarLabel) {
    	try {
	    	//first we need to check that both labels exist
	    	if (map.containsKey(srcLabel) && map.containsKey(tarLabel) ) {
	    		
	    		//now we get the source vertex and its index in the array
	    		Vertex vertex = map.get(srcLabel);
	    		if (vertex != null) {
	    			//now we can find the linkedlist (Adj list for this vertex)
	    			LinkedList<String> list = adjList.getObject(vertex.getIndexPointer());
	    			
	    			//delete the given edge from the linkedlist
	    			list.deleteNode(tarLabel);
	    			
	    		}		    		
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


    public void deleteVertex(String vertLabel) {
        //we need to delete the vertex from the map and the array
    	
    	//check if it exists first..
    	if (map.containsKey(vertLabel)) {
    		
    		//get our index pointer for the vertex 
    		int index = map.get(vertLabel).getIndexPointer();
    		
    		//delete the edges (linked list) from the adjacency list
    		adjList.deleteAtIndex(index);
    		    		
    		//we then also need to delete any edge references to the deleted vertex
        	//this is the most expensive part of the adjacency list, as it needs
        	//to iterate through every linked list and delete the reference
    		for (String n: map.keySet()) {
    			deleteEdge(n, vertLabel);
    		}
    		
    		//finally remove the vertex from the map
    		map.remove(vertLabel);
    		
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
        // Testing only
    	for (String n: map.keySet()) {
    		LinkedList<String> printList = adjList.getObject(map.get(n).getIndexPointer());
    		System.out.print("Key: " + n + " ");
    		System.out.print("Index: " + map.get(n).getIndexPointer() + " Edges: ");
    		Node<String> printNode = printList.getHead();
    		
    		while (printNode != null) {
    			System.out.print(printNode.getValue() + ", ");
    			printNode = printNode.getNext();
    		}
    		
    		System.out.println("");    		
    	}
    } // end of printEdges()

} // end of class AdjacencyList
