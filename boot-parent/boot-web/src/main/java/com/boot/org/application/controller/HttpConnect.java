package com.boot.org.application.controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpConnect {

	
	public static String connectServer(String requestParams, String serverURL) throws Exception {
        String result = "";
        CloseableHttpResponse response = null;
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(serverURL);
            //application/x-www-form-urlencoded
            StringEntity stringEntity = new StringEntity(requestParams, ContentType.create("text/plain", "UTF-8"));
            httppost.setEntity(stringEntity);
            response = httpclient.execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                result = EntityUtils.toString(resEntity);
            } else {
                throw new Exception("异常信息为: " + response.getStatusLine());
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return result;
    }
	
	public static String connectServerGet(String serverURL) throws Exception {
		String result = "";
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(serverURL);
			response = httpclient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity resEntity = response.getEntity();
				result = EntityUtils.toString(resEntity);
			} else {
				throw new Exception("请求失败: " + response.getStatusLine());
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return result;
	}

}
