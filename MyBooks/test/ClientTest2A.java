package mybooks;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class ClientTest2A implements Runnable{
	private int myID;
	
	public ClientTest2A(int id) {
		this.myID = id;
	}
	
	public void run() {
		String host = null;
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			Store stub = (Store) registry.lookup("Store");
			
			System.out.println("Thread number: " + myID + " is ordering...");
			order(1, stub);
		} catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	
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
}
