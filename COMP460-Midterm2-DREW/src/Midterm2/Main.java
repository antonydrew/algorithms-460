package Midterm2;
import java.util.Arrays;
/**
 * Midterm package for problem2 that tests out function to find max INT given P projectiles
 * Uses DYNAMIC PROGRAMMING to ensure right answer
 * (unlike GREEDY algo which will NOT keep track of MAX)
 * @author Tony Drew
 */

public class Main {
	/**
	 * Function that finds max intercepted projectiles given P Projectiles
	 * @param heights as int array
	 * @return max as int
	 */
	public static int findMaxInt2(int[] heights)
	{   
	    int counter = 0; int max = 0; int hurdle = heights[0]; //init counter and max for loop later on   
	    int lens = heights.length;  //get length of array heights p1 
	    int[][] table = new int[lens][lens+1]; //init 2D array table to store results + extra column for sum
      
	    for(int i = 0 ; i<lens ; i++){  //outer loop by # heights
	    	counter = 0;  //reset counter  
	    	hurdle = heights[i]; //reset hurdle
	    	
	    	for(int j = 0 ; j<lens ; j++){  //inner loop by # heights (again) to form matrix table	
	    		if(heights[j] > heights[i]) table[i][j] = 0; //gun can't fire if higher level so 0
	    		else if(heights[j] <= heights[i] && heights[j] <= hurdle && j >= i) { //check if height < hurdle
	    			table[i][j]=1; //1 is for intercept
	    			hurdle = heights[j]; //increment hurdle height
	    			counter++; } //increment counter since can hit lower target
	    		if(table[i][0] == 0 && heights[0] >= heights[i])  { //check value in first column
	    			table[i][0]= 1;  //1 is for intercept
	    			counter++;  } //increment since can hit first target if P[0] > P[i]-th height
	    		table[i][lens]=counter; //set last column to store sum of 0/1's
	    		if(i>0 && max < counter) max = table[i][lens]; //take optimal amount at each sub-level  
	    		}
	    	}  
	    
	    return max;  //get max interceptions back 
	}
	
	/**
	 * Call to MAIN for testing of coin function
	 * @param args
	 */
	public static void main(String[] args) {
		int[] P = {390,208,156,301,300,171,159,66};  //init heights
		int[] P1 = {24,35,21};  //init more heights to test
		
		long startTime = System.nanoTime(); //start timer
		int ans = findMaxInt2(P);  //call function - can change P here to P1/P2 to test time
		long stopTime = System.nanoTime();  //end timer
        long byteTime =(int)((stopTime - startTime)/ 1000);  //convert from nanoseconds to microseconds
		System.out.println("\n\nMax interceptions is "+ans+" in "+byteTime+" ms");  //see answer which is 6
	}
}
