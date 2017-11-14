package mybooks;

/**
 * @author iramlee
 *
 * This class represents a book on the store
 */
public class Book {
	String bookTitle;
	String bookTopic;
	int bookCost;
	int itemNumber;
	
	public Book() {}
	
	public Book(String title, String topic, int cost, int itemNum) {
		this.bookTitle = title;
		this.bookTopic = topic;
		this.bookCost = cost;
		this.itemNumber = itemNum;
	}
	
	public void setTitle(String title) {
		this.bookTitle = title;
	}
	
	public void setTopic(String topic) {
		this.bookTopic = topic;
	}
	
	public void setCost(int cost) {
		this.bookCost = cost;
	}
	
	public void setItemNumber(int num) {
		this.itemNumber = num;
	}
	
	public int getBookCost() {
		return bookCost;
	}
	
	public String getBookTitle() {
		return bookTitle;
	}
	
	public String getBookTopic() {
		return bookTopic;
	}
	
	public int getBookItemNumber() {
		return itemNumber;
	}
}
