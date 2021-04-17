import java.io.PrintWriter;

/**
 * SIR model.
 *
 * @author Jeffrey Chan, 2021.
 */
public class SIRModel
{

    /**
     * Default constructor, modify as needed.
     */
    public SIRModel() {

    } // end of SIRModel()


    /**
     * Run the SIR epidemic model to completion, i.e., until no more changes to the states of the vertices for a whole iteration.
     *
     * @param graph Input contracts graph.
     * @param seedVertices Set of seed, infected vertices.
     * @param infectionProb Probability of infection.
     * @param recoverProb Probability that a vertex can become recovered.
     * @param sirModelOutWriter PrintWriter to output the necessary information per iteration (see specs for details).
     */
    public void runSimulation(ContactsGraph graph, String[] seedVertices,
        float infectionProb, float recoverProb, PrintWriter sirModelOutWriter)
    {
        
    	//initialise the graph with infected vertices given by seedVertices
    	try {
    		initInfected(graph, seedVertices);
    	}
    	catch (NullPointerException npe) {
    		System.err.println(npe.getMessage());
    	}
    	
    	//keeps track of the iterations in the simulation
    	int counter = 1;
    	
    	//keeps track of the last change step
    	int lastChange = 1;
    	
    	//when this is set to true we stop the simulation
    	boolean stopCondition = false;
    	
    	//keep looping while our stop conditions have not been met
    	while (!stopCondition) {

    		//the status list will store both newly infected and newly recovered vertex lists
    		StatusList sList = new StatusList();
    		
    		//iterates through and calculates infected and recovered based on the probabilities given. 
    		sList = ((AbstractGraph)graph).updates(infectionProb, recoverProb);
    		
    		//commits changes to our graph from above generated lists. 
    		((AbstractGraph)graph).updateGraph(sList);
    		
    		sirModelOutWriter.print(counter + ": ");
    		
    		printStep(sList, sirModelOutWriter);
    		
    		//stop condition - if there are no changes to both newly infected and newly recovered..
    		if (sList.getNewlyInfectedTotal() == 0 && sList.getNewlyRecoveredTotal() == 0) {
    			
    			//if there are no new changes, no new infected nodes and no changes in the last 10 turns we stop
    			if (sList.getTotalInfected() == 0 && (counter >= (lastChange + 9))) {
    				stopCondition = true;
    			}
    		}
    		else {
    			//keeps track of what iteration our last step was. 
    			lastChange = counter;
    		}
    		counter++;
    	}

    } // end of runSimulation()
    
    
    //A second simulation method that allows us to record the rate of increase for infections
    //and recovery - instead of printing each vertex, we print only the number of 
    //newly acquired infections and new recoveries to printwriter 
    public void runIterationSimulation(ContactsGraph graph, String[] seedVertices,
            float infectionProb, float recoverProb, PrintWriter sirModelOutWriter)
        {
            
        	//initialise the graph with infected vertices given by seedVertices
        	try {
        		initInfected(graph, seedVertices);
        	}
        	catch (NullPointerException npe) {
        		System.err.println(npe.getMessage());
        	}
        	
        	//keeps track of the iterations in the simulation
        	int counter = 1;
        	
        	//keeps track of the last change step
        	int lastChange = 1;
        	
        	//when this is set to true we stop the simulation
        	boolean stopCondition = false;
        	
        	//keep looping while our stop conditions have not been met
        	while (!stopCondition) {

        		//the status list will store both newly infected and newly recovered vertex lists
        		StatusList sList = new StatusList();
        		
        		//iterates through and calculates infected and recovered based on the probabilities given. 
        		sList = ((AbstractGraph)graph).updates(infectionProb, recoverProb);
        		
        		//commits changes to our graph from above generated lists. 
        		((AbstractGraph)graph).updateGraph(sList);
        		
        		//sirModelOutWriter.print(counter + ": ");
        		
        		//printStep(sList, sirModelOutWriter);
        		
        		sirModelOutWriter.println(sList.getNewlyInfectedTotal() + " " + sList.getNewlyRecoveredTotal());
        		
        		//stop condition - if there are no changes to both newly infected and newly recovered..
        		if (sList.getNewlyInfectedTotal() == 0 && sList.getNewlyRecoveredTotal() == 0) {
        			
        			//if there are no new changes, no new infected nodes and no changes in the last 10 turns we stop
        			if (sList.getTotalInfected() == 0 && (counter >= (lastChange + 9))) {
        				stopCondition = true;
        			}
        		}
        		else {
        			//keeps track of what iteration our last step was. 
        			lastChange = counter;
        		}
        		counter++;
        	}

        } // end of runSimulation()
    
    //this method sets the infected nodes on the graph at the beginning of the simulation 
    private void initInfected(ContactsGraph graph, String[] seedVertices) {
    	
    	for (int i = 0; i < seedVertices.length; i++) {
    		if (seedVertices[i] != null) {
    			try {
    				((AbstractGraph)graph).setVertexState(seedVertices[i], SIRState.I);
    			}
    			catch (NullPointerException npe) {
    				//ignore vertices that don't exist
    			}
    		}
    	}
    }
    
    //prints the changed nodes (both newly infected and newly recovered) to the PrintWriter
    private void printStep(StatusList sList, PrintWriter pw) {
    	
    	String[] infected = sList.getInfected();
    	String[] recovered = sList.getRecovered();
    	
    	pw.print("[ ");
    	for (int i = 0; i < infected.length; i++) {

    		pw.print(infected[i] + " ");
    		
    	}
    	
    	pw.print("]  :  [ ");
    	
    	for (int i = 0; i < recovered.length; i++) {

    		pw.print(recovered[i] + " ");
    		
    	}
    	
    	pw.println("]");
    	
    }
    
} // end of class SIRModel
