
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileNotFoundException;

class Main {
	
	//number of villages in graph
	static int numVillage;
	//number of roads in graph
	static int numRoads;
	//stores parent/leader of each village in respective index
	static List<Integer> parent = new ArrayList<Integer>();
	//stores number of villages the respective index is a parent/leader of
	static List<Integer> subset = new ArrayList<Integer>();
	
	

	public static void main(String[] args) {
		
		

		Scanner s = new Scanner(System.in);

		
		//stores input edges
		String [] pairs = new String[2];
		String line;
		
		//next line to read
		line = s.nextLine();
		pairs = line.split(" ");
		//number of villages
		numVillage = Integer.parseInt(pairs[0]);
		//number of roads
		numRoads = Integer.parseInt(pairs[1]);
		
		/* No village has a leader at the beginning, so initializing each village to have
		 * a leader/parent of -1. Additionally, because there are no parents, there are 
		 * also no children for each parent, so I initialize each parent to have 0 children 
		 * in the subset list
		 */
		for(int i =0; i<numVillage;i++) {
			parent.add(-1);
			subset.add(0);
		}
		
		
		
		//parsing line by line to get the roads 
				while (s.hasNextLine()) {
					
					line = s.nextLine();
					pairs = line.split(" ");
					
					//stores the input roads
					int vil1 = Integer.parseInt(pairs[0]);
					int vil2 = Integer.parseInt(pairs[1]);
					
					//gets parent of each village
					int parentNode1 = findParent(vil1);
					int parentNode2 = findParent(vil2);
					/*if the parents don't equal each other, then set 
					 * one village as the child of the other or vice-versa to
					 * show that there is a road between the villages
					 */
					if(parentNode1 != parentNode2) {
						union(vil1, vil2);
					}	
				}

				 
			
		//prints out final answer of number of roads needed
		System.out.print(assignRoads() + "\n");


	};
	
	//returns number of roads needed to connect all villages
	public static int assignRoads() {
		
		//counts number of villages without a leader/parent
		int count = Collections.frequency(parent, -1);

		//returns one less than the number of negative ones if the count > 0
		return (count == 0) ? count : (count-1);
	}
	
	//returns leader/parent of the passed in viallge
	public static int findParent( int index) {
		
		//village is its own parent
		if(parent.get(index) == -1) {
			return index;
		}
		
		return findParent(parent.get(index));
	}
	
	
	public static void union(int vil1, int vil2) {
		//gets parent of first village
		int parent1 = findParent(vil1);
		//gets parent of second village
		int parent2 = findParent(vil2);

		//set the parent of the village with less children as the one with more children 
		if(subset.get(parent1)<subset.get(parent2)) {

			//set the parent
			parent.set(parent1,parent2);
			int intialCount = subset.get(parent2);
			//increment the number of children for the parent by 1
			subset.set(parent2, intialCount++);
		}
		else {

			parent.set(parent2,parent1);
			int intialCount = subset.get(parent1);
			subset.set(parent1, intialCount+1);

		}



	}

}
