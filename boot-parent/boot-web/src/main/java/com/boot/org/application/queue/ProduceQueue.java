package com.boot.org.application.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.sf.json.JSONObject;

/**
 * 定义队列
 * @author Administrator
 *
 */
public class ProduceQueue {

	/**队列声明*/
	public static BlockingQueue<JSONObject>queue = new LinkedBlockingQueue<JSONObject>(10000);

}
