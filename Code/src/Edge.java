/****************************************************************************************************
 * Immutable Class: Edge
 * Description:
 * This immutable class is used as a key for the Edge Map of the incidence matrix. 
 * 
 * @author Team #23
 *
 */
public final class Edge {

	private String source;
	private String target;
	
	
	//this constructor must be used to set the initial edge information
	public Edge(String source, String target) {
		
		this.source = source;
		this.target = target;		
	}
	
	//get methods. NOTE: this class cannot have set methods
	public String getSource() {
		return source;
	}
	
	public String getTarget() {
		return target;
	}

	/*********** POSSIBLE IMPROVEMENT ***********/
	//very simple hashcode, this could be improved to work so that both combinations of strings
	//ie. AB BA create the same hashcode. if we do that we would not need to store both 
	//combinations separately for the incidence matrix
	public int hashCode() {
		return (String.join("<->", source, target)).hashCode();
	}
	
	//Need to override the equals method. equals will return true as long as both 
	//string are the same but not necessarily in the same order. 
	public boolean equals(Object obj) {
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		//if it's the exact same object return true
		if (this == obj) {
			return true;
		}
		
		Edge edge = (Edge)obj;
		
		//compare so that the order of values doesn't matter
		//ie. Edge(a, b) = Edge(b, a)
		if (source.equals(edge.getSource()) && target.equals(edge.getTarget())) {
			return true;
		}
		if (source.equals(edge.getTarget()) && target.equals(edge.getSource())) {
			return true;
		}
		
		//otherwise return false
		return false;
		
	}
}
