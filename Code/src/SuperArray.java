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
	
	@SuppressWarnings("unchecked")
	//constructor that creates an array of given size
	public SuperArray(int size) throws NegativeArraySizeException {
		currentIndex = 0;
		totalItems = 0;

		//initialise the array with our initial size 
		array = (Obj[])new Object[size];
		
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
	
	public int[] getLiveIndexList() {
		SuperArray<Integer> list = new SuperArray<Integer>();
		
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				list.add(i);
			}
		}
		
		return list.convertToIntArray();
	}
	
	
	
	public int getCurrentIndex() {
		//check if the deletedIndexList has items
		if (deletedIndexList.getLength() > 0) {
			return deletedIndexList.getHead().getValue();
		}
		
		//otherwise return the index after the end of the array
		return currentIndex;
	}
	
	public int getTotalItems() {
		return totalItems;
	}
	
	public Obj getObject(int index) throws ArrayIndexOutOfBoundsException {
		return array[index];
	}
	
	public void setObject(int index, Obj obj) throws ArrayIndexOutOfBoundsException {
		array[index] = obj;
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
		
		//first if we have an empty array return an empty array
		if (array.length == 0) {
			String[] emptyList = new String[initialIndexSize];
			return emptyList; 
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
				stringList[newIndexPointer++] = array[indexPointer++].toString(); 
				
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
	
	
	public int[] convertToIntArray() {
		
		//first if we have an empty array return an empty array
		if (array.length == 0) {
			int[] emptyList = new int[initialIndexSize];
			return emptyList; 
		}
		
		//create an array with the exact number of items we need
		int[] intList = new int[totalItems];
		
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
				intList[newIndexPointer++] = Integer.parseInt(array[indexPointer++].toString()); 
				
				//also increment itemCount
				itemCount++;
			}
			else {
				//ignore the null value and increment the indexPointer
				indexPointer++;
			}
		}
		
		return intList;
		
	}
	
	
	//appends a SuperArray to this array.
	//Both MUST contain the same object type, 
	//Duplicates are NOT CHECKED (therefore added)
	//Order is ignored 
	public void append(SuperArray<Obj> newArray) {

		int totalCounter = 0;
		
		//if we get a null input, do nothing
		if (newArray == null) {
			return;
		}
		
		//if we get an empty super array, do nothing
		if (newArray.getTotalItems() == 0) {
			return;
		}
		
		for (int i = 0; i < newArray.getLength(); i++) {
			
			Obj nextObj = newArray.getObject(i);
			
			//if the object we got from the array is not null then add it to our array
			if (nextObj != null) {
				this.add(nextObj);
				totalCounter++;
			}
			
			//now check if we have reached our total items
			if (totalCounter == newArray.getTotalItems()) {
				return;
			}
		}
	}
	
	//This method will remove all duplicate values in this array. Mainly used to delete
	//duplicates after the khop neighbor algorithm 
	@SuppressWarnings("unchecked")
	public String[] deDuplicate() {
		//if our array is empty or only 1 item return a string array as is
		if (totalItems == 0 || totalItems == 1) {
			return this.convertToStringArray();
		}
		
		//first we convert the array into a string array, this also gets rid of NULL values 
		String[] deDup = this.convertToStringArray();
		
		//depending on how many items we have in the array, run different sort algo's
		// need to identify what the optimal value is to make decision. 
		if (totalItems < 10) {
			//run an insertion sort
			deDup = insertionSort(deDup);
			
			//now delete the duplicates and re-assign the result to this array
			deDup = slowDeDup(deDup);
			array = (Obj[])deDup;			
		}
		else {
			//for large number of items we run a quicksort
			
			deDup = quickSort(deDup, 0, deDup.length);
			
			//now delete the duplicates and re-assign the result to this array
			deDup = slowDeDup(deDup);
			array = (Obj[])deDup;
		}
		
		return deDup;

	}
	
	// standard insertion sort to be used for sorting small arrays of strings
	private String[] insertionSort(String[] unsorted) {
		
		int j, i = 1;
		
		for (i = 1; i < unsorted.length; i++) {
			j = i;
			
			try {
				while (j > 0 && (unsorted[j - 1].compareToIgnoreCase(unsorted[j]) > 0) ) {
					//swap items
					String temp = unsorted[j - 1];
					unsorted[j - 1] = unsorted[j];
					unsorted[j] = temp;
					j--;
				}
			}
			catch (NullPointerException npe) {
				//treat nulls as the higher value so we push them all at the
				//back of the array
				if (unsorted[j - 1] == null) {
					//swap items
					String temp = unsorted[j - 1];
					unsorted[j - 1] = unsorted[j];
					unsorted[j] = temp;
					j--;
				}
			}
		}
		
		return unsorted;
	}
	
	//helper function to de-duplicate a sorted array
	//PLEASE NOTE: this is using brute force as not sure if we can use a HashSet here (which is preferable)
	private String[] slowDeDup(String[] duplicates) {
		//create a target string array
		String[] target = new String[duplicates.length];
		
		int i, j = 0;
		
		for (i = 0; i < (duplicates.length - 1); i++) {  //we don't need to compare the last value
			
			//check if the current index value is equal to the next one
			if (!duplicates[i].equals(duplicates[i + 1])) {
				//if it does not equal save the value to the target array
				target[j++] = duplicates[i];
			}
		}
		
		//now assign the last element
		target[j] = duplicates[duplicates.length - 1];
		
		return target;
	}	
	
	//this is a helper function that will sort the array based on its values
	private String[] quickSort(String[] array, int low, int high) {
		
		if (low < high) {
			int pivot = getPivotIndex(array, low, high);
			
			quickSort(array, low, pivot - 1); // range before the pivot
			quickSort(array, pivot + 1, high); //range after the pivot
		}
		
		//get the pivot value for the quicksort..
		return array;
		
	}
	
	//This is the helper function to QuickSort that gets a pivot 
	private int getPivotIndex(String[] array, int low, int high) {
		
		String highValue = array[high];
		
		int smallIndex = low -1;
		
		for (int i = low; i <= high - 1; i++) {
			
			//we have to catch a possible nullpointer exception because
			//our array could have nulls
			try {
				//if the current string is smaller
				if (array[i].compareToIgnoreCase(highValue) < 0) {
					smallIndex++;
					
					//swap the elements
					String temp = array[smallIndex];
					
					array[smallIndex] = array[i];
					array[i] = temp;
				}
			}
			catch (NullPointerException npe) {
				//treat null as the larger value so we move all nulls at the back
				//of the array
				if (array[i] != null && highValue == null) {
					smallIndex++;
					
					//swap the elements
					String temp = array[smallIndex];
					
					array[smallIndex] = array[i];
					array[i] = temp;
				}				
			}
		}
		
		//swap elements
		String temp = array[smallIndex + 1];
		
		array[smallIndex + 1] = array[high];
		array[high] = temp;
		
		return (smallIndex + 1);
		
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
