package com.example.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONRecipe {
	
	static InputStream is = null;
	static JSONObject jObj= null;
	static String json = "";
	
	public JSONRecipe() {
		// TODO Auto-generated constructor stub
	}
	
	
	public JSONObject getJSONFromUrl(String url)
	{
		//Making HttpRequest
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(url);
		
		try {
			HttpResponse httpResp = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResp.getEntity();
			is = httpEntity.getContent();
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while((line = reader.readLine())!= null)
			{
				sb.append(line + "\n");
				
			}
			is.close();
			json=sb.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			jObj = new JSONObject(json);
			
			Log.e(">>>>>>", " >>>" + jObj.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jObj;
		
	}



}
