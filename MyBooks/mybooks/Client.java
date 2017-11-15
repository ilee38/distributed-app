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
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Setting the host to null indicates that the local host address should be used
		String host = null;
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			Store stub = (Store) registry.lookup("Store");
			
			// *** Search *** //
			ArrayList<Book> searchResp = stub.search("Graduate School");
			if( searchResp != null) {
				for(Book book : searchResp) {
					System.out.println("Book title: " + book.bookTitle);
					System.out.println("Item number: " + book.itemNumber);
					System.out.println("---------------");
				}
			}else {
				System.out.println("No results found under topic: ");
			}
			
			// *** Lookup *** //
			Book lookupBook = stub.lookup(2);
			if(lookupBook != null) {
				System.out.println("Qty in stock: " + lookupBook.stockQty);
				System.out.println("Item cost: $" + lookupBook.bookCost);
				System.out.println("Book topic: " + lookupBook.bookTopic);
			}else {
				System.out.println("Item number not found");
			}
			
			// ** Order ** //
			String orderMsg = stub.order(2);
			System.out.println(orderMsg);
			
		} catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}

	}

}
