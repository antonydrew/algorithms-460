package Midterm;
import java.util.Arrays;

/**
 * Midterm package the test out function to find max INT given T coins and D denoms
 * Uses DYNAMIC PROGRAMMING as noted in Chapter 8 of textbook to get right answer
 * (unlike GREEDY algo which will NOT keep track of MIN # COINS)
 * @author Tony Drew
 */

public class Main {
	/**
	 * Function that finds max int given T coins and D denoms via dynamic programming
	 * @param denom as int array
	 * @param T as int
	 * @return max int given T coins and D denoms
	 */
	public static int findMaxCoinsInt(int[] D, int T)
	{   
	    int counter = 0;  //init int counter for loop later on   
	    Arrays.sort(D); //sort array in ascending order in case not done so by user
	    int denoms = D.length;  //get length of denom array
	    int[] denom = new int[denoms+1]; //init array to insert first value as ZERO to make loop counters intuitive
	    System.arraycopy(D, 0, denom, 1, denoms); denom[0]=0; //insert ZERO as first dummy denom
	    int ddenoms = denom.length;  //get length of new adjusted array of denoms (w/ZERO)
	   
	    if(denom[1] > T) {  //check for min acceptable units
	    	System.out.println("Cannot make change as smallest denom is bigger than T # of Coins\n");	
	    	System.exit(0);  //exit program if user gives silly input
	    }
	    	    
	    int limit = (denom[ddenoms-1]*T);  //set loop limit to highest possible multiple of T for loop later on
	    int[][] table = new int[ddenoms][limit+1]; //init 2D array table to store results

	    for(int i = 0 ; i<ddenoms ; i++) table[i][0]=0;  //populate first col of 2D array with ZEROES (since 0 coins for 0 units)
      
	    	for(int i = 1 ; i<ddenoms ; i++){  //outer loop by # of denoms
	    		for(int j = 1 ; j<=limit ; j++){  //inner loop by some # units up to limit n*T while min coins <= T
	    			if(i == 1 && j % denom[i] != 0) break; //no change if units have remainder from min denom so end loop as GAP here 
	    			else if(i==1) table[i][j] = 1 + table[1][j-denom[1]];  //use min denom coins for min units
	    			else if(j<denom[i]) table[i][j] = table[i-1][j];  //carry over #coins from previous denom i-1 if units < i
	    			else table[i][j] = Math.min(table[i-1][j],1+table[i][j-denom[i]]);  //take MIN num of coins as optimal choice
	    			if(table[ddenoms-1][j] > T) break;  //break out of loop if #coins bigger than T limit
	    			if(table[i][j] <=T && table[i][j] != 0) counter=j; //increment counter if NO GAP and <=T
	    			}
	    		}    	  
	    return counter;	    
	}
	
	/**
	 * Call to MAIN for testing of coin function
	 * @param args
	 */
	public static void main(String[] args) {
		int[] D = {1,5,7,13};  //init Denoms 
		int[] D1 = {1,5,7,13,26,54,104,208};  //init another denom array for time-complexity testing
		int[] D2 = {1,5,7,13,26,54,104,208,416,832,1664,3328};  //init another denom array for time-complexity testing
		int T = 500; //init T coin limit
		
		long startTime = System.nanoTime(); //start timer
		int ans = findMaxCoinsInt(D, T);  //call function - can change D here to D1 or D2 to test time-complexity
		long stopTime = System.nanoTime();  //end timer
        long byteTime =(int)((stopTime - startTime)/ 1000);  //convert from nanoseconds to microseconds
		System.out.println("\n\nMax Coverage is "+ans+" for limit T of "+T+" in "+byteTime+" ms");  //see answer which is 6476 
	}
}
