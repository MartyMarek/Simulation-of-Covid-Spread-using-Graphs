import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;

/**
 * Abstract class to allow you to implement common functionality or definitions.
 * All three graph implement classes extend this.
 *
 * Note, you should make sure to test after changes.  Note it is optional to
 * use this file.
 *
 * @author Jeffrey Chan, 2021.
 * @author of additional methods Team #23
 */

public abstract class AbstractGraph implements ContactsGraph
{
	//create the map here as every type of graph will use this
	protected HashMap<String, Vertex> map;
	
	//keeps track of the total infections throughout the history of the graph
	//Please note: this isn't the current total infection, rather the total ever!
	protected int totalInfections;
	
	//default constructor
	public AbstractGraph() {
		map = new HashMap<String, Vertex>();
		
		totalInfections = 0;
		
	}
	
	
	/**
	 * abstract method that needs implementation by each graph time (it's used for edge testing)
	 * @param srcLabel
	 * @param tarLabel
	 * @return boolean to indicate whether an edge exists between given vertices
	 */
	public abstract boolean isEdge(String srcLabel, String tarLabel);
	
	
	/**
	 * @return returns the total number of edges
	 */
	public abstract int countEdges();
	
	
	/**
	 * @return returns and array of existing edges contained in this graph
	 */
	public abstract Edge[] getEdges();
	
	
	/**
	 * @param total
	 * @param start
	 * @return returns the number of khops it takes to reach the total provided
	 * if there are not enough neighbours to reach the total 0 is returned. 
	 */
	public int howManyKhops(int total, String start) {
		
		if (!map.containsKey(start)) {
    		//issue system error
    		System.err.println("> Vertex does not exists!");
    		return 0;
		}
		else {
			//keeps track of whether  we have found enough vertices to put into our neighbourhood
			boolean enough = false;
			int khops = 1; //start with 1 khop to see if we have enough neighbours..
			String[] neighbours;
			
			//we need to compare each khop to see if we have reached 
			//a plateau (no more vertices can be reached)
			int prev = 0;
			
			while (!enough) {
				neighbours = kHopNeighbours(khops, start);
				
				//if the length of the neighbour list is larger than the total we need
				//we found the number of khops needed to reach total..
				if (neighbours.length >= total) {
					return khops;
				}
				//if the previous set of neighbours was the same as this one, we can't advance anymore
				else if (prev >= neighbours.length) {  
					return 0;
				}
				else { //we are not quite there yet..
					prev = neighbours.length;
					khops++;
				}
			}
			return 0;
		}

	}
	
	
	/**
	 * @return this method tells us whether the graph is complete (no more edges can be added)
	 */
	public boolean isComplete() {
		int vertexCount = map.size();
		
		return ((vertexCount * (vertexCount - 1))/2 == countEdges());
	}
	

	/**
	 * @param amount
	 * @return returns true if the amount of new edges can be added to this graph before it completes(cannot have more edges)
	 */
	public boolean roomForEdges(int amount) {
		int vertexCount = map.size();
		
		return ((vertexCount * (vertexCount - 1))/2 >= countEdges() + amount);
	}
	
	
	/**
	 * method that will iterate through this graph and determine which nodes
	 * will turn into new infected and new recovered. 
	 * @param iProb
	 * @param rProb
	 * @return Returns a list of both via StatusList class
	 */
	public StatusList updates(float iProb, float rProb) {
		
		StatusList sList = new StatusList();
		Random rand = new Random();
		SIRState state;
		
		//iterate through the vertices and determine their current status
		for (String n: map.keySet()) {
			state = map.get(n).getState();
			if (state == SIRState.S) {
				//if susceptible check its connections
				String[] contacts = this.kHopNeighbours(1, n);
				
				//for each connection that is infected, this vertex 
				//has a change of catching it based on given probability
				for (int i = 0; i < contacts.length; i++) {
					//check for infection
					if (map.get(contacts[i]).getState() == SIRState.I) {
						//roll the dice and based on result infect (or not) this vertex 
						float chance = rand.nextFloat();
						
						if (chance <= iProb) {
							//infection!! add to the infection list
							sList.addInfection(n);
							sList.incrementTotalInfected();
							//once we are infected once we can break from the loop
							break;
						}
					}
				}
			}
			else if (state == SIRState.I) {  //if already infected
				float chance = rand.nextFloat();
				
				if (chance <= rProb) {
					//recovery!!!
					sList.addRecovery(n);
				}
				else {
					//stays as infected so add it to our total
					sList.incrementTotalInfected();
				}
			}
		}
		
		return sList;
	}
	
	
	/**
	 * updates this graph using a new infected and new recovered list
	 * @param sList
	 */
	public void updateGraph(StatusList sList) {
		
		//iterate through the newly infected list and update each vertex
		String[] newlyInfected = sList.getInfected();
		
		for (int i = 0; i < newlyInfected.length; i++) {
			//get the vertex from the map and toggle its state
			map.get(newlyInfected[i]).toggleState();
			totalInfections++;
		}
		
		String[] newlyRecovered = sList.getRecovered();
		
		for (int i = 0; i < newlyRecovered.length; i++) {
			//get the vertex from the map and toggle its state
			map.get(newlyRecovered[i]).toggleState();
		}
	}
	
	/**
	 * set the vertex state based on parameters provided 
	 * @param vertLabel
	 * @param state
	 */
	public void setVertexState(String vertLabel, SIRState state) {
		//check if the vertex exists..
    	if (map.containsKey(vertLabel)) {    		
    		map.get(vertLabel).setState(state);
    	}
    	else {
    		//issue system error
    		System.err.println("> Vertex " + vertLabel + " does not exists!");
    		
    		throw new NullPointerException("Vertex doesn't exists");
    	}
	}
			
	/**
	 * @param size
	 * @return random list of valid vertex names picked from the current graph
	 */
	public String[] randomListArray(int size) {
		return DataGenerator.pickRandom(map, size);
	}
	
	/**
	 * @param size
	 * @return generates a random list of vertex names that don't currently exists in the loaded graph
	 */
	public String[] generateRandomVertex(int size) {
		return DataGenerator.generateRandomVertexList(size);
	}
	
	
	/**
	 * @param amount
	 * @return creates valid edges (amount up to given amount) using the DataGenerator 
	 */
	public Edge[] generateRandomEdgeList(int amount) {
		return DataGenerator.randomNewEdges(this, amount);
	}
	
	/**
	 * @param amount
	 * @return generates a set of random edges that don't currently exists in the graph
	 */
	public Edge[] pickRandomEdges(int amount)
	{
		return DataGenerator.randomExistingEdges(this, amount);
	}
	
	/**
	 * Creates an input data file to be used for automating simulations 
	 * @param infections
	 * @param infProb
	 * @param recProb
	 * @param connected
	 * @param pw
	 * @param command
	 */
	public void generateDataFile(int infections, float infProb, float recProb, 
									boolean connected, PrintWriter pw, String command) {
		
		DataGenerator.generateSimulationFile(this, infections, infProb, recProb, connected, pw, command);
	}
	
	
	/**
	 * @return total number of vertices 
	 */
	public int getVertexSize() {
		return map.size();
	}
	
	/**
	 * @return the hashmap that vertices are stored in 
	 */
	public HashMap<String, Vertex> getMap() {
		return map;
	}
	
	/**
	 * @return total infected vertices
	 */
	public int getTotalInfections() {
		return totalInfections;
	}
	
} // end of abstract class AbstractGraph
