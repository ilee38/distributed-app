package mybooks;

/**
 * @author iramlee
 * 
 * Server class, implements the interface Store. 
 * Also, creates and exports the remote objects, and 
 * it registers the remote objects with the Java RMI registry
 */

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Server implements Store{
	
	private static final int INITIAL_STOCK_QTY = 5;		//initial stock quantity per book
	
	private int searchCount = 0, lookupCount = 0, orderCount = 0, failOrderCount = 0;	//Counters
	private long searchTime, lookupTime, orderTime;		//Timers
	private int itemID = 1;
	private String topic1 = "distributed systems";
	private String topic2 = "graduate school";
	//Two book maps, one for each book topic. The map associates the Book object with its stock quantity. 
	private Map<Book, Integer> top1 = new LinkedHashMap<Book, Integer>();
	private Map<Book, Integer> top2 = new LinkedHashMap<Book, Integer>();
	ArrayList<Book> responseList = new ArrayList<Book>();
	
	public Server() {}
	
/**
 * search() method
 * */
	synchronized public ArrayList<Book> search(String topic) {
		long beginTime = System.nanoTime();
		searchCount++;
		responseList.clear();	//clear the list before each search
		if(topic.toLowerCase().equals(topic1)) {
			for(Book book : top1.keySet()) {
				responseList.add(book);
			}
			long endTime = System.nanoTime();
			searchTime += (endTime - beginTime);
			return responseList;
		}else if (topic.toLowerCase().equals(topic2)) {
			for(Book book : top2.keySet()) {
				responseList.add(book);
			}
			long endTime = System.nanoTime();
			searchTime += (endTime - beginTime);
			return responseList;
		}
		responseList = null;
		long endTime = System.nanoTime();
		searchTime += (endTime - beginTime);
		return responseList;
	}
	
	
/**
 * lookup() method
 * */
	synchronized public Book lookup(int itemNumber) {
		long beginTime = System.nanoTime();
		lookupCount++;
		for(Book book : top1.keySet()) {
			if(book.itemNumber == itemNumber) {
				book.setStockQty(top1.get(book));
				long endTime = System.nanoTime();
				lookupTime += (endTime - beginTime);
				return book;
			}
		}
		for(Book book : top2.keySet()) {
			if(book.itemNumber == itemNumber) {
				book.setStockQty(top2.get(book));
				long endTime = System.nanoTime();
				lookupTime += (endTime - beginTime);
				return book;
			}
		}
		long endTime = System.nanoTime();
		lookupTime += (endTime - beginTime);
		Book book = null;
		return book;
	}
	
	
/**
 * order() method
 * */
	synchronized public String order(int itemNumber) {
		long beginTime = System.nanoTime();
		orderCount++;
		Book book = lookup(itemNumber);
		lookupCount--;	//decrease lookup counter, since the previous call to lookup() was not made by a Client
		if(book != null) {
			if(book.stockQty > 0) {
				//update stock qty
				if(top1.containsKey(book)) {
					top1.replace(book, book.stockQty - 1);
				}else {
					top2.replace(book, book.stockQty - 1);
				}
				long endTime = System.nanoTime();
				orderTime += (endTime - beginTime);
				return "You have successfully ordered " + book.bookTitle;
			}else {
				long endTime = System.nanoTime();
				orderTime += (endTime - beginTime);
				failOrderCount++;
				return "Item is out of stock";
			}
		}else {
			long endTime = System.nanoTime();
			orderTime += (endTime - beginTime);
			failOrderCount++;
			return "Item number not found";
		}
	}
	
	
	synchronized public String reportRequestsNumber(String service) {
		if(service.toLowerCase().equals("search")) { 
			return "Total reuqests for service " + service + ": " + searchCount;
		}else if(service.toLowerCase().equals("lookup")) {
			return "Total reuqests for service " + service + ": " + lookupCount;
		}else if(service.toLowerCase().equals("order")) {
			return "Total reuqests for service " + service + ": " + orderCount;
		}
		return service + " is not an available service";
	}
	
	
	synchronized public String reportGoodOrders() {
		return "Number of books sold successfully: " + (orderCount - failOrderCount);
	}
	
	
	synchronized public String reportFailedOrders() {
		return "Number of failed orders: " + failOrderCount;
	}
	
	
	synchronized public String reportServicePerformance(String service) {
		if(service.toLowerCase().equals("search")) { 
			return "Average servicing time for " + service + ": " + ((searchTime/1000) / (long)searchCount) + " ms/request";
		}else if(service.toLowerCase().equals("lookup")) {
			return "Average servicing time for " + service + ": " + ((lookupTime/1000) / (long)lookupCount) + " ms/request";
		}else if(service.toLowerCase().equals("order")) {
			return "Average servicing time for " + service + ": " + ((orderTime/1000) / (long)orderCount) + " ms/request";
		}
		return service + "is not an available service";
	}
	

/**
 * Sets up all the books and related information in the bookstore
 * */
	private void initBookStore() {
		Book book_1 = new Book("How to be Good at CS5523", topic1, 75.99, itemID);
		top1.put(book_1, INITIAL_STOCK_QTY);
		itemID += 1;
		Book book_2 = new Book("RPCs and RMI in Distributed Systems", topic1, 89.99, itemID);
		top1.put(book_2, INITIAL_STOCK_QTY);
		itemID += 1;
		Book book_3 = new Book("Why Go to the Graduate School", topic2, 56.98, itemID);
		top2.put(book_3, INITIAL_STOCK_QTY);
		itemID += 1;
		Book book_4 = new Book("How to Survive the Graduate School", topic2, 93.85, itemID);
		top2.put(book_4, INITIAL_STOCK_QTY);
		itemID += 1;
		
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
