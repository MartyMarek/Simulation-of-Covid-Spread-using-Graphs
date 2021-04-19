/****************************************************************************************************
 * Generic Class: Node<Obj>
 * Description:
 * This class is used in the linked list as a node that store a provided object and a pointer
 * to the next node in the linked list. If it's the last node, it will point to null.
 *
 * @author Team #23
 *
 * @param <Obj>
 ****************************************************************************************************/


public class Node<Obj> {
	
	//points to the next node in the linked list
	private Node<Obj> nextNode;
	
	//stores the object for this node
	private Obj value;
	
	//default constructor
	public Node() {
		nextNode = null;
		value = null;
	}
	
	public Node(Obj obj) {
		this();
		value = obj;
	}
	
	public Node(Obj obj, Node<Obj> next) {
		this(obj);
		nextNode = next;
	}
	
	// get and set methods
	public Node<Obj> getNext() {
		return nextNode;
	}
	public void setNext(Node<Obj> next) {
		this.nextNode = next;
	}
	public Obj getValue() {
		return value;
	}
	public void setValue(Obj value) {
		this.value = value;
	}
	
	
}
