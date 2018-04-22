
public class MyRunnable implements Runnable {
	//run 是继承的！！！
	public void run() {
		go();
	}

	public void go() {
		doMore();
	}

	public void doMore() {
		System.out.println("top o's the stack");
	}
}