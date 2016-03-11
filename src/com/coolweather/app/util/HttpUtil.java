package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

	public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection connection=null;
				try{
					URL url=new URL(address);
					connection=(HttpURLConnection)url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream input=connection.getInputStream();
					BufferedReader reader=new BufferedReader(new InputStreamReader(input));
					StringBuilder response=new StringBuilder();
					String line=null;
					while((line=reader.readLine())!=null){
						response.append(line);
					}
					if(listener!=null){
						//回调onFinish方法
						listener.onFinish(response.toString());
					}
					
				}catch(Exception e){
					if(listener!=null){
						//回调onError方法
						listener.onError();
					}
				}finally{
					if(connection!=null){
						connection.disconnect();
					}
					
				}
			}
			
		}).start();
	}
}
