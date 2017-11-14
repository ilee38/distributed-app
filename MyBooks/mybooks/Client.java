package mybooks;

/**
 * @author iramlee
 * 
 * Client class, obtains the stubs (proxy) on the server in order to make
 * method calls to the remote objects.
 */

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Client {
	
	private Client() {}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Setting the host to null indicates that the local host address should be used
		String host = null;
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			Store stub = (Store) registry.lookup("Store");
			String searchResp = stub.search("Grad school");
			System.out.println("response from server: " + searchResp);
		} catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}

	}

}
