package parallelism;
import java.lang.*;
import java.util.Arrays;
import java.io.*;
import java.util.Random;

/**
 * @author Tony Drew for COMP 460 HW7 2014
 * This Runnable class contains both sequential and parallel version of Floyds
 */
public class FloydsP implements Runnable
{
		private static int rowStop;
		private static int rowStart;
		private static int[][] A;
		private static int[][] Out;
	
		/**
		 * Constructor for FloydsP class
		 * @param A as adjacency matrix for input in shared memory
		 * @param Out as matrix for output in shared memory
		 * @param rowStart as dynamically changing row number in shared memory
		 * @param rowStop as dynamically changing row number in shared memory
		 */
		public FloydsP(int[][] A, int[][] Out, int rowStart, int rowStop){ 
			FloydsP.A = A;
			FloydsP.Out = Out;
			FloydsP.rowStop = rowStop;
			FloydsP.rowStart = rowStart;
		}
    
		/**
		 * Getter function to see piece-wise output on each local thread
		 * @return Out matrix for each local thread
		 */
		public static int[][] getOut() { 
			System.out.println("\nParallel Output for local thread is\n ");
			 for(int i = 0 ; i<Out.length ; i++){  
  	    		for(int j = 0 ; j<Out.length ; j++){ 			
  	    			System.out.println(Out[i][j]);
 	    		}
         	}  
			return FloydsP.Out;
		}

		/**
		 * Setter function for matrix output - synchronized in case threads try to access Out matrix at same time
		 * @param Out matrix for each local thread
		 */
		public synchronized void setOut(int[][] Out) { 
			FloydsP.Out = Out;
		}
		
		public void run(){
			setOut(ParaFloyd(rowStart,rowStop,A));	//use setter to be able to get back results from void function
		}		
		
		/**
		 * Parallel Floyds - big change is on inner for loop "i" which now takes row numbers instead of total vertices
		 * @param rowStart 
		 * @param rowStop
		 * @param A as adjacency matrix for input
		 * @return Out matrix
		 */
		public static int[][] ParaFloyd(int rowStart, int rowStop, int[][] A) 
	    	{      
	    	    int[][] D = new int[A.length][A.length]; //all threads using different section of rows so no overlapping on AdjMatrix	  
	    	    
	    	    for(int k = 0 ; k < A.length ; k++){ 
	    	    	for(int i = rowStart ; i < rowStop ; i++){ 
	    	    		for(int j = 0 ; j < A.length ; j++){ 
	    	    			D[i][j] = Math.min(A[i][j], A[i][k]+A[k][j]);
	    	    		}
	    	    	}
	    	    }  
	    	    return D;	    
	    	} 
		
		/**
		 * Old sequential FLoyds - no parallelism here
		 * @param A as adjacency matrix for input
		 * @return Out matrix
		 */
        public static int[][] OldFloyd(int[][] A) 
    	{      
    	    //int[][] D = new int[A.length][A.length]; 
    	    int[][] D = Arrays.copyOf(A,A.length);	//use local copy of AdjMatrix so threads in parallel version don't cause overlap
    	    
    	    for(int k = 0 ; k<A.length ; k++){ 
    	    	for(int i = 0 ; i<A.length ; i++){ 
    	    		for(int j = 0 ; j<A.length ; j++){   			    			
    	    			D[i][j] = Math.min(D[i][j], D[i][k]+D[k][j]); 	    			
    	    		}
    	    	}
    	    }  
    	    return D;	    
    	}              
        
        /**
         * Creates adjacency matrix by randomly filling in numbers from 1 to 9999999 (infinity)- also
         * checks for sparse matrix elements in theory though not practically possible here and then replaces with infinity if empty -
         * assumes dense matrix here as 9999999 won't randomly pop up often (but still works for either dense or sparse overall)
         * @param size of matrix
         * @return Out matrix
         */
        public static int[][] CreateAdjacencyMat(int size) 
    	{     
        	int infinity = 9999999;  //if value = 9999999 or infinity then no edge exists
        	Integer sparse = null;  //theoretically, sparse matrices can have empty cells or NaNs
        	Random e = new Random(); 
        	int[][] A = new int[size][size];
        	
        	for(int i = 0 ; i<size ; i++){  
	    		for(int j = 0 ; j<size ; j++){ 
	    			A[i][j] = e.nextInt(infinity)+1; //fill matrix w/random nums from (1 to 9999999 infinity)
	    			if(i == j) A[i][j] = 0; //use zero on diagonals
	    			if(i > 0 && A[j][i] !=0 & i != j && A[j][i] !=infinity && j < i) A[i][j] = A[j][i]; //transpose non-zero diagonals
	    			if((Integer) A[i][j] == sparse) A[i][j] = infinity; //set value to infinity if cell empty in sparse matrix 	
	    			//if(A[i][j] == infinity) System.out.println("\nNo edge exists!"); 
	    		}
        	}   	      	
    	    return A;	    
    	}             
        
