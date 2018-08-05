package MEC;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import MEC.Utility;

// TODO: Auto-generated Javadoc
/**
 * The Class MEC.
 */
public class MEC 
{
	
	/** The sr. */
	private static Scanner sr = null;
	
	/** The points. */
	public static ArrayList<ArrayList<Double>> points = new ArrayList<ArrayList<Double>>();
	
	/** The circle. */
	public static ArrayList<ArrayList<Double>> circle = new ArrayList<ArrayList<Double>>();
	
	/** The next point. */
	public static ArrayList<Double> nextPoint = new ArrayList<Double>();
	
	/** The equation. */
	public static ArrayList<Double> equation = new ArrayList<Double>();
	
	/** The count. */
	public static int count = 3;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws FileNotFoundException the file not found exception
	 */
	public static void main(String[] args) throws FileNotFoundException
	{
		if(args[0].isEmpty())
		{
			System.err.println("Usage : java -cp . MEC/MEC [file inside DataSet]");
			System.exit(0);
		}
		
		long startTime = System.nanoTime();
		ArrayList<ArrayList<Double>> result = MEC("Dataset/" + args[0]);
		long endTime = System.nanoTime();
		long timeUsed = (endTime - startTime);
		
		
		equation = Utility.find_equation(result);		
		System.out.println("The following are points the MEC passes through:");
		System.out.println(result);
		System.out.println("The following is the x, y coordinate and r^2 of the MEC:");
		System.out.println(equation);
		System.out.println("Time used in nanosecond:");
		System.out.println(timeUsed);
		
		PrintWriter pw = new PrintWriter(new File("Dataset/" + args[0].substring(0, 4)+"result.txt"));
		pw.println("The following are points the MEC passes through:");
		pw.println(result);
		pw.println("The following is the x, y coordinate and r to power 2 of the MEC:");
		pw.println(equation.subList(0, 2));
		pw.println(Math.sqrt(equation.get(2)));
		pw.println("The following is the time used in nanosecond for this dataset:");
		pw.println(timeUsed);
		pw.flush();
		pw.close();

	}
	
	/**
	 * Mec.
	 *
	 * @param path the path
	 * @return the array list
	 */
	public static ArrayList<ArrayList<Double>> MEC(String path)
	{
		try 
		{
			sr = new Scanner(new File(path));
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("File " + path + " cannot be found.");
			circle.clear();
			return circle;
		}
		
		//File is found and start to do stuff
		System.out.println("This Dataset have " + sr.nextLine() + " data.");
		
		//Data is randomly permuted already during creating part
		//Adding p1 and p2 into the circle that is our smallest circle then calculate their radius and set of points.
		points.add(Utility.string2doubleList(sr.nextLine()));
		points.add(Utility.string2doubleList(sr.nextLine()));
		circle.add(points.get(0));
		circle.add(points.get(1));
		equation = Utility.find_equation(circle);
		
		while(sr.hasNextLine())
		{
			//System.out.println("Checking point " + count);
			
			nextPoint = Utility.string2doubleList(sr.nextLine());
			
			if(!Utility.check_position(equation, nextPoint))
			{
				//System.out.println("OnePt Called.");
				circle = onePointMEC(points, nextPoint);
				points.add(nextPoint);
				//count++;
			}
			else
			{
				//System.out.println("Point " + count + " inside circle!");
				points.add(nextPoint);
				//count++;
			}
		}
		
		System.out.println("Algorithm Complete.");
		sr.close();

		return circle;
		
	}

	/**
	 * One point MEC.
	 *
	 * @param points the points
	 * @param p1 the p 1
	 * @return the array list
	 */
	public static ArrayList<ArrayList<Double>> onePointMEC(ArrayList<ArrayList<Double>> points, ArrayList<Double> p1)
	{
		circle.clear();
		circle.add(p1);
		circle.add(points.get(0));
		Collections.shuffle(points);
		for(int i = 0; i < points.size(); i++)
		{
			equation = Utility.find_equation(circle);
			if(!Utility.check_position(equation, points.get(i)))
			{
				ArrayList<ArrayList<Double>> subList = new ArrayList<ArrayList<Double>>();
				subList.addAll(points.subList(0, i));
				circle = twoPointMEC(subList, p1, points.get(i));
			}
		}
		
		return circle;
	}
	
	/**
	 * Two point MEC.
	 *
	 * @param points the points
	 * @param p1 the p 1
	 * @param p2 the p 2
	 * @return the array list
	 */
	public static ArrayList<ArrayList<Double>> twoPointMEC(ArrayList<ArrayList<Double>> points, ArrayList<Double> p1, ArrayList<Double> p2)
	{
		circle.clear();
		circle.add(p1);
		circle.add(p2);
		ArrayList<ArrayList<Double>> temp = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < points.size(); i++)
		{
			equation = Utility.find_equation(circle);
			if(!Utility.check_position(equation, points.get(i)))
			{
				if(circle.size()==3)
				{
					circle.remove(2);
					circle.add(points.get(i));
				}else
				{
					circle.add(points.get(i));
				}
			}
		}
		
		//System.out.println(circle.size());
		return circle;
	}
}
