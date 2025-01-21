import java.util.*;
import java.text.SimpleDateFormat;

class Biker implements Runnable
{
	private String name;
	private String startTime;
	private String endTime;
	private static int currentRank = 1;
	private double speed;
	private static volatile boolean raceStarted = false;
	private static BikerResult[] results = new BikerResult[10];
	private int index;
	private static final Object raceLock = new Object();
	private static final int RACE_DISTANCE = 100;
	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	public Biker(String name, int index)
	{
		this.name = name;
		this.index = index;
		this.speed = 30+Math.random() * 3;
	}
	
	public void run()
	{
		synchronized (raceLock)
		{
			try
			{
				while(!raceStarted)
				{
					raceLock.wait();
				}
			}
			catch(InterruptedException e)
			{
				System.out.println(e);
			}
		}

		try
		{
			this.startTime = TIME_FORMAT.format(new Date());
			System.out.println(name + " has started !");
			double raceTime = RACE_DISTANCE/speed;
			Thread.sleep((long)(raceTime*1000));
			this.endTime = TIME_FORMAT.format(new Date());
			int bikerRank = getNextRank();
			results[index] = new BikerResult(name, startTime, endTime, bikerRank, raceTime);
			System.out.println(name+" has finished with rank "+bikerRank);
		}
		catch(InterruptedException e)
		{
			System.out.println(e);
		}
	}
	private synchronized static int getNextRank()
	{
		return currentRank++;
	}
	public static void startRace()
	{
		synchronized (raceLock)
		{
			raceStarted = true;
			raceLock.notifyAll();
		}
	}
	public static BikerResult[] getResults()
	{
		return results;
	}
}

class BikerResult
{
	private String name;
	private String startTime;
	private String endTime;
	int rank;
	private double timeTaken;
	public BikerResult(String name, String startTime, String endTime, int rank, double timeTaken)
	{
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.rank = rank;
		this.timeTaken = timeTaken;
	}
	public String toString()
	{
		return ("Rank "+rank+": "+name+"    Start Time: "+startTime+"    End Time: "+endTime+"    Time Taken: "+timeTaken+" s");
	}
}

public class BikerGame
{
	private static BikerResult[] results = new BikerResult[10];public static void main(String args[]) 
	{
		Thread[] bikerThreads = new Thread[10];
		String[] bikerNames = {"Biker 1", "Biker 2", "Biker 3", "Biker 4", "Biker 5", "Biker 6", "Biker 7", "Biker 8", "Biker 9", "Biker 10"};
		
		for(int i=0;i<10;i++)
		{
			Biker b = new Biker(bikerNames[i], i);
			bikerThreads[i] = new Thread(b,bikerNames[i]);
			bikerThreads[i].start();
		}

		System.out.println("Countdown starts!");
		for(int i=10;i>0;i--)
		{
			System.out.println(i);
			try
			{
				Thread.sleep(500);
			}
			catch(InterruptedException e)
			{
				System.out.println(e);
			}
			
		}
		System.out.println("GO!");
	
		Biker.startRace();
		
		for(Thread thread : bikerThreads)
		{
			try
			{
				thread.join();
			}
			catch(InterruptedException e)
			{
				System.out.println(e);
			}
		}
		System.out.println("\nRace Results:");
		BikerResult[] results = Biker.getResults();
		Arrays.sort(results, Comparator.comparingInt(r -> r.rank));
		for(BikerResult result : results)
		{
			System.out.println(result);
		}
	}
}
		
		