        /**
         * MAIN function use 3 for-next loops to iterate thru vertices, threads and num of rows (for each thread) then checks output at end
         * @param args
         */
        public static void main(String args[]) 
        {
        	//int[] numVertices = {10,50,100,200,300,400,500,600,700,800,900,1000,1500,2000,2500,3000,3500,4000,4500,5000}; 
        	int[] numVertices = {4}; //use above array for longer tests
        	int[] numThreads = {1,2,3,4,5,6,7,8}; 
    		int cores = 0;                
            int rowStart = 0;
            int error = 0; //if sequential output and parallel output do not match than increment error counter
            
            for (int vertices = 0; vertices < numVertices.length; vertices++) //outer loop by num vertices
        	{
            	System.out.println("\nStarting tests for all vertices of " + numVertices[vertices]);            	
        		int[][]AdjMat = CreateAdjacencyMat(numVertices[vertices]); //call func to create AdjMatrix for input  
        		System.out.println("\nCreating Initial AdjMatrix values for vertices of " + numVertices[vertices] + "\n");
   			 	for(int i = 0 ; i<AdjMat.length ; i++){  
     	    		for(int j = 0 ; j<AdjMat.length ; j++){ 			
     	    			System.out.println(AdjMat[i][j]);
    	    		}
            	} 
            	
            	for (int threads = 0; threads < numThreads.length; threads++)  //inner loop by num threads
                {
                	 cores = numThreads[threads];
            		 System.out.println("\nTest is vertices of " + numVertices[vertices] + " on threads of " + cores);
            		 int[][] MatOut = Arrays.copyOf(AdjMat,AdjMat.length); //create array for output same size as AdjMat input                       
                     if (cores > AdjMat.length) cores = AdjMat.length; //check if threads bigger than vertices and reduce if so
                     int p = (AdjMat.length/cores);  //get num of rows that each thread can handle for subdivision of AdjMatrix
             	     int pLast = p;
             	     if (AdjMat.length % cores != 0){ //check for odd number of rows and assign to last thread if odd num exists
             	    	pLast = p + AdjMat.length % cores;
             	    }
             	    
                     long t1=System.currentTimeMillis();                      
                      for (int i = 1; i <= cores; i++){  //inner-most loop by threads to get num of rows for each local thread
                     	rowStart = p * (i-1); //dynamically update rowStart for iteration thru AdjMatrix
                     	if (i==cores) p = pLast;
                     	
                         Thread thread =  new Thread(new FloydsP(AdjMat, MatOut, rowStart, rowStart+p)); //call thread for ParaFloyds
                         thread.start();
                         
                         if (i==cores)
         					try {
         						thread.join(); //wait for last thread to finish before comparing parallel matrix output below
         					} catch (InterruptedException e) {
         						e.printStackTrace();
         					}
                         System.out.println("\nMy name is " + thread.getName() + " on Core " + (i-1));
                         getOut(); //show piece-wise local output for each current thread
                       } 
                     
                     long t2=System.currentTimeMillis();                           
                     long t3=System.currentTimeMillis();    
                     int[][]DummyTest = OldFloyd(AdjMat); //run Floyds sequential to check parallel output
                     long t4=System.currentTimeMillis();                         
                    
                     System.out.println("\nAll PARAoutput\n ");  //print all final output for comparison
                     for(int i = 0 ; i<MatOut.length ; i++){  
         	    		for(int j = 0 ; j<MatOut.length ; j++){ 			
         	    			System.out.println(MatOut[i][j]);
         	    		}
                 	}              
                     System.out.println("\nAll SEQoutput\n ");  //print all final output for comparison
                     for(int i = 0 ; i<DummyTest.length ; i++){  
         	    		for(int j = 0 ; j<DummyTest.length ; j++){ 			
         	    			System.out.println(DummyTest[i][j]);
         	    		}
                 	}  
                     
                     //do final check to see if both parallel & sequential output matrices match
                     System.out.println("\nDoes Parallel Matrix Output match Sequential Matrix Output? "+Arrays.deepEquals(DummyTest,MatOut)); 
                     if(Arrays.deepEquals(DummyTest,MatOut) == false) error++; //increment error count
                     System.out.println("\nPARAtime ms= " + (t2-t1)*1);
                     System.out.println("SEQtime ms= " + (t4-t3)*1);
                     
                     }
            }
            System.out.println("\nTesting is finished with " + error + " errors!");
        }      
}

