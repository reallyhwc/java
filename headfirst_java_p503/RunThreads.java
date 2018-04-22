
public class RunThreads implements Runnable {
	public static void main(String[] args) {
		RunThreads runner = new RunThreads();

		//创建两个线程
		Thread alpha = new Thread(runner);
		Thread beta = new Thread(runner);
		alpha.setName("Alpha thread");
		beta.setName("		Beta thread");
		alpha.start();
		beta.start();
	}

	public void run() {
		for (int i = 0; i < 100; i++) {
			String threadName = Thread.currentThread().getName();
			System.out.println(threadName + " is running");
		}
	}
}