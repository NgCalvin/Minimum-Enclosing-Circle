package MEC;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public final class Utility 
{
	
	//Just for reading in a line from the dataset and transform into a ArrayList of integer of the point.
	public static ArrayList string2doubleList(String line)
	{
		ArrayList<Double> result = new ArrayList();
		//Splitting the line into String[] so to extract the data
		String[] temp = line.split(" ");
		//Adding x coordinate of the next line in the document
		result.add(Double.parseDouble(temp[0]));
		//Adding y coordinate of the next line in the document
		result.add(Double.parseDouble(temp[1]));
		
		return result; 
	}

	//Calculating center x, y and radius r^2 of the circle passing the point
	public static ArrayList<Double> find_equation(ArrayList<ArrayList<Double>> circle)
	{
		ArrayList<Double> result = new ArrayList<Double>();
		if(circle.size()==2)
		{
			
			//Circle is passing two points only
			//Adding x , y coordinates of the center and radius in double
			result.add(((double) circle.get(0).get(0) + (double) circle.get(1).get(0))/2);
			result.add(((double) circle.get(0).get(1) + (double) circle.get(1).get(1))/2);
			result.add(Math.pow(((double) circle.get(0).get(0)) - result.get(0), 2) + Math.pow(((double) circle.get(0).get(1)) - result.get(1), 2));
		}
		else if(circle.size()==3)
		{
			//Circle is passing three points
			double x1 = (double) circle.get(0).get(0);
			double x2 = (double) circle.get(1).get(0);
			double x3 = (double) circle.get(2).get(0);
			double y1 = (double) circle.get(0).get(1);
			double y2 = (double) circle.get(1).get(1);
			double y3 = (double) circle.get(2).get(1);
			
			if(x1==x2)
			{
				double tempx = x2;
				double tempy = y2;
				x2 = x3;
				y2 = y3;
				x3 = tempx;
				y3 = tempy;
			}
			else if(x3==x2)
			{
				double tempx = x2;
				double tempy = y2;
				x2 = x1;
				y2 = y1;
				x1 = tempx;
				y1 = tempy;

			}
			
			double slope_a = (y1-y2)/(x1-x2);
			double slope_b = (y3-y2)/(x3-x2);
			
			//Adding the x coordinate of center
			result.add(((slope_a * slope_b) * (y1 - y3) + slope_b * (x1 + x2) - slope_a * (x2 + x3)) / (2 * (slope_b - slope_a)));
			
			//Adding the y coordinate of center
			result.add( ((y1 + y2) / 2) - (1 / slope_a) * (result.get(0) - ((x1 + x2) / 2)));
			
			//Adding radius of circle
			result.add(Math.pow(x1 - result.get(0), 2) + Math.pow(y1 - result.get(1), 2));
		}
		return result;
	}

	//Checking if a point fall inside or outside the circle, assuming TRUE 
	public static boolean check_position(ArrayList<Double> equation, ArrayList<Double> nextPoint)
	{
		boolean result = Boolean.TRUE;
		double center_x = (double) equation.get(0);
		double center_y = (double) equation.get(1);
		double radius = (double) equation.get(2);
		double next_x = (double) nextPoint.get(0);
		double next_y = (double) nextPoint.get(1);
		
		if(roundoff((Math.pow((next_x - center_x), 2) + Math.pow((next_y - center_y), 2)),2) > roundoff(radius,2))
		{
			result = Boolean.FALSE;
		}
		return result;
	}
	
	//Rounding off doubles to a specific places to easy check_position calculation
	public static double roundoff(double value, int dp)
	{
		if(dp < 0) throw new IllegalArgumentException();
		value = value * Math.pow(10.0, dp);
		double result = Math.round(value);
		result = result/ Math.pow(10.0, dp);
		return result;
	}
}
