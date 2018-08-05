import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class RandomData 
{
	public static int x;
	public static int y;
	
	public static void main(String[] args) throws FileNotFoundException
	{
		//generate("set1", 50, 50, 0);
		//generate("set2", 1000, 100000, 500);
		//generate("set3", 1000000, 10000000, 500000);
		//generate("set4", 500, 100000, 50000);
		generate("set5", 500000, 1000000, 500000);
		
	}
	
	public static void generate(String name ,int size, int bound, int offset) throws FileNotFoundException
	{
		PrintWriter pw = new PrintWriter(new File("src/Dataset/" + name + ".txt"));
		Scanner sr = null;
		Random randGen = new Random();
		System.out.println("Generating dataset with size " + size + " and upper bound of " + bound +".");
		
		pw.println(size);
		for(int i=0; i<=size ; i++)
		{
			x = randGen.nextInt(bound)+1-offset;
			y = randGen.nextInt(bound)+1-offset;
			pw.print(x);
			pw.print(" ");
			pw.print(y);
			pw.println("");
		}
		pw.flush();
		pw.close();
		System.out.println("Done generation.");
	}
}
