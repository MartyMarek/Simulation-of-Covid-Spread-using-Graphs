import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

/****************************************************************************************************
 * Class: DataGenerator
 * Description:
 * The DataGenerator is a static final class that is used to facilitate the data generation required
 * for testing graphs. The methods are called encapsulated by AbstractGraph and are called by 
 * commands from RmitCovidModelling.
 *
 * @author Team #23
 *
 ****************************************************************************************************/

public final class DataGenerator {
	

	/**
	 * default constructor
	 */
	public DataGenerator() {
		
	}
	
	/**
	 * creates a set of random existing edges from an input graph of size amount
	 * @param graph
	 * @param amount
	 * @return an Edge[]
	 * @throws ArrayIndexOutOfBoundsException
	 */
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
	

	/**
	 * creates a set of random edges from the given graph and amount
	 * will check for duplicates to make sure new edges don't already exists..
	 * @param graph
	 * @param amount
	 * @return an Edge[]
	 * @throws ArrayIndexOutOfBoundsException
	 */
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
	
	
	//
	/**
	 * use a guid to generate unique strings so we don't need to do a comparison
	 * to the existing list to find duplicates 
	 * @param size
	 * @return list of strings
	 */
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
	
	/**
	 * This method will pick a set of random values from the given hashmap
	 * up to the amount provided by amount. It will return the random values
	 * as an array of strings
	 * @param vertexMap
	 * @param amount
	 * @return
	 */
	public static String[] pickRandom(HashMap<String, Vertex> vertexMap, int amount) {
		Random rand = new Random(); //random numbers between 1 and the number of vertices (+1 for inclusive)
		
		//this tracks whether the random number we generate is already in our list
		//(we want unique numbers for each generation)
		boolean found = false;
		
		//create an array of vertices from our map
		String[] list = vertexMap.keySet().toArray(new String[vertexMap.keySet().size()]);
		
		//this will store our randomly generated vertex names 
		String[] generated = new String[amount];
		
		int randomIndex;
		
		//now generate index numbers between 0 and the length of the array (exclusive)
		for (int i = 0; i < amount; i++) {
			randomIndex = rand.nextInt(list.length);
			
			//check if this new random number already exists..
			for (int j = 0; j < generated.length; j++) {
				if (generated[j] != null) {
					if (generated[j].equals(list[randomIndex])) {
						found = true;
						break;
					}
				}
			}
			//if the random generated value is unique
			if (!found) {
				//then we can add it to our list
				generated[i] = list[randomIndex];
			}
			else { //if the value already exists try again..
				found = false;
				i--;
			}
		}
		
		return generated;
	}
	
	
	/**
	 * This method is used to generate the simulation files for SIR sims. 
	 * The method will look to find an appropriate set of random vertices, 
	 * for example if a cluster of 5 infected vertices is required it will look 
	 * for vertices with at least that many connections. It will also provide
	 * unique values (no duplicate randoms)
	 * 
	 * @param graph
	 * @param infections
	 * @param infProb
	 * @param recProb
	 * @param connected
	 * @param pw
	 * @param command
	 */
	public static void generateSimulationFile(AbstractGraph graph, int infections, 
												float infProb, float recProb, boolean connected, 
													PrintWriter pw, String command) {
		
		//must call this method with minimum 1
		if (infections < 1) {
			System.err.println("Must specify minimum 1 for infection to generate file.");
			return;
		}
		
		//used to store a non-connected random set of vertices for infection
		String[] randomInfectionList = new String[infections];
		
		//used to store a connected vertex and its neighbours for infection
		SuperArray<String> infectionList = new SuperArray<String>();
		
		//stores the label for the starting vertex for connected infection
		String startVertex = null;
		
		//counts how many khops are required to reach out total infections..
		int khops = 0;	
		
		//if we want only one cluster of infections as a starting point,
		//that also has enough neighbours to meet the total infections criteria..
		if(connected) {
			
			String[] randomList = pickRandom(graph.getMap(), graph.getMap().size());
			
			for (int i = 0; i < randomList.length; i++) {
				khops = graph.howManyKhops(infections - 1, randomList[i]);
				
				if (khops > 0) {
					//we have found a random vertex with enough neighbours..
					startVertex = randomList[i];
					break;
				}
			}
			
			//if we didn't find a vertex, then exit
			if (startVertex == null) {
				System.err.println("There are no vertices with enough neighbours to fill the total infections specified");
				return;
			}
				
			//if we did find one then add the starting vertex to our list 
			infectionList.add(startVertex);

			//once we know how many hops we need to take, record neighbours inside out (Breadth first)
			for(int i = 1; i <= khops; i++) {
				String[] klist = graph.kHopNeighbours(i, startVertex);
				
				for (int j = 0; j < klist.length; j++) {
					infectionList.add(klist[j], true); //skip duplicate additions
					
					//if we have reached out target number then finish
					if(infectionList.getTotalItems() == infections) {
						break;
					}
				}
			}
			
			//now assign our temp list to the final list to write to the file
			randomInfectionList = infectionList.convertToStringArray();
			
		}
		else {
			//for non-connected infections (random throughout the graph)
			
			//check if we have enough vertices to meet the infection criteria
			if (graph.getVertexSize() < infections) {
				System.err.println("There are no vertices in the graph to meet the total infections.");
				return;
			}
			
			//generate random set of vertices (no duplicates)
			randomInfectionList = pickRandom(graph.getMap(), infections);
			
		}

		//now we write this all out to the printwriter (the -o file)
		pw.print(command + " ");   // "SIRT" is the timed version of the SIR command, SIRI is the iteration version
		
		//now loop through our list
		for (int i = 0; i < randomInfectionList.length; i++) {
			if (i == 0) {
				pw.print(randomInfectionList[i]);
			}
			else {
				pw.print(";" + randomInfectionList[i]);
			}	
		}
		
		//now add the probabilities..
		pw.println(" " + infProb + " " + recProb);
		
		//finish file with the quit command 
		pw.println("Q");

	}

}
