import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;

public final class DataGenerator {
	
	//default contructor
	public DataGenerator() {
		
	}
	
	public static SuperArray<String> pickRandom(SuperArray<String> list) {
		
		return list;
		
	}
	
	
	public static void pickRandom(HashMap<String, Vertex> vertexMap, int amount, PrintWriter pw) {
		Random rand = new Random(); //random numbers between 1 and the number of vertices (+1 for inclusive)
		
		//create an array of vertices from our map
		String[] list = vertexMap.keySet().toArray(new String[vertexMap.keySet().size()]);
		
		//this will store our randomly generated vertex names 
		String[] generated = new String[amount];
		
		int randomIndex;
		
		//now generate index numbers between 0 and the length of the array (exclusive)
		for (int i = 0; i < amount; i++) {
			randomIndex = rand.nextInt(list.length);
			
			//we then add the vertex name to our randomly generated list
			generated[i] = list[randomIndex];
			
		}
		
		//we now have our random list, so write out (Save to a specific file)
		for (int i = 0; i < generated.length; i++) {
			
			//OUTPUT HERE WILL DEPEND ON THE TEST FILE NEEDED
			
			pw.println(generated[i]);
		}
		
	}

}
