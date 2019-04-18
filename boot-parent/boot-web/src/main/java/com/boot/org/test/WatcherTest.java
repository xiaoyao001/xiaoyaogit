package com.boot.org.test;

import java.io.UnsupportedEncodingException;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.I0Itec.zkclient.serialize.ZkSerializer;

/**
 * 观察者示例
 * @author xiaoyao
 * @version 2019.3.24
 */
public class WatcherTest {
	
	private static final String ZKURL = "localhost:2181";
	
	private static ZkClient zkClient = new ZkClient(ZKURL,1000,1000,new SerializableSerializer());
	
	public static void main(String[] arr) throws InterruptedException {
		zkClient.setZkSerializer(new ZkSerializer() {
			
			@Override
			public byte[] serialize(Object data) throws ZkMarshallingError {
				try {
					return String.valueOf(data).getBytes("utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return null;
				}
				
			}
			
			@Override
			public Object deserialize(byte[] bytes) throws ZkMarshallingError {
				try {
					return new String(bytes, "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					return null;
				}
				
			}
		});
		/*DataSourceConfig.setUrl(zkClient.readData("/config/pre/datasource/url").toString());
		DataSourceConfig.setDriverClassName(zkClient.readData("/config/pre/datasource/driver").toString());
		DataSourceConfig.setUsername(zkClient.readData("/config/pre/datasource/username").toString());
		DataSourceConfig.setPassword(zkClient.readData("/config/pre/datasource/password").toString());
		System.out.println("初始化"+DataSourceConfig.getDriverClassName());*/
		IZkDataListener dataListener = new IZkDataListener() {
			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				// TODO Auto-generated method stub
				/*DataSourceConfig.setUrl(zkClient.readData("/config/pre/datasource/url").toString());
				DataSourceConfig.setDriverClassName(zkClient.readData("/config/pre/datasource/driver").toString());
				DataSourceConfig.setUsername(zkClient.readData("/config/pre/datasource/username").toString());
				DataSourceConfig.setPassword(zkClient.readData("/config/pre/datasource/password").toString());*/
				System.out.println("监听内部"+zkClient.readData("/config/pre/datasource/url").toString());
			}
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				// TODO Auto-generated method stub

			}
		};
		zkClient.subscribeDataChanges("/config/pre/datasource/driver", dataListener);
		Thread.currentThread().join();
	}
}
