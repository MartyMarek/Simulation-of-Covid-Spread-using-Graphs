/****************************************************************************************
 * Generic Class: SuperArray
 * Description:
 * This is the replacement array class that is used across the project. It will double its size
 * when it runs out of indexes and adds each new object into the next index slot available. 
 * It's a generic class that can take any type of object and store it in an array. 
 * 
 * @author Team #23
 *
 * @param <Obj> //generic object to store in the array
 */

public class SuperArray<Obj> {
	
	//This is the default initial size used by Java collection classes as well. 
	// Could be tweaked based on input vs performance 
	private final int initialIndexSize = 10;
	
	//keeps track of which index we are up to (if added in a sequence)
	private int currentIndex;
	
	//this is the array storage 
	private Obj[] array;
	
	//we also build a deleted index list to store the index of any deleted 
	//objects from the middle of the array. We then use this for any subsequent
	//additions to the array until it runs out. This saves us having to re-adjust
	//the entire array index every time we delete from the middle.
	private LinkedList<Integer> deletedIndexList;
	
	@SuppressWarnings("unchecked")
	//default constructor 
	public SuperArray() {
		currentIndex = 0;

		//initialise the array with our initial size 
		array = (Obj[])new Object[initialIndexSize];
		
		deletedIndexList = new LinkedList<Integer>();
	}
	
	//adds a new object to the array (at the next available slot)
	public void add(Obj obj) {
		
		//check if the deletedIndexList has items
		if (deletedIndexList.getLength() > 0) {
			//then we get the first item from the list and re-use the index
			array[deletedIndexList.getHead().getValue()] = obj;
			
			//once we have re-used that index, we need to delete it from the list
			deletedIndexList.deleteNode(deletedIndexList.getHead().getValue());
		}
		else { //otherwise keep adding to the end of the array
			
			//need to check if we still have empty slots left
			if(currentIndex == (array.length)) {
				//if we are out of room, need to expand the array
				expandArray();
			}
			
			array[currentIndex] = obj;
			currentIndex++;
		}
	}
	
	public void deleteAtIndex(int index) throws ArrayIndexOutOfBoundsException {
		
		//if the given index in the array is already empty, do nothing 
		if (array[index] == null) {
			return;
		}
		else {
			//otherwise, set the index in the array to null
			array[index] = null;
			
			//and that index to our index queue
			deletedIndexList.addNode(index);
			
		}
	}
	
	public int getCurrentIndex() {
		//check if the deletedIndexList has items
		if (deletedIndexList.getLength() > 0) {
			return deletedIndexList.getHead().getValue();
		}
		
		//otherwise return the index after the end of the array
		return currentIndex;
	}
	
	public Obj getObject(int index) throws ArrayIndexOutOfBoundsException {
		return array[index];
	}
	
	//this method will double the size of the internal array when called
	private void expandArray() {
		
		@SuppressWarnings("unchecked")
		Obj[] tempArray = (Obj[])new Object[array.length * 2];
		
		try {		
			//loop through the existing array and reassign all indexes to new array
			for (int i = 0; i < array.length; i++) {
				tempArray[i] = array[i];
			}
			
		}
		catch (ArrayIndexOutOfBoundsException e ) {
			System.out.println("Error with the Super Array");
		}
		
		//once we have re-assigned all index values, we can reassign the tempArray
		array = tempArray;
		
	}

}
