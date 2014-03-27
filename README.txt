Code Written by Brennan McTernan

Description:
Homework for Concurrent Programming
This program creates a hash table consisting of buckets which hold strings.

HashTest:
Reads in commands and strings from the in.txt file. 
put - Adds a string to the table
putifabsent - Adds a string to the table if the string doesn't exist in the table
remove - Removes a string from the table
removeifpresent - Removes a string from the table if it is present
ispresent - Returns true if the string is present in the table, false otherwise
display - prints out the table

HashTime:
Adds a given number of random strings to the table and displays the time taken to do so afterwards.
Used to examine the time difference between a table implemented witha  read-write lock and a normal lock


Requirements:
None

Notes:
StringHash.java - contains the hash table class
Bucket.java - contains the bucket class 