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
import java.util.*;

public class Server implements Store{
	
	private int searchCount = 0, lookupCount = 0, buyCount = 0;	//Counters
	private int searchTime = 0, lookupTime = 0, buyTime = 0;		//Timers
	private int itemID = 1;
	private int initialQty = 50;		//initial stock quantity per book
	private String topic1 = "Distributed Systems";
	private String topic2 = "Graduate School";
	//Two book maps, one for each book topic. The map associates the Book object with its stock quantity. 
	private Map<Book, Integer> top1 = new LinkedHashMap<Book, Integer>();
	private Map<Book, Integer> top2 = new LinkedHashMap<Book, Integer>();
	ArrayList<Book> responseList = new ArrayList<Book>();
	
	public Server() {}
	

/**
 * search() method
 * */
	public ArrayList<Book> search(String topic) {
		long beginTime = System.currentTimeMillis();
		responseList.clear();	//clear the list before each search
		if(topic.equals(topic1)) {
			for(Book book : top1.keySet()) {
				responseList.add(book);
			}
			long endTime = System.currentTimeMillis();
			searchTime += (int)(endTime - beginTime);
			return responseList;
		}else if (topic.equals(topic2)) {
			for(Book book : top2.keySet()) {
				responseList.add(book);
			}
			long endTime = System.currentTimeMillis();
			searchTime += (int)(endTime - beginTime);
			return responseList;
		}
		responseList = null;
		long endTime = System.currentTimeMillis();
		searchTime += (int)(endTime - beginTime);
		return responseList;
	}
	
	
/**
 * lookup() method
 * */
	public Book lookup(int itemNumber) {
		long beginTime = System.currentTimeMillis();
		for(Book book : top1.keySet()) {
			if(book.itemNumber == itemNumber) {
				book.setStockQty(top1.get(book));
				long endTime = System.currentTimeMillis();
				lookupTime += (int)(endTime - beginTime);
				return book;
			}
		}
		for(Book book : top2.keySet()) {
			if(book.itemNumber == itemNumber) {
				book.setStockQty(top2.get(book));
				long endTime = System.currentTimeMillis();
				lookupTime += (int)(endTime - beginTime);
				return book;
			}
		}
		long endTime = System.currentTimeMillis();
		lookupTime += (int)(endTime - beginTime);
		Book book = null;
		return book;
	}
	
	
/**
 * order() method
 * */
	public String order(int itemNumber) {
		long beginTime = System.currentTimeMillis();
		Book book = lookup(itemNumber);
		if(book != null) {
			if(book.stockQty > 0) {
				//update stock qty
				if(top1.containsKey(book)) {
					top1.replace(book, book.stockQty - 1);
				}else {
					top2.replace(book, book.stockQty - 1);
				}
				long endTime = System.currentTimeMillis();
				buyTime += (int)(endTime - beginTime);
				return "You have successfully ordered " + book.bookTitle;
			}else {
				long endTime = System.currentTimeMillis();
				buyTime += (int)(endTime - beginTime);
				return "Item is out of stock";
			}
		}else {
			long endTime = System.currentTimeMillis();
			buyTime += (int)(endTime - beginTime);
			return "Item number not found";
		}
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
 * */
	private void initBookStore() {
		Book book_1 = new Book("How to be Good at CS5523", topic1, 75.99, itemID);
		top1.put(book_1, initialQty);
		itemID += 1;
		Book book_2 = new Book("RPCs and RMI in Distributed Systems", topic1, 89.99, itemID);
		top1.put(book_2, initialQty);
		itemID += 1;
		Book book_3 = new Book("Why Go to the Graduate School", topic2, 56.98, itemID);
		top2.put(book_3, initialQty);
		itemID += 1;
		Book book_4 = new Book("How to Survive the Graduate School", topic2, 93.85, itemID);
		top2.put(book_4, initialQty);
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
