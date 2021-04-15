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
	
	//keeps track of the total number of edges this graph contains 
	//protected int numOfEdges;
	
	//default constructor
	public AbstractGraph() {
		map = new HashMap<String, Vertex>();
		
		//numOfEdges = 0;
		
	}
	
	//abstract method that needs implementation by each graph time (it's used for edge testing)
	public abstract boolean isEdge(String srcLabel, String tarLabel);
	
	//returns the total number of edges
	public abstract int countEdges();
	
	//returns and array of existing edges contained in this graph
	public abstract Edge[] getEdges();
	
	
	//this method tells us whether the graph is complete (no more edges can be added)
	public boolean isComplete() {
		int vertexCount = map.size();
		
		return ((vertexCount * (vertexCount - 1))/2 == countEdges());
	}
	
	//returns true if the amount of new edges can be added to this graph 
	//before it completes(cannot have more edges)
	public boolean roomForEdges(int amount) {
		int vertexCount = map.size();
		
		return ((vertexCount * (vertexCount - 1))/2 >= countEdges() + amount);
	}
	
	//method that will iterate through this graph and determine which nodes
	//will turn into new infected and new recovered. Returns a list of both
	//via StatusList class
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
	
	//updates this graph using a new infected and new recovered list
	public void updateGraph(StatusList sList) {
		
		//iterate through the newly infected list and update each vertex
		String[] newlyInfected = sList.getInfected();
		
		for (int i = 0; i < newlyInfected.length; i++) {
			//get the vertex from the map and toggle its state
			map.get(newlyInfected[i]).toggleState();
		}
		
		String[] newlyRecovered = sList.getRecovered();
		
		for (int i = 0; i < newlyRecovered.length; i++) {
			//get the vertex from the map and toggle its state
			map.get(newlyRecovered[i]).toggleState();
		}
	}
	
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
	

	//uses the data generator to create a random list of vertices from our map of vertices 
	public void randomList(int size, PrintWriter pw) {
		DataGenerator.pickRandom(map, size, pw);
	}
	
	public String[] randomListArray(int size) {
		return DataGenerator.pickRandom(map, size);
	}
	
	public String[] generateRandomVertex(int size) {
		return DataGenerator.generateRandomVertexList(size);
	}
	
	//creates valid edges (amount up to given amount) using the DataGenerator 
	public Edge[] generateRandomEdgeList(int amount) {
		return DataGenerator.randomNewEdges(this, amount);
	}
	
	public Edge[] pickRandomEdges(int amount)
	{
		return DataGenerator.randomExistingEdges(this, amount);
	}
	
	
	public int getVertexSize() {
		return map.size();
	}
	
	public HashMap<String, Vertex> getMap() {
		return map;
	}
	
} // end of abstract class AbstractGraph
