package com.boot.org.test;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * 观察者示例
 * @author xiaoyao
 * @version 2019.3.24
 */
public class WatcherTest {

	private static final String ZKURL = "localhost:2181";
	
	private static ZkClient zkClient = new ZkClient(ZKURL, 1000,1000,new SerializableSerializer());
	
	public static void main(String[] arr) throws InterruptedException {
		System.out.println("线程开始");
		//创建一个持久节点
		String path ="/config";
		zkClient.createPersistent(path);
		
		//实例化一个监听器
		IZkDataListener listener = new IZkDataListener() {
			
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("收到节点被删除的事件，被删除的节点为"+dataPath);
			}
			
			@Override
			public void handleDataChange(String arg0, Object arg1) throws Exception {
				// TODO Auto-generated method stub
				
			}
		};
		zkClient.subscribeDataChanges(path, listener);
		Thread.currentThread().join();
	}
}
