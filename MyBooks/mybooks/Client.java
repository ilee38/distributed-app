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
import java.io.*;

public class Client{
	
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
	
	
	private void displayMenu() {
		System.out.println("Select service number from the list: ");
		System.out.println("1: Search");
		System.out.println("2: Lookup");
		System.out.println("3: Order");
		System.out.println("4: Report Requests Number");
		System.out.println("5: Report Good Orders");
		System.out.println("6: Report Failed Orders");
		System.out.println("7: Report Service Performance");
		System.out.println("8: Exit program");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Setting the host to null indicates that the local host address should be used
		String host = null;
		Client clientApp = new Client();
		BufferedReader strReader = new BufferedReader(new InputStreamReader(System.in));
		Scanner intReader = new Scanner(System.in);
		boolean exit = false;
		
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			Store stub = (Store) registry.lookup("Store");
			
			while( exit == false) {
				clientApp.displayMenu();
				int selectedService = intReader.nextInt();
				switch(selectedService) {
					case 1:
						System.out.println("for Search, specifiy topic: distributed systems or graduate school");
						String searchTopic = strReader.readLine().toLowerCase();
						clientApp.search(searchTopic, stub);
						break;
					case 2:
						System.out.println("for Lookup, specifiy item number (1 to 4)");
						int itemNum = intReader.nextInt();
						clientApp.lookup(itemNum, stub);
						break;
					case 3:
						System.out.println("for Order, specifiy item number (1 to 4)");
						int itemNbr = intReader.nextInt();
						clientApp.order(itemNbr, stub);
						break;
					case 4:
						System.out.println("for Requests Number report, specifiy one service: search, lookup, order");
						String serviceReport = strReader.readLine().toLowerCase();
						clientApp.reportRequestsNumber(serviceReport, stub);
						break;
					case 5: 
						clientApp.reportGoodOrders(stub);
						break;
					case 6:
						clientApp.reportFailedOrders(stub);
						break;
					case 7:
						System.out.println("for Service Performance report, specifiy one service: search, lookup, order");
						String perfReport = strReader.readLine().toLowerCase();
						clientApp.reportServicePerformance(perfReport, stub);
						break;
					case 8:
						exit = true;
						break;
					default:
						System.out.println("Incorrect entry.");
						break;
				}
			}
		} catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
		intReader.close();	
	}
}
