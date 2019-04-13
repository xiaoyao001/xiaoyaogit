package com.boot.org.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResouceReadUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ResouceReadUtil.class);
	
    private static Map<String, String> source = new HashMap<String, String>();
    
    static{
		//读取指定位置的配置文档(读取class目录文件)
    	//classPathSourceRead();
    	//读取磁盘位置的文件	
    	fileSourceRead();
    }

    private static void classPathSourceRead(){
    	//读取指定位置的配置文档(读取class目录文件)
    	try {
			ResourceBundle rb = ResourceBundle.getBundle("application");
			put(rb);
			LOGGER.info("初始化classpath配置文件成功!");
		} catch (Exception e) {
			LOGGER.error("初始化classpath文件失败!",e);
		}
    }
    
    private static void fileSourceRead(){
    	ResourceBundle rb;
    	BufferedInputStream inputStream = null; 
    	//String proFilePath = "/home/config/junbao/junbao.properties";
    	String proFilePath = "D:/destop/table/phonepro/application.properties";
        try {
            inputStream = new BufferedInputStream(new FileInputStream(proFilePath));  
            rb = new PropertyResourceBundle(inputStream);  
            put(rb);
            LOGGER.info("初始化配置文件成功!");
        } catch (Exception e) {  
           LOGGER.error("初始化配置文件失败,文件地址: "+ proFilePath, e);  
        } finally{
        	try {
        		if (inputStream != null) {
        			inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}  
        }
    }
    
    private synchronized static void put(ResourceBundle rb){
    	if (rb != null) {
    		Set<String> keySet = rb.keySet();
        	for (String key : keySet) {
        		source.put(key, rb.getString(key));
    		}
		}
    }
    /**
     * 根据配置文件中的key查询值
     * @param key
     * @return
     */
    public static String get(String key){
    	String msg = null;
    	if (source.containsKey(key)) {
    		msg = source.get(key);
		}
    	return msg;
    }
    
    public static void main(String[] args) {
		System.out.println(ResouceReadUtil.get("JUNBAO_URL"));
	}
}
