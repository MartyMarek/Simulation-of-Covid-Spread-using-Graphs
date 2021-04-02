/****************************************************************************************
 * Generic Class: SuperArray
 * Description:
 * This is the replacement array class that is used across the project. It will double its size
 * when it runs out of indexes and adds each new object into the next index slot available. If there
 * are deletions from the array - their indexes are tracked and next addition will fill the gap. This is
 * to maximise speed performance. 
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
	
	//keeps track of the total number of current items in this array
	private int totalItems;
	
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
		totalItems = 0;

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
		
		//increment total number of items in our array
		totalItems++;
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
		//decrease total number of items in our array
		totalItems--;
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
	
	//returns the total length of the array
	public int getLength() {
		return array.length;
	}
	
	/* WARNING: this method returns an array that can potentially
	 * have nulls in the middle of it
	 */
	public Obj[] convertToBasic() {
		return array;
	}
	
	/* converToStringArray can further be optimised by checking whether 
	 * the deleted item linkedlist is empty or not
	 */
	
	//to return a clean string array, we need to do a clean up 
	//and convert the generic object array into a string array
	public String[] convertToStringArray() {
		
		//first if we have an empty array return null
		if (array.length == 0) {
			return null;   // could return empty string[] here instead. 
		}
		
		//create an array with the exact number of items we need
		String[] stringList = new String[totalItems];
		
		//to do a clean up we must get rid of any potential
		//nulls in the middle of the array. to do that we can iterate
		//through the array cast the object into a string and add it to stringList
		
		int indexPointer = 0;
		int itemCount = 0;
		int newIndexPointer = 0;
		
		//we can terminate as soon as we have all the items or if we have
		//reached the end of the array 
		while (indexPointer < array.length && itemCount < totalItems) {
			
			//check if the next slot is null 
			if (array[indexPointer] != null) {
				//convert the object to a string and assign it to our new array
				//increment both index pointers afterwards
				stringList[newIndexPointer++] = (String)array[indexPointer++];
				
				//also increment itemCount
				itemCount++;
			}
			else {
				//ignore the null value and increment the indexPointer
				indexPointer++;
			}
		}
		
		return stringList;
		
	}
	
	
	//appends a SuperArray to this array.
	//Both MUST contain the same object type, Duplicates are ignored, Order is ignored 
	public void append(SuperArray<Obj> newArray) {
		
		//if we get a null input, do nothing
		if (newArray == null) {
			return;
		}
		
		//we must iterate the whole array because there could be nulls in the middle of the array
		//one way to fix this is the keep track of how many items we have in each super array
		//and stop iterating once we reach that amount 
		
		for (int i = 0; i < newArray.getLength(); i++) {
			
			Obj nextObj = newArray.getObject(i);
			
			//if the object we got from the array is not null then add it to our array
			if (nextObj != null) {
				this.add(nextObj);
			}
		}
		
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
