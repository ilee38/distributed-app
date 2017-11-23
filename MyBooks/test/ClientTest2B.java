package mybooks;

public class ClientTest2B {

		public static void main(String [] args) {
			Runnable r1 = new ClientTest2A(1);
			Runnable r2 = new ClientTest2A(2);
			//Runnable r3 = new ClientTest2A(3);
			//Runnable r4 = new ClientTest2A(4);
			for(int i = 0; i < 30; i++) {
				Thread thread1 = new Thread(r1);
				Thread thread2 = new Thread(r2);
				thread1.start();
				thread2.start();
			}
		}
}
