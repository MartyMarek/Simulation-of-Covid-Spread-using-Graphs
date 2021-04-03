/****************************************************************************************************
 * Class: LinkedList
 * Description:
 * LinkedList is the class that holds the linked list information such as the head and tail.
 * For the purposes of this assignment it is kept as lean as possible; Only 'must have' methods are
 * implemented for maximum performance. 
 *
 * @author Team #23
 *
 ****************************************************************************************************/


public class LinkedList<Obj> {
	
	private Node<Obj> head;
	private Node<Obj> tail;
	
	//keeps the total length of the linked list (total num of nodes)
	private int length;
	
	//default constructor 
	public LinkedList () {
		head = null;
		tail = null;
		length = 0;
	}
	
	//get method for the length (note: we have no set method because you should
	//not be able to set length from outside the LinkedList Class
	public int getLength() {
		return length;
	}	
	
	public Node<Obj> getHead() {
		return head;
	}
	
	public Node<Obj> getTail() {
		return tail;
	}
	
	//this method adds a new generic object to the end of the list
	public void addNode(Obj newObject) {
		
		Node<Obj> newObj = new Node<Obj>(newObject);
		
		//check if tail is null (ie. we have an empty list)
		if (tail == null) {
			//then both head and tail are the newNode
			head = newObj;
			tail = newObj;
			
			length++;
		}
		else {
			//add the new node to the tail
			tail.setNext(newObj);
			
			//then update the tail
			tail = newObj;
			
			length++;
		}
	}
	
	//deletes a node from the linked list based on the parameter provided
	public void deleteNode(Obj deleteObj) {
		
		//if the list is empty, do nothing
		if (length == 0) {
			return;
		}
		
		//if there is only 1 item in the list check if its equal 
		if (length == 1) {
			if (head.getValue().equals(deleteObj)) {
				head = null;
				tail = null;
				length--;
			}
			return;
		}
		
		//if there are only 2 items in the list, check head and tail
		if (length == 2) {
			if (head.getValue().equals(deleteObj)) {
				head = tail;
				length--;
				return;
			}
			
			if (tail.getValue().equals(deleteObj)) {
				tail = head;
				length--;
				return;
			}			
		}
		
		//if there are more than 2 items in our list we iterate through 
		//the list recursively until we find a match or run out of nodes 
		recursiveDelete(null, head, deleteObj);
		
		length--;
	}
	
	//helper function to the deleteNode method, that recursively iterates through the list
	//and deletes an object if found
	private void recursiveDelete(Node<Obj> prev, Node<Obj> current, Obj delete) {
		//do we have a match?
		if (current.getValue().equals(delete)) {
			if (prev == null) {
				head = current.getNext();
			}
			else {
				prev.setNext(current.getNext()); // repoint the previous nodes link to this nodes next node
				current.setNext(null); //setting this nodes next link to null will delete it from the list
			}
		}
		else {
			if (current.getNext() != null) {
				recursiveDelete(current, current.getNext(), delete);
			}
		}
	}
	
	//this method returns true if it finds the given object
	public boolean find(Obj obj) throws NullPointerException {
		
		//if the head of the linked list is null return false right away
		if (head == null) {
			return false;
		}
		
		//otherwise start at the head and iterate through until we hit the tail or find our object
		Node<Obj> pointer = head;
				
		do {
			if (pointer.getValue().equals(obj)) {
				return true;
			}
			else if (pointer == tail) {
				return false;
			}
			else {
				pointer = pointer.getNext();
			}
		}
		while(pointer != tail);
		
		return false;
		
	}
	
	//this method converts the linked list of objects into an array of the same object
	public SuperArray<Obj> convertToArray() {
		//if there is nothing to convert return empty array
		if (length == 0) {
			return new SuperArray<Obj>();
		}
		
		try {
			
			//otherwise, create an array 
			SuperArray<Obj> array = new SuperArray<Obj>();
			
			// create a node pointer and set it to the beginning
			Node<Obj> pointer = head;

			//now add each node's object into the array
			while (pointer != null) {
				array.add(pointer.getValue());
				pointer = pointer.getNext();
			}
			
			//once we have all the elements, return the array.
			return array;
		}
		catch (ArrayIndexOutOfBoundsException aoe) {
			//something has gone wrong with the array
			return null;
		}
		catch (NullPointerException npe) {
			//something has gone wrong with the linked list
			return null;
		}
		catch (Exception e) {
			//something terrible has happened 
			return null;
		}
	}


}
