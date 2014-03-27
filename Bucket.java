import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.*;

//Bucket manages a linked list of words
//Uses a Reentrant read-write lock in order to control concurrency
public class Bucket
{
	private LinkedList<String> list;
	private ReentrantReadWriteLock rwLock;

	Bucket(boolean rw)
	{
		//create lock for each bucket
		rwLock = new ReentrantReadWriteLock();

		//create a linkedlist of strings
		list = new LinkedList<String>();
	}

	void putString(String target)
	{
		//puts string in bucket
		list.add(target);
	}

	String getString(String target)
	{
		String out = "false";
		//returns String if present
		if(list.contains(target))
		{
			out = target;
			return out;
		}
		return out;
	}

	boolean remove(String target)
	{
		return list.remove(target);
	}

	void display()
	{
		for(Iterator<String> i = list.iterator(); i.hasNext();)
		{
			String item = i.next();
			System.out.print(item + " ");
		}
		System.out.println();
	}

	void getLock()
	{
		//gets lock
		rwLock.writeLock().lock();
	}

	void getReadLock()
	{
		//gets lock
		rwLock.readLock().lock();
	}

	void unlock()
	{
		//unlocks lock
		rwLock.writeLock().unlock();
	}

	void unlockRead()
	{
		//unlocks lock
		rwLock.readLock().unlock();
	}
}
