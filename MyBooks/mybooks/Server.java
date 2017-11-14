package mybooks;

/**
 * @author iramlee
 * 
 * Server class, implements the interface Store. 
 * Also, creates and exports the remote objects, and 
 * it registers the remote objects with a Java RMI registry
 */

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Store{
	
	//Counters
	private int searchCount = 0, lookupCount = 0, buyCount = 0;
	//Timers
	private int searchTime = 0, lookupTime = 0, buyTime = 0;
	
	public Server() {}
	
	
	public String search(String topic) {
		long beginTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		searchTime += (endTime - beginTime);
		return "you searched for topic: " + topic;
	}
	
	
	public String lookup(int itemNumber) {
		long beginTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		lookupTime += (endTime - beginTime);
		return "test value";
	}
	
	
	public String order(int itemNumber) {
		long beginTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		buyTime += (endTime - beginTime);
		return "test value";
	}
	
	
	public String reportRequestsNumber(String service) {
		return "test value";
	}
	
	
	public String reportGoodOrders() {
		return "test value";
	}
	
	
	public String reportFailedOrders() {
		return "test value";
	}
	
	
	public String reportServicePerformance(String service) {
		return "test value";
	}
	

/**
 * Sets up all the books and related information in the bookstore
 * 
 * */
	private void initBookStore() {
		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Server obj = new Server();
			Store stub = (Store) UnicastRemoteObject.exportObject(obj, 0);
			
			//Bind remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("Store", stub);
			
			//Initialize book "database"
			obj.initBookStore();
			
			System.err.println("Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}

	}

}
