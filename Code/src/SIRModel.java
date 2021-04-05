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
    	
    	int counter = 0;
    	
    	while (counter++ < 15) {
    		
    		String[] newlyInfected = updateInfected(graph, infectionProb);
    		
    		String[] newlyRecovered = updateRecovered(graph, recoverProb);
    		
    		sirModelOutWriter.print(counter + ": ");
    		
    		displayUpdates(newlyInfected, newlyRecovered, sirModelOutWriter);
    		
    	}
    	
    	
    	/* TESTING
    	//while stop condition is not met
    	String[] newlyInfected = updateInfected(graph, infectionProb);
    	
    	for (int i = 0; i < newlyInfected.length; i++) {

    		System.out.println(newlyInfected[i]);
    		
    	}
    	
    	System.out.println("*****");
    	
    	String[] newlyRecovered = updateRecovered(graph, recoverProb);
    	
    	for (int i = 0; i < newlyRecovered.length; i++) {

    		System.out.println(newlyRecovered[i]);
    		
    	} */
    	
    	
    	
    } // end of runSimulation()
    
    /************************** VALIDATE ASSUMPTION ***************************/
    //this method sets the infected nodes on the graph at the beginning of the simulation 
    //for now we are making the assumption that all vertex state start off as S in the beginning 
    //of the simulation
    private void initInfected(ContactsGraph graph, String[] seedVertices) {
    	
    	for (int i = 0; i < seedVertices.length; i++) {
    		if (seedVertices[i] != null) {
    			graph.toggleVertexState(seedVertices[i]); //we are assuming that all vertex state start off as S
    		}
    	}
    }
    

    //implemented in the AbstractGraph class
    private String[] updateInfected(ContactsGraph graph, float infectionProb) {
    	
    	return ((AbstractGraph)graph).updateInfected(infectionProb);
    }
    
    //implemented in the AbstractGraph class
    private String[] updateRecovered(ContactsGraph graph, float recoverProb) {
    	
    	return ((AbstractGraph)graph).updateRecovered(recoverProb);
    }
    
    //displays the newly infected and newly recovered lists on the last turn
    private void displayUpdates(String[] infected, String[] recovered, PrintWriter pw) {
    	
    	pw.print("[");
    	for (int i = 0; i < infected.length; i++) {

    		pw.print(infected[i] + " ");
    		
    	}
    	
    	pw.print("]  :  [");
    	
    	for (int i = 0; i < recovered.length; i++) {

    		pw.print(recovered[i] + " ");
    		
    	}
    	
    	pw.println("]");
    	
    }
    
} // end of class SIRModel
