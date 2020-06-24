/**
 * @author Drew
 * Tutorial links on Big Integer Library in JAVA: http://archive.atomicmpc.com.au/forums.asp?s=2&c=10&t=3022 & 
 * http://stackoverflow.com/questions/2290057/how-to-generate-a-random-biginteger-value-in-java
 * This class simply contains function that generates a random BigInteger for a given byte size/interval
 */

package HW1;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.math.BigInteger;


public class BigRandInteger
{ 
 
	
	
	/**
	 * 
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
	
	
	
}

