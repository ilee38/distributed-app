/**
 * 
 */
package mybooks;

/**
 * @author iramlee
 *
 */
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class ClientTest1{
	
	private ClientTest1() {}


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
				System.out.println("Book title: " + lookupBook.bookTitle);
				System.out.println("Qty in stock: " + lookupBook.stockQty);
				System.out.println("Item cost: $" + lookupBook.bookCost);
				System.out.println("Book topic: " + lookupBook.bookTopic);
				System.out.println("---------------");
			}else {
				System.out.println("Item number not found");
				System.out.println("---------------");
			}
		}catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	
/**
 * order() method
 * --------------
 * Returns success/failed ordering result
 * */
	public void order(int itemNum, Store stub) {
		try {
			String orderMsg = stub.order(itemNum);
			System.out.println(orderMsg);
			System.out.println("---------------");
		}catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	

/**
 * reportRequestsNumber() method
 * -----------------------------
 * returns the number of requests on the specified service
 * */
	public void reportRequestsNumber(String service, Store stub) {
		try {
			String requestsReport = stub.reportRequestsNumber(service);
			System.out.println(requestsReport);
			System.out.println("---------------");
		}catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	

/**
 * reportGoodOrders() method
 * -------------------------
 * returns the number of books sold successfully
 * */
	public void reportGoodOrders(Store stub) {
		try {
			String ordersReport = stub.reportGoodOrders();
			System.out.println(ordersReport);
			System.out.println("---------------");
		}catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	
/**
 * reportFailedOrders() method
 * ---------------------------
 * returns the number of orders that failed
 * */
	public void reportFailedOrders(Store stub) {
		try {
			String failedOrders = stub.reportFailedOrders();
			System.out.println(failedOrders);
			System.out.println("---------------");
		}catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	
/**
 * reportServicePerformance() method
 * ---------------------------------
 * returns the average performance of serving the specified service
 * */
	public void reportServicePerformance(String service, Store stub) {
		try {
			String performanceReport = stub.reportServicePerformance(service);
			System.out.println(performanceReport);
			System.out.println("---------------");
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
		ClientTest1 clientApp = new ClientTest1();
		
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			Store stub = (Store) registry.lookup("Store");
			
			//*** TEST search() method ***//
			System.out.println("*** TESTING search() SERVICE ***");
			System.out.println("Searching for: distributed systems");
			clientApp.search("distributed systems", stub);
			System.out.println("Searching for: graduate school");
			clientApp.search("graduate school", stub);
			System.out.println("Searching for: computer science");
			clientApp.search("computer science", stub);
			System.out.println("********************************");
			
			//*** TEST lookup() method ***//
			System.out.println("*** TESTING lookup() SERVICE ***");
			System.out.println("Looking up item number: 1");
			clientApp.lookup(1, stub);
			System.out.println("Looking up item number: 2");
			clientApp.lookup(2, stub);
			System.out.println("Looking up item number: 3");
			clientApp.lookup(3, stub);
			System.out.println("Looking up item number: 4");
			clientApp.lookup(4, stub);
			System.out.println("Looking up item number: 6");
			clientApp.lookup(6, stub);
			System.out.println("********************************");
			
			//*** TEST order method ***//
			System.out.println("*** TESTING order() SERVICE ***");
			System.out.println("Ordering item number: 1");
			clientApp.order(1, stub);
			System.out.println("Ordering item number: 2");
			clientApp.order(2, stub);
			System.out.println("Ordering item number: 3");
			clientApp.order(3, stub);
			System.out.println("Ordering item number: 4");
			clientApp.order(4, stub);
			System.out.println("Ordering item number: 6");
			clientApp.order(6, stub);
			for(int i = 0; i < 5; i++) {
				System.out.println("Ordering item number: 2");
				clientApp.order(2, stub);
			}
			System.out.println("Looking up item number: 2");
			clientApp.lookup(2, stub);
			System.out.println("********************************");
			
			//*** TEST reportRequestsNumber() method ***//
			System.out.println("*** TESTING reportRequestsNumber() ***");
			System.out.println("Reporting requests for: search");
			clientApp.reportRequestsNumber("search", stub);
			System.out.println("Reporting requests for: lookup");
			clientApp.reportRequestsNumber("lookup", stub);
			System.out.println("Reporting requests for: order");
			clientApp.reportRequestsNumber("order", stub);
			System.out.println("********************************");
			
			//*** TEST reportGoodOrders() method ***//
			System.out.println("*** TESTING reportGoodOrders() ***");
			clientApp.reportGoodOrders(stub);
			System.out.println("********************************");
			
			//*** TEST reportFailedOrders() method ***//
			System.out.println("*** TESTING reportFailedOrders() ***");
			clientApp.reportFailedOrders(stub);
			System.out.println("********************************");
			
			//*** TEST reportServicePerformance() method ***//
			System.out.println("*** TESTING reportServicePerformance() ***");
			System.out.println("Reporting service performance for: search");
			clientApp.reportServicePerformance("search", stub);
			System.out.println("Reporting service performance for: lookup");
			clientApp.reportServicePerformance("lookup", stub);
			System.out.println("Reporting service performance for: order");
			clientApp.reportServicePerformance("order", stub);
			System.out.println("********************************");
			
		} catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}

