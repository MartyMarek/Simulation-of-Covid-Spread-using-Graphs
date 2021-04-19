/******************************************************************************************************************
 * Class : StatusList
 * Description:
 * This class holds two SuperArrays to store a newly infected list and a newly recovered list
 * from a contacts graph. It also keeps track of the total infected nodes in the graph.
 * 
 * @author Team #23
 *
 */


public class StatusList {

	//keeps track of the total infections in a contacts graph
	int totalInfected;
	
	//stores each new infected vertex name
	SuperArray<String> newInfected;
	
	//stores each new recovered vertex name
	SuperArray<String> newRecovered;
	
	public StatusList() {
		newInfected = new SuperArray<String>();
		newRecovered = new SuperArray<String>();
		
		totalInfected = 0;
	}
	

	//adds the given vertex label to the newly infected list
	public void addInfection(String vertLabel) {
		newInfected.add(vertLabel);
	}
	
	//adds the given vertex label to the newly recovered list
	public void addRecovery(String vertLabel) {
		newRecovered.add(vertLabel);
	}
	
	//returns a list of all infected names
	public String[] getInfected() {
		return newInfected.convertToStringArray();
	}
	
	//returns a list of all recovered vertex names
	public String[] getRecovered() {
		return newRecovered.convertToStringArray();
	}
	
	//increments the total infected
	public void incrementTotalInfected() {
		totalInfected++;
	}
	
	//decrements the total infected 
	public void decrementTotalInfected() {
		totalInfected--;
	}
	
	//returns total infected 
	public int getTotalInfected() {
		return totalInfected;
	}
	
	//returns the total amount of infected in the new list
	public int getNewlyInfectedTotal() {
		return newInfected.getTotalItems();
	}
	
	//returns the total amount of recovered in the new list
	public int getNewlyRecoveredTotal() {
		return newRecovered.getTotalItems();
	}
}
