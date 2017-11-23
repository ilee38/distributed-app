<h3>Instructions to run the programs</h3>

<h4>In general, the procedure should be:</h4>

 1. Compile and run the Server
 2. Start the rmi registry (on Unix systems use command: rmiregistry &)
 3. Compile and run the Client


<h4>Detailed instructions:</h4>

<h5>On the Server side:</h5>

  1. Compile the source files
     $ javac Server.java Store.java Book.java

  2. Start the Java RMI registry (on UNIX system):
     $ rmiregistry &

  3. Start the Server:
     $ java Server

<h5>On the Client side:</h5>

  4. Compile source files (including the interface file)
     $ javac Client.java Store.java Book.java

  5. Start the client
     $ java Client


<h4>Test Cases</h4>

Two test cases were created to test the program's functionality, ClientTest1.java and ClientTest2B.java. 

The first test case verifies the program's basic functionality using a single Client. It checks that it can call all the remote methods in the Server, and that correct book keeping is maintained by the Server, i.e. that stock quantities are updated and service reports are correct. The test results show correct functionality. 
	
The second test case simulates multiple Client requests by creating 2 threads and having them both order the same book. This process wis repeated 30 times in a loop. ClientTest2B.java makes use of class file ClientTest2A.java, which is the Client program, but it implements Java's Runnable interface. This means that the Client can be assigned to as many threads as we choose, and then executed. ClientTest2B.java is the driver that creates and executes the threads. Since the threads are created on the Client's side, they are executed in random order, however running the test shows that there is no deadlock on the Server side.

Note: to run test cases follow the same procedure as above, but use the Client files under the test folder. Once all files are compiled, two shell scripts are provided to run the tests: test1.sh and test2.sh
