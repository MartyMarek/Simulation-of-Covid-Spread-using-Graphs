import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public final class DataGenerator {
	
	//default contructor
	public DataGenerator() {
		
	}
	
	//creates a set of random existing edges from an input graph of size amount
	public static Edge[] randomExistingEdges(AbstractGraph graph, int amount) throws ArrayIndexOutOfBoundsException {
		Random rand = new Random(); 
		
		//used to pick an index from the array at random
		int nextIndex = 0;
		
		//this will store edges to delete..
		Edge[] edges = new Edge[amount];
		
		//get the list of existing edges to choose from
		Edge[] existingEdgeList = graph.getEdges();
		
		//it doesn't matter how long this takes to complete so we need to 
		//check for the reverse edge for each chosen and also delete it from our
		//list (so we don't double up with a deletion eg. AB BA 
		for (int i = 0; i < edges.length; i++) {
			
			//pick next random index..
			do {
				nextIndex = rand.nextInt(edges.length*2);
			}
			while (existingEdgeList[nextIndex] == null);  //if null it's already used and deleted 
			
			
			//create a new edge and add it to our list
			Edge nextEdge = new Edge(existingEdgeList[nextIndex].getSource(), existingEdgeList[nextIndex].getTarget());
				
			edges[i] = nextEdge;
				
			//now delete this edge and its inverse from our existing list..
			existingEdgeList[nextIndex] = null;
			Edge reverseEdge = new Edge(nextEdge.getTarget(), nextEdge.getSource());
				
			for (int j = 0; j < existingEdgeList.length; j++) {
				if (existingEdgeList[j] != null) {
					if (existingEdgeList[j].equals(reverseEdge)) {
						existingEdgeList[j] = null;
						break;
					}
				}
			}

		}
		
		return edges;
	}
	
	//creates a set of random edges from the given graph and amount
	//will check for duplicates to make sure new edges don't already exists..
	public static Edge[] randomNewEdges(AbstractGraph graph, int amount) throws ArrayIndexOutOfBoundsException {
		Random rand = new Random(); 
		
		//this will store are new valid edges
		Edge[] edges = new Edge[amount];
		
		//keep track of the number of valid random edges we have created
		int validEdgeCount = 0;
		
		//used to pick the random vertices to match for an edge
		int srcIndex = 0;
		int tarIndex = 0;
		
		String srcLabel;
		String tarLabel;
		
		Boolean found = false;
		
		//get the vertex list from the current graph
		HashMap<String, Vertex> vertexMap = graph.getMap();
		
		//create an array of vertices from our map
		String[] list = vertexMap.keySet().toArray(new String[vertexMap.keySet().size()]);
		
		//randomly pick two vertices, test to see if they have an edge
		//and if they don't add as a new edge to our edge list
		while (validEdgeCount < edges.length) {
			srcIndex = rand.nextInt(list.length);
			tarIndex = rand.nextInt(list.length);
			
			//check to make sure we don't use self edges..
			if (srcIndex != tarIndex) {
				//get the vertex names
				srcLabel = list[srcIndex];
				tarLabel = list[tarIndex];

				//check if this edge already exists..
				if (!graph.isEdge(srcLabel, tarLabel)) {
					
					//also check whether we have already generated this random edge in our list
					for (int i = 0; i < edges.length; i++) {
						if (edges[i] != null) {
							if( ((edges[i].getSource() == srcLabel) && (edges[i].getTarget() == tarLabel)) ||
									((edges[i].getTarget() == srcLabel) && (edges[i].getSource() == tarLabel)) ) {
								found = true;
							}
						}
					}
					
					//if the edge does not exists in our graph nor does it exist in our 
					//random list already.. then add it to our list
					if (!found) {
						//create new random edge and add it to our edge list
						Edge newEdge = new Edge(srcLabel, tarLabel);
						edges[validEdgeCount] = newEdge;
						validEdgeCount++;
					}
					found = false;
					
				}
			}
		}
		return edges;	
	}
	
	
	//use a guid to generate unique strings so we don't need to do a comparison
	//to the existing list to find duplicates 
	public static String[] generateRandomVertexList(int size) {
		
		String[] list = new String[size];
		
		for (int i = 0; i < list.length; i++) {
			//generate unique string
			UUID uuid = UUID.randomUUID();
			String uniqueValue = uuid.toString();
			
			//insert string into array
			list[i] = uniqueValue;
		}
		return list;
	}
	
	public static String[] pickRandom(HashMap<String, Vertex> vertexMap, int amount) {
		Random rand = new Random(); //random numbers between 1 and the number of vertices (+1 for inclusive)
		
		//create an array of vertices from our map
		String[] list = vertexMap.keySet().toArray(new String[vertexMap.keySet().size()]);
		
		//this will store our randomly generated vertex names 
		String[] generated = new String[amount];
		
		int randomIndex;
		
		//now generate index numbers between 0 and the length of the array (exclusive)
		for (int i = 0; i < amount; i++) {
			randomIndex = rand.nextInt(list.length);
			
			//we then add the vertex name to our randomly generated list
			generated[i] = list[randomIndex];
		}
		return generated;
	}
	
	
	//this will allow the creation of input files for the model simulation 
	public static void pickRandom(HashMap<String, Vertex> vertexMap, int amount, PrintWriter pw) {
		Random rand = new Random(); //random numbers between 1 and the number of vertices (+1 for inclusive)
		
		//create an array of vertices from our map
		String[] list = vertexMap.keySet().toArray(new String[vertexMap.keySet().size()]);
		
		//this will store our randomly generated vertex names 
		String[] generated = new String[amount];
		
		int randomIndex;
		
		//now generate index numbers between 0 and the length of the array (exclusive)
		for (int i = 0; i < amount; i++) {
			randomIndex = rand.nextInt(list.length);
			
			//we then add the vertex name to our randomly generated list
			generated[i] = list[randomIndex];
			
		}
		
		//we now have our random list, so write out (Save to a specific file)
		for (int i = 0; i < generated.length; i++) {
			
			//OUTPUT HERE WILL DEPEND ON THE TEST FILE NEEDED
			
			pw.println(generated[i]);
		}
		
	}

}
