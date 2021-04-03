import java.io.PrintWriter;
import java.util.HashSet;


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

    // complete
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

    // complete
    public void addEdge(String srcLabel, String tarLabel) {
    	
    	//don't allow self loops
    	if (srcLabel.equals(tarLabel)) {
    		return;
    	}
        
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

    //need to find out more
    public void toggleVertexState(String vertLabel) {
    	//check if the vertex exists..
    	if (map.containsKey(vertLabel)) {
    		
    		//toggle to the next SIR state
    		map.get(vertLabel).toggleState(); 
    	}
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

    // complete
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


    //complete
    public String[] kHopNeighbours(int k, String vertLabel) {
        
    	//first, if the vertex given doesn't exist then warning to System.err should be issued
    	if (!map.containsKey(vertLabel)) {
    		
    		//issue system error
    		
    		//and return a small empty string array 
    		return new String[1];
    	}
    	else if (k <= 0) { //if k is 0 or less return an empty array
    		return new String[1];
    	}
    	else if (k == 1) {
    		//if k is 1 we can simply return this vertex's connections
    		
    		//get our index pointer for the vertex 
    		int index = map.get(vertLabel).getIndexPointer();
    		
    		//from the array we get the linked list (using index) convert it to 
    		//SuperArray then to a basic array and return it 
    		SuperArray<String> sArray = (SuperArray<String>)adjList.getObject(index).convertToArray();
    		return sArray.convertToStringArray();	
    	}
    	else {  //k is greater than 1
    		
    		//create an array to store every node
    		SuperArray<String> kHop = new SuperArray<String>();
    		
    		//run the recursive helper and convert the result to a basic string array
    		
    		//need to de-duplicate any values in the super array first...
    		kHop.deDuplicate();
    		
    		return recursiveKHop(k, vertLabel, kHop).convertToStringArray();
    	}

    } // end of kHopNeighbours()
    
    /* Additional Helper Function */
    //recursive khop helper function for k's larger than 1
    private SuperArray<String> recursiveKHop(int k, String key, SuperArray<String> sArray) {
    	
    	if (k == 0) {
    		return sArray; //return array without any changes
    	}
    	else {
    		//get our index pointer for the vertex 
    		int index = map.get(key).getIndexPointer();
    		
    		//get our linked list for this vertex, add their values to the array 
    		//then iterate through each node
    		LinkedList<String> list = adjList.getObject(index);
    		
    		//convert the linkedlist to a super array and append it to our array
    		//NOTE: append ignores duplicates
    		sArray.append(list.convertToArray());
    		
    		//get the head node from the linked list
    		Node<String> iterator = list.getHead();
    		
    		while (iterator != null) {
    			//call the method again with the next value in the list
    			recursiveKHop(k-1, iterator.getValue(), sArray);
    			
    			iterator = iterator.getNext();
    		}  
    		
    		return sArray;
    		
    	}
    	
    }

    //complete
    public void printVertices(PrintWriter os) {
        
    	for (String n: map.keySet()) {
    		os.print("(" + n + "," + map.get(n).getState().toString() + ") ");
    		
    		//FOR TESTING ONLY
    		System.out.print("(" + n + "," + map.get(n).getState().toString() + ") ");
    	}
    	
    	os.println();
    	
    } // end of printVertices()

    //complete
    public void printEdges(PrintWriter os) {
        
    	for (String n: map.keySet()) {
    		LinkedList<String> printList = adjList.getObject(map.get(n).getIndexPointer());
    		Node<String> printNode = printList.getHead();
    		
    		while (printNode != null) {
    			os.println(n + " " + printNode.getValue());
    			
    			System.out.println(n + " " + printNode.getValue());  //TESTING ONLY
    			
    			printNode = printNode.getNext();
    		}
    	}

    } // end of printEdges()

} // end of class AdjacencyList