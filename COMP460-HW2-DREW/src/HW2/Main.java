/**
 * @author Drew
 * All this MAIN method does is run BigIntegers thru fast/slow Fibonacci algorithms (via
 * calls to BigRandInteger class) for timing and comparison purposes - there are no GUI graphs here!
 * 
 */

package HW2;

import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.math.BigInteger; 
import java.util.Random;

import HW2.BigRandInteger;
//import HW2.Graph;




public class Main
{ 
 
	public static void main(String[] args) 
	{ 	
		
		int[] digits = {10,100,500,1000,1500,2000,2500,3000,3500,4000,4500,5000,5500,6000,6500,7000,7500,8000,8500,9000,9500,10000,15000,20000,25000,50000};  //num of digits array for loop
		int lens = digits.length;
		
		
		
		for(int i=0; i<lens; i++)
		{
		BigInteger fibSlow = new BigInteger(Integer.toString(digits[i]*2));
		BigInteger fibFast = new BigInteger(Integer.toString(digits[i]*2));	
		
		fibSlow = BigRandInteger.fiboIto(digits[i]);	
		fibFast = BigRandInteger.fiboFastDoublr(digits[i]);
		
		}
	
		

	} 		
	
}

