package com.boot.org.test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class ZookeeperLock {

	private static final String LOCK_PATH = "/LOCK";
	
	private static final String ZKURL = "localhost:2181";
	
	private ZkClient zkClient = new ZkClient(ZKURL,1000,1000,new SerializableSerializer());
	
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
			System.out.println("tryLock当前节点为-----------------------"+currentPoint);
		}
		List<String>children = this.zkClient.getChildren(LOCK_PATH);
		Collections.sort(children);
		if(currentPoint.equals(LOCK_PATH+"/"+children.get(0))){
			return true;
		}else{
			System.out.println("before准备"+currentPoint.substring(6));
			int beforeNumber = Collections.binarySearch(children, currentPoint.substring(6));
			System.out.println("current下标"+beforeNumber);
			beforePoint = LOCK_PATH+"/"+children.get(beforeNumber-1);
			System.out.println("前置节点为："+beforePoint);
		}
		return false;
	}
	
	
	public void unlock(){
//		if(currentPoint != null){
//			String currentPath = LOCK_PATH+currentPoint;
//			System.out.println("删除的节点路劲为："+currentPoint);
			zkClient.delete(currentPoint);
			System.out.println(currentPoint+"解锁");
		//}
	}
}
