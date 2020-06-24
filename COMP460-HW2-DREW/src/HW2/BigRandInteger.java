/**
 * @author Drew
 * Tutorial links on Big Integer Library in JAVA: http://archive.atomicmpc.com.au/forums.asp?s=2&c=10&t=3022 & 
 * http://stackoverflow.com/questions/2290057/how-to-generate-a-random-biginteger-value-in-java
 * http://stackoverflow.com/questions/8965006/java-recursive-fibonacci-sequence
 * http://nayuki.eigenstate.org/res/fast-fibonacci-algorithms/fastfibonacci.java
 * This class simply contains function that generates a random BigInteger for a given byte size/interval
 * and also generates a Fibonacci number for BigIntegers via slow iterative way as well as fast doubling...
 */

package HW2;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.math.BigInteger;


public class BigRandInteger
{ 
	
	/**
	 * Generates random BigInteger given size/digits
	 * @param digits size which is implicit in BigInteger input/value
	 * @return BigInteger number randomly selected with given byte size/interval
	 */
	public static BigInteger getRandomBigInteger (BigInteger digits) {
	    String sNumber = "";
	    
	    //generate BigInteger randomly according to given byte size/digits - uses built-in BigInteger library in Java
	    for (BigInteger index = BigInteger.ZERO; index.compareTo(digits) < 0; index = index.add(BigInteger.ONE) ) {
	        char c = Double.toString(Math.random()*10).charAt(0);
	        sNumber += c;
	    }
	    return new BigInteger(sNumber);
	}
	
	/**
	 * Slower iterative fibo method - loop thru to get F(n-1) + F(n-2)
	 * @param digits size which is implicit in BigInteger input/value
	 * @return BigInteger number which represents Fibonacci number F(100000)
	 */
	 public static BigInteger fiboIto (int digits) {
	        int n=digits;
	        int n2=n*2;
	        int lens = 0; int byteTime = 0;
	        long startTime; //declare timer start so can time operations
	    	long stopTime; //declare timer end so can time operations
	        
	        BigInteger sum = new BigInteger(Integer.toString(n2)); //initialize in case we need to keep track of sum
	        
	        BigInteger[] vector = new BigInteger[digits];
	        vector[0]=BigInteger.ZERO;
	        vector[1]=BigInteger.ONE;
	        
	        try
	        {
	        	
	        // calculating
	        startTime = System.nanoTime();
	        	
	        for(int i = 2 ; i<digits ; i++){
	            vector[i]=vector[i-1].add(vector[i-2]);
	            //System.out.println(String.format("%,8d%n%n", vec[i]));
	            //sum = sum.add(vec[i]);        	                     
	        }
	        
	        stopTime = System.nanoTime();
	        byteTime =(int)((stopTime - startTime)/ 1000);  //convert from nanoseconds to microseconds
	        
	        }
	        catch(Error e) 
	    	{
	    		  System.out.println("Number is too large for PC and exceeds bounds of heap memory - try a number with less than 23000 digits!\n" + e);		  
	    	}
	        
	        //print to terminal        
//	        for(int i = vec.length-1 ; i>=0 ; i--){
//	            System.out.println(String.format("%,8d%n%n", vec[i]));
//	            System.out.println("");}
	        
	        lens = vector.length-1;
	        System.out.println("\nFiboSlow is " + vector[lens]);
	        System.out.println("FiboSlow Time in microseconds is " + byteTime);
	        return vector[lens];
	        
	    }
	 
	 
	 
	 /**
	  * Faster doubling method - can be proven by induction via matrix base case
	  * F(2n) = F(n) * (2*F(n+1) - F(n)) ->> F(2n+1) = F(n+1)^2 + F(n)^2
	  * @param n digits for BigInteger number
	  * @return BigInteger number which represents Fibonacci number F(100000)
	  */
		public static BigInteger fiboFastDoublr(int digits) {
			BigInteger a = BigInteger.ZERO;
			BigInteger b = BigInteger.ONE;
			int byteTime = 0;
	        long startTime; //declare timer start so can time operations
	    	long stopTime; //declare timer end so can time operations
			
			int m = 0;
			
			
			startTime = System.nanoTime();
			
			try
			{
			//calculating
			for (int i = 31 - Integer.numberOfLeadingZeros(digits); i >= 0; i--) {
				
				
				// Double value
				BigInteger d = a.multiply(b.shiftLeft(1).subtract(a));
				BigInteger e = a.multiply(a).add(b.multiply(b));		
				
				a = d;
				b = e;
				m *= 2;
				
				
				// Move forward by one 
				if (((digits >>> i) & 1) != 0) {
					BigInteger c = a.add(b);
					a = b;
					b = c;
					m++;
					
				}
			}
			}
			catch(Error e) 
		    	{
		    		  System.out.println("Number is too large for PC and exceeds bounds of heap memory - try a number with less than 23000 digits!\n" + e);		  
		    	}
			
			stopTime = System.nanoTime();
			byteTime =(int)((stopTime - startTime)/ 1000);  //convert from nanoseconds to microseconds
				
			System.out.println("\nFiboFast is " + b.subtract(a)); //move back by one
			System.out.println("FiboFast Time in microseconds is " + byteTime);
			return a;
			
		}


	
}

