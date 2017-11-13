package mybooks;

/**
 * @author iramlee
 * 
 * Remote interface declaring the methods available on the server
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Store extends Remote{
	//Services methods
	String search(String topic) throws RemoteException;
	String lookup(int itemNumber) throws RemoteException;
	String order(int itemNumber) throws RemoteException;
	
	//Reports methods
	String reportRequestsNumber(String service) throws RemoteException;
	String reportGoodOrders() throws RemoteException;
	String reportFailedOrders() throws RemoteException;
	String reportServicePerformance(String service) throws RemoteException;
}
