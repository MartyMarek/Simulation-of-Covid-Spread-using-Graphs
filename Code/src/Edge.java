
public final class Edge {

	private String source;
	private String target;
	
	public Edge(String source, String target) {
		
		this.source = source;
		this.target = target;		
	}
	
	public String getSource() {
		return source;
	}
	
	public String getTarget() {
		return target;
	}

	
	public int hashCode() {
		return String.join(source, target).hashCode();
	}
	
	//Need to override the equals method so that we can use the
	//map to find our objects again
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
