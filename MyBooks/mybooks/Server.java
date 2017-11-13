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
	
	public Server() {}
	
	
	public String search(String topic) {
		return "you searched for topic: " + topic;
	}
	
	
	public String lookup(int itemNumber) {
		return "test value";
	}
	
	
	public String order(int itemNumber) {
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
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Server obj = new Server();
			Store stub = (Store) UnicastRemoteObject.exportObject(obj, 0);
			
			//Bind remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("Store", stub);
			
			System.err.println("Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}

	}

}
