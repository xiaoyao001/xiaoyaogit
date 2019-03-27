package com.boot.org.test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class ZookeeperLock implements Lock{

	private static final String LOCK_PATH = "/LOCK";
	
	private static final String ZKURL = "localhost:2181";
	
	private ZkClient zkClient = new ZkClient(ZKURL,1000,1000,new SerializableSerializer());
	//信号灯
	private CountDownLatch cdl;
	//当前节点的前一个节点
	private String beforePoint;
	//当前节点
	private String currentPoint;
	
	//判断有没有该节点，如果没有就创建
	public ZookeeperLock(){
		if(!this.zkClient.exists(LOCK_PATH)){
			this.zkClient.createPersistent(LOCK_PATH);
		}
	}
	
	@Override
	public void lock(){
		if(tryLock()){
			return;
		}
		waitForLock();
		lock();
	}
	
	
	public void waitForLock(){
		IZkDataListener dataListener = new IZkDataListener() {
			
			@Override
			public void handleDataDeleted(String arg0) throws Exception {
				// TODO Auto-generated method stub
				if(cdl != null){
					//将信号等里面的1改变：信号灯变绿
					cdl.countDown();
					System.out.println(Thread.currentThread().getName()+"绿灯");
				}
				
			}
			
			@Override
			public void handleDataChange(String arg0, Object arg1) throws Exception {
				// TODO Auto-generated method stub
			
			}
		};
		this.zkClient.subscribeDataChanges(beforePoint, dataListener);
		//给排在前面的节点增加数据删除的watcher  本质是启动另外一个线程去监听前置节点
		if(this.zkClient.exists(beforePoint)){
			//信号灯打开红灯：等待
			cdl = new CountDownLatch(1);
			try {
				System.err.println("等待"+Thread.currentThread().getName());
				cdl.await();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		this.zkClient.unsubscribeDataChanges(beforePoint, dataListener);
	}
	
	public boolean tryLock(){
		//如果拿到当前的节点为空，则尝试加锁
		if(currentPoint == null || currentPoint.length()<=0){
			currentPoint = this.zkClient.createEphemeralSequential(LOCK_PATH+"/", "lock");
		}
		List<String>children = this.zkClient.getChildren(LOCK_PATH);
		Collections.sort(children);
		if(currentPoint.equals(LOCK_PATH+"/"+children.get(0))){
			return true;
		}else{
			int beforeNumber = Collections.binarySearch(children, currentPoint.substring(6));
			beforePoint = LOCK_PATH+"/"+children.get(beforeNumber-1);
		}
		return false;
	}
	
	
	public void unlock(){
		zkClient.delete(currentPoint);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}
}
