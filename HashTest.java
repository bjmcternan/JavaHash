//Written by Brennan McTernan
//CS511 Assignment 4
import java.io.*;
import java.util.concurrent.*;

//Starting point of each thread
final class threadStart implements Runnable
{
	private String inLine;

	public threadStart(String line)
	{
		inLine = line;
	}

	@Override
	public void run()
	{
		try
		{
			HashTest.execute(inLine);
		}
		catch(InterruptedException ie)
		{
			System.out.println(Thread.currentThread().getName() + "Has encountered an error");
		}
	}
}

//HashTest reads in commands from in.txt and adds, removes, or prints out strings to or from the bucket structure
public class HashTest
{
	private static ExecutorService service;
	private static StringHash stringHash;			//initializes hash table - stringHash

	public static void main(String[] args)
	{
		// Check if usage is correct
		if(args.length < 4)
		{
			System.out.println("usage: HashTest num_buckets sleep_time read_write name_of_file");
			System.exit(0);
		}

		//See usage above
		int numBuckets = Integer.parseInt(args[0]);
		int sleepTime = Integer.parseInt(args[1]);
		boolean readWrite = Boolean.parseBoolean(args[2]);
		String fileName = args[3];
		String line;
		String [] tokens;

		//Create hash table, stringHash, and the threadpool with 10 threads
		stringHash = new StringHash(numBuckets, sleepTime, readWrite);
		service = Executors.newFixedThreadPool(10);

		//Read data in from in.txt file
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			while ((line = reader.readLine()) != null)
			{
				service.submit(new threadStart(line));		//Submit command to service
			}
			reader.close();
			service.shutdown();
		}
		catch (FileNotFoundException ex)
		{
			System.exit(1);
			//TODO complain to user
		}
		catch (IOException ex)
		{
			System.exit(1);
			//TODO notify user
		}

		//Wait for all threads to terminate
		try
		{
			service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			System.out.println("Exiting Hash Test");
		}
		catch(InterruptedException is)
		{
			System.out.println("Await termination has fialed.");
		}
	}


	static void execute(String line) throws InterruptedException
	{
		String[] tokens;
		tokens = line.split(" ");
		// process line
		switch(tokens[0])
		{
			case "put":					//Add String into hash table
				stringHash.put(tokens[1]);
				break;
			case "isPresent":			//Checks if string is present
				stringHash.isPresent(tokens[1]);
				break;
			case "removeIfPresent":		//Removes string if present
				stringHash.removeIfPresent(tokens[1]);
				break;
			case "putIfAbsent":			//Adds string if not present
     			stringHash.putIfAbsent(tokens[1]);
     			break;
    		case "display":				//Prints hash table
     			stringHash.display();
     			break;
    	}
  	}
}
