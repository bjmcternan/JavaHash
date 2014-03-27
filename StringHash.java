import java.util.ArrayList;

public class StringHash 
{
 private ArrayList<Bucket> bucketList;
 private int numBuckets;
 private int sleepTime;
 private boolean readFlag;
 
 StringHash(int size, int time, boolean useRW)
 {
  readFlag = useRW;
  sleepTime = time;
  numBuckets = size;
  bucketList = new ArrayList<Bucket>(numBuckets);
  
  for(int i = 0; i < numBuckets; i++)
  {
   bucketList.add(i, new Bucket(readFlag));
  }
 }
 
 void display()
 {
  try
  {
   Thread.sleep(1000 * sleepTime);
  } 
  catch(InterruptedException ex) 
  {
   Thread.currentThread().interrupt();
  }
  
  for(int i = 0; i < numBuckets - 1; i++)
  {
   System.out.print("Bucket #"+ i + " ");
   bucketList.get(i).display();
  }
 }
 
 void put(String value)
 {
  int bucket = hashFunction(value,numBuckets);
  bucketList.get(bucket).putString(value);
  
  bucketList.get(bucket).getLock();
  
  try
  {
   Thread.sleep(1000 * sleepTime);
  } 
  catch(InterruptedException ex) 
  {
   Thread.currentThread().interrupt();
  }
  
  bucketList.get(bucket).putString(value);
  System.out.println(bucket + " has added " + value);
  bucketList.get(bucket).unlock();
 }
 
 void putIfAbsent(String value)
 {
  int bucket = hashFunction(value,numBuckets);
  
  bucketList.get(bucket).getLock();

  try
  {
   Thread.sleep(1000 * sleepTime);
  } 
  catch(InterruptedException ex) 
  {
   Thread.currentThread().interrupt();
  }
  
  if((bucketList.get(bucket).getString(value)) == "false")
  {
    bucketList.get(bucket).putString(value);
    System.out.println(bucket + " " + value + " does not exist. Added.");
  }
  else
  {
    System.out.println(bucket + " " + value + " exists");
  }
  bucketList.get(bucket).unlock();  
 }
 
 boolean isPresent(String value)
 {
  int bucket = hashFunction(value,numBuckets);
  boolean rValue = true;
  if(readFlag)
   bucketList.get(bucket).getReadLock();
  else
   bucketList.get(bucket).getLock();
  
  try
  {
   Thread.sleep(1000 * sleepTime);
  } 
  catch(InterruptedException ex) 
  {
   Thread.currentThread().interrupt();
  }
  
  
  if((bucketList.get(bucket).getString(value)) == "false")
  {
    System.out.println(bucket + " " + value + " does not exist.");
    rValue = false;
  }
  else
  {
    System.out.println(bucket + " " + value + " exists.");
  }
   
  if(readFlag)
   bucketList.get(bucket).unlockRead();
  else
   bucketList.get(bucket).unlock();
  
  return rValue;
 }
 
 void removeIfPresent(String value)
 {
  int bucket = hashFunction(value,numBuckets);
  
  bucketList.get(bucket).getLock();
  try
  {
   Thread.sleep(1000 * sleepTime);
  } 
  catch(InterruptedException ex) 
  {
   Thread.currentThread().interrupt();
  }
  
  if(bucketList.get(bucket).remove(value))
  {
    System.out.println(bucket + " " + value + " does exists. Removed.");
  }
  else
  {
    System.out.println(bucket + " " + value + " does not exist.");
  }
  bucketList.get(bucket).unlock();
 }
 
 private static int hashFunction(String str, int modulus) {
        int sum = 0;
        for (int i=0; i<str.length(); i++)
            sum = sum + Character.getNumericValue(str.charAt(i));
        return sum % modulus;
    }
}
