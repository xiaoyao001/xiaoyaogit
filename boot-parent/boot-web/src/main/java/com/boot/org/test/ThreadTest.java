package com.boot.org.test;

public class ThreadTest {
	
	
	private static Integer count = 100;
	
	
	public static void main(String[] args) {
		sellTickit sell = new sellTickit();
			try {
				Thread thread1 = new Thread(sell,"A窗口");
				Thread thread2 = new Thread(sell,"B窗口");
				Thread thread3 = new Thread(sell,"C窗口");
				Thread thread4 = new Thread(sell,"D窗口");
				Thread thread5 = new Thread(sell,"E窗口");
				thread1.start();
				thread2.start();
				thread3.start();
				thread4.start();
				thread5.start();
				System.err.println("所有的子线程执行完毕：当前count为"+count);
				Thread.currentThread().join();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	
	public static class sellTickit implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(count>0){
				ZookeeperLock lock = new ZookeeperLock();
				lock.lock();
				try {
						if(count == 50){
							System.err.println(Thread.currentThread().getName()+"现在售卖出的票为："+(count--)+"号");
						}
				} catch (Exception e) {
					// TODO: handle exception
				}finally{
					lock.unlock();
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
