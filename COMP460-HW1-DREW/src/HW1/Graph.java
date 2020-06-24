/**
 * 
 * @author Drew
 * Tutorial links on Graphs in JAVA: http://www.daniweb.com/software-development/java/threads/214451/how-to-plot-coordinates-in-java 
 * This class is where most of the action occurs - it will create a 2D chart and then call BigInteger function
 * to get test values for graph
 */

package HW1;

import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.math.BigInteger;
import java.util.Random;

import HW1.BigRandInteger;

public class Graph extends Frame 
{
  public Graph()
  {
	  
	  try 
	  {
	  setSize(630,630); //width x length in pixels  -should fit on most screens
	  } 
	  catch(Error e) 
	  {
		  System.out.println("Please adjust resolution in Graph class as it does not fit your screen and then try again!\n" + e);
	  }
  
	  //add listener so that user can click in chart graph in order to close/stop program
	  addWindowListener(new WindowAdapter() 
	  	{ public void windowClosing(WindowEvent event)
	  	{ System.exit(0); }}); }

  

  public void paint(Graphics g)
  {
  
	//turn on anti-aliasing for rendering to avoid strange GUI flickering on screen or during click event
	Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);

	int sumTime = 0; //declare and initialize sum variable to keep track of total time for all calcs
	long startTime; //declare timer start so can time operations
	long stopTime; //declare timer end so can time operations
	BigInteger a,b;  //declare BigInteger variables
	
	String[] byteSize = {"0","100000","150000","200000","250000","300000","350000","400000","450000","500000"};  //#bytes array for loop to create and time BigIntegers
	int[] byteTime = {0,0,0,0,0,0,0,0,0,0};  //array placeholder for timer of BigInteger calculations
	
	//prepare x-axis of graph - convert bytes into kilobytes - divide by 1000 (to get kilobytes) in order to fit x-axis on graph (600 pixels)
	int[] xd = {0,0,0,0,0,0,0,0,0,0}; 
	
	//prepare y-axis of graph - must take inverse (600 pixels - y value in seconds) since 0,0 coordinate starts in top-left corner - change first value to desired pixel size
	int[] yd = {600,0,0,0,0,0,0,0,0,0}; 
	
	//get default length of arrays in order to stop for-next loop below
	int lens = byteSize.length;
  
	//warn user calc chart is starting - could take up to 10-to-20 minutes depending on PC - do not interrupt or click/reset graph during calcs (or could re-calc)
	g.drawString("Calculating...", 50, 50);
	
	//start main for-next loop to do BigInteger calcs and fill data for chart graph
	try
	{
	  for(int i=1; i<lens; i++)
	  {
		//start time
		startTime = System.nanoTime();
		
		//generate 2 BigIntegers and then multiply - this function is called from BigRandInteger class - see class diagram in doc folder for more detail
		a =  new BigInteger(byteSize[i]); 
		a = BigRandInteger.getRandomBigInteger(a);
		b =  new BigInteger(byteSize[i]); 
		b = BigRandInteger.getRandomBigInteger(b);
		b = b.multiply(a);
		
		//stop time
		stopTime = System.nanoTime();
		
		//store time
		byteTime[i]=(int)((stopTime - startTime)/ 1000000000);  //convert from nanoseconds to seconds
		sumTime = sumTime + byteTime[i]; //keep track of total time for all calcs
		
		//create and store x-axis for graph as we move thru loop
		xd[i] = Integer.parseInt(byteSize[i])/1000;
		g.drawString(Integer.toString(xd[i]), xd[i], 600);  //draw x-axis labels...in kilobytes
		
		//create and store y-axis for graph as we move thru loop
		yd[i] = (int) Math.round(600-(byteTime[i]));
		g.drawString(Integer.toString(byteTime[i]), 10, yd[i]);  //draw y-axis labels...in seconds and then take inverse: (600 pixels - y value in seconds)
					
	  }
	}
	catch(Error e) 
	{
		  System.out.println("If you changed default values or pixel size, update arrays and then try again!\n" + e);		  
	}
	

	//draw x-axis label
	g.drawString("kilobytes", 550, 600);
	
	//draw y-axis label
	g.drawString("sec", 10, 400);
	
	//alert user calcs are finished
	g.drawString("Finished", 120, 50);
	g.drawString("Total sec", 180, 50);
	g.drawString(Integer.toString(sumTime), 240, 50);
	
	//draw graph function - quadratic
	g.drawPolyline(xd, yd, lens);
	
	//clear graph and free resources before garbage collection does this later on
	g.dispose();
	
	
 }
}

