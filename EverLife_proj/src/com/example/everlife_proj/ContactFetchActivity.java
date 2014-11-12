package com.example.everlife_proj;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class ContactFetchActivity extends Activity {
	

	ListView lst;
	TextView tv;
	String phoneNumber;
	TextView nameCnt, number;
	
public static final String URL = "http://192.168.0.2:8080/AndroidServletCall/InsertContact" ;
	
	JSONObject jsonObj;
	
	
	JSONObject jsonObject = new JSONObject();
	JSONArray jsonArray = new JSONArray();
	
	 private static final String CON_NAME = "contname";
	 private static final String CON_NUMBER = "contnumber";
	 

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_contact_fetch);
			
		lst = (ListView) findViewById(R.id.listView1);
		nameCnt = (TextView)findViewById(R.id.tv_name);
		number = (TextView)findViewById(R.id.tv_number);
		tv = (TextView)findViewById(R.id.textView1);
		
		ArrayList<HashMap<String, String>> contactData = new ArrayList<HashMap<String, String>>();

		ContentResolver cr = getContentResolver();
		Cursor cursor = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

		while (cursor.moveToNext()) {

			try {

				String contactId = cursor.getString(cursor
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		/*		String hasPhone = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));*/

				if (Integer
						.parseInt(cursor.getString(cursor
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

					Cursor phones = getContentResolver().query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = " + contactId, null, null);
				
					
					while (phones.moveToNext()) {

						phoneNumber = phones
								.getString(phones
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

						HashMap<String, String> map = new HashMap<String, String>();

						map.put(CON_NAME, name);
						map.put(CON_NUMBER, phoneNumber);
						contactData.add(map);
						
						
				
						jsonObj = new JSONObject();
						jsonObj.put("contact_name", name);
						jsonObj.put("contact_number", phoneNumber);
						
						jsonArray.put(jsonObj);
						
						

						lst.setAdapter(new SimpleAdapter(ContactFetchActivity.this,
								contactData, R.layout.contactlistitem, new String[] {
										CON_NAME, CON_NUMBER }, new int[] {
										R.id.tv_name, R.id.tv_number }));

					}
					phones.close();
					
					jsonObject.put("contacts", jsonArray);
					
				}
			} catch (Exception e) {
			}
			Log.e("<<<<<<<<<<<", ""+jsonObject);
		}
		
		GetXMLTask task = new GetXMLTask();
		task.execute(new String[] { URL });
		
	
	}
	

	class GetXMLTask extends AsyncTask<String, Void, String> {
		
		  
		@Override
		protected String doInBackground(String... urls) {
			String output = null;
			for (String url : urls) {
				output = getOutputFromUrl(url);
				Log.e(">>>>>>>>>>>>>", ">>>>>>>>>>>>>> call the url"+ url );
			}
			return output;
		}

		private String getOutputFromUrl(String url) {
			String output = null;
			Log.e(">>>>>>>>>>>>>", ">>>>>>>>>>>>>> call the url Output URL    "+ url );
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				// HttpGet httpGet = new HttpGet(url);
				HttpPost httpPost = new HttpPost(url);

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						1);
				nameValuePairs.add(new BasicNameValuePair("Contact", jsonObject.toString()));
				
				Log.e(">>>>>>>>>>>>>>>>>>", ">>>>>>>>>>>."+ jsonObject.toString());
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				HttpResponse httpResponse = httpClient.execute(httpPost);
				
				HttpEntity httpEntity = httpResponse.getEntity();
				output = EntityUtils.toString(httpEntity);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return output;
		}

		@Override
		protected void onPostExecute(String output) {
			tv.setText(output);
		}
	}

}