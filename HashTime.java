//Written by Brennan McTernan
//CS511 Assignment 4
import java.io.*;
import java.util.concurrent.*;
import java.util.Random;


//Creates random strings and adds them to a hash table
final class threadTimeStart implements Runnable
{
	private String inString;
	private static final String ABC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Random rand = new Random();

	//Creates a random string
	private static String randomString(int len)
	{
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(ABC.charAt(rand.nextInt(ABC.length())));
		return sb.toString();
	}

	//Constructor
	public threadTimeStart()
	{
		inString = randomString(10);
	}

	//Start of each thread
	@Override
	public void run()
	{
		try
		{
			HashTime.execute(inString);
		}
		catch(InterruptedException ie)
		{
			System.out.println(Thread.currentThread().getName() + "Has encountered an error");
		}
	}
}


//Measures time taken to add strings to a hash table
public class HashTime
{
	private static ExecutorService service;
	private static StringHash stringHash;		//initialize hash table, stringHash

	public static void main(String[] args)
	{
		//Check if usage is correct
		if(args.length < 4)
		{
			System.out.println("usage: HashTest num_buckets sleep_time read_write name_of_file");
			System.exit(0);
		}

		//See usage above
		long startTime = System.nanoTime();
		int numBuckets = Integer.parseInt(args[0]);
		int sleepTime = Integer.parseInt(args[1]);
		boolean readWrite = Boolean.parseBoolean(args[2]);
		int numStrings = Integer.parseInt(args[3]);

		//Create hash table, stringHash, and the threadpool with 10 threads
		stringHash = new StringHash(numBuckets, sleepTime, readWrite);
		service = Executors.newFixedThreadPool(10);

		for(int i = 0; i < numStrings; i++)
		{
			service.submit(new threadTimeStart());
		}

		service.shutdown();

		//Waits for all strings to finish
		//Prints out time taken
		try
		{
			service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			System.out.println("Exiting Hash Time");
			System.out.println("Elapsed time: "+ (System.nanoTime() - startTime) + " nano seconds");
		}
		catch(InterruptedException is)
		{
			System.out.println("Await termination has fialed.");
		}
	}

	static void execute(String line) throws InterruptedException
	{
		stringHash.putIfAbsent(line);
	}
}
