package mybooks;

/**
 * @author iramlee
 * 
 * Client class, obtains the stubs (proxy) on the server in order to make
 * method calls to the remote objects.
 */

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class Client {
	
	private Client() {}


/**
 * search() method
 * ---------------
 * Returns all books under the specified topic
 * */
	public void search(String topic, Store stub) {
		try {
			ArrayList<Book> searchResp = stub.search(topic);
			if( searchResp != null) {
				for(Book book : searchResp) {
					System.out.println("Book title: " + book.bookTitle);
					System.out.println("Item number: " + book.itemNumber);
					System.out.println("---------------");
				}
			}else {
				System.out.println("No results found under topic: " + topic);
			}
		}catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	
/**
 * lookup() method
 * ---------------
 * Returns information about the requested item number
 * */
	public void lookup(int itemNum, Store stub) {
		try {
			Book lookupBook = stub.lookup(itemNum);
			if(lookupBook != null) {
				System.out.println("Qty in stock: " + lookupBook.stockQty);
				System.out.println("Item cost: $" + lookupBook.bookCost);
				System.out.println("Book topic: " + lookupBook.bookTopic);
			}else {
				System.out.println("Item number not found");
			}
		}catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	
/**
 * order() method
 * --------------
 * Return success / failed ordering result
 * */
	public void order(int itemNum, Store stub) {
		try {
			String orderMsg = stub.order(itemNum);
			System.out.println(orderMsg);
		}catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Setting the host to null indicates that the local host address should be used
		String host = null;
		Client clientApp = new Client();
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			Store stub = (Store) registry.lookup("Store");
			
			clientApp.search("Graduate School", stub);
			clientApp.lookup(2, stub);
			clientApp.order(2, stub);
		} catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}

	}

}
