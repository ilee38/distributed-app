package mybooks;

/**
 * @author iramlee
 * 
 * Remote interface declaring the methods available on the server
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface Store extends Remote{
	//Services methods
	ArrayList<Book> search(String topic) throws RemoteException;
	Book lookup(int itemNumber) throws RemoteException;
	String order(int itemNumber) throws RemoteException;
	
	//Reports methods
	String reportRequestsNumber(String service) throws RemoteException;
	String reportGoodOrders() throws RemoteException;
	String reportFailedOrders() throws RemoteException;
	String reportServicePerformance(String service) throws RemoteException;
}
