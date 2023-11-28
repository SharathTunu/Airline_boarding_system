**Implementation Approach**

- Create a passenger class to store each passenger's data as a class object - Done

- The csv file have the priority column and the objects are added to the queue based on that priority - Done

- Read the list of passengers from the csv file and store them in a queue and sort them by priority - Done

- Start the boarding process with the passenger on top of the queue. Pop the top of list and wait for 20 secs. - Done

- If there are no new inputs during the wait time board the the next passenger thats top of the queue. - Done

- Add new passengers via different thread.

- If there is a new passenger during wait time add the new object to queue and sort it to fit the new arrival based on priority

- The loop exits when the passenger count hits 15 or 10 minutes has elapsed since the start of the code execution.

- The on boarded passengers are added to the hashtable from java collections

- The above table can be searched/filtered.
