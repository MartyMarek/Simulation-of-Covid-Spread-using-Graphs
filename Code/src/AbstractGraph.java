import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
	


} // end of abstract class AbstractGraph
