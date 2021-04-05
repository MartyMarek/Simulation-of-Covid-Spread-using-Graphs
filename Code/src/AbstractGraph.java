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
 */
public abstract class AbstractGraph implements ContactsGraph
{
	//create the map here as every type of graph will use this
	protected HashMap<String, Vertex> map;
	
	//default constructor
	public AbstractGraph() {
		map = new HashMap<String, Vertex>();
	}
	
	//method that returns the current state of a given Vertex
	public SIRState getVertexState(String vertLabel) throws NullPointerException {
		//check if the vertex exists..
    	if (map.containsKey(vertLabel)) {
    		
    		//toggle to the next SIR state
    		return map.get(vertLabel).getState(); 
    	}
    	else {
    		//issue system error
    		System.err.println("> Vertex " + vertLabel + " does not exists!");
    		
    		throw new NullPointerException("Vertex doesn't exists");
    	}
	}
	
	//this method iterates through the entire graph and updates the SIRState
	//of the Vertex based on a given probability. It returns a list of all
	//newly infected vertices 
	public String[] updateInfected(float probability) {
		
		SuperArray<String> newInfections = new SuperArray<String>();
		Random rand = new Random();
		
		// for every vertex in the graph
		for (String n: map.keySet()) {
			//for this vertex, check if its susceptible 
			if (map.get(n).getState() == SIRState.S) {
				//check its connections
				String[] contacts = this.kHopNeighbours(1, n);
				
				//for each connection that is infected this vertex 
				//has a change of catching it based on given probability
				for (int i = 0; i < contacts.length; i++) {
					//check for infection
					if (map.get(contacts[i]).getState() == SIRState.I) {
						//roll the dice and based on result infect (or not) this vertex 
						float chance = rand.nextFloat();
						
						if (chance <= probability) {
							//infection!!
							map.get(n).toggleState();
							newInfections.add(n); //add to the newly infected vertex 
						}
					}
				}
			}
			
		}
		
		//once we are done infecting, return a list of newly infected
		return newInfections.convertToStringArray();		
		
	}
	
    //this method iterates through the graph and based on a given probability
    //toggles any infected vertices to recovered. It then returns the newly recovered
    //vertex names
	public String[] updateRecovered(float probability) {
		SuperArray<String> newRecoveries = new SuperArray<String>();
		Random rand = new Random();
		
		// for every vertex in the graph
		for (String n: map.keySet()) {
			//for this vertex, check if its susceptible 
			if (map.get(n).getState() == SIRState.I) {
				//if it is infected, throw the dice
				float chance = rand.nextFloat();
				
				if (chance <= probability) {
					//recovery!!!
					map.get(n).toggleState();
					newRecoveries.add(n);
				}
			}
				
		}
		
		return newRecoveries.convertToStringArray();
	}


} // end of abstract class AbstractGraph
