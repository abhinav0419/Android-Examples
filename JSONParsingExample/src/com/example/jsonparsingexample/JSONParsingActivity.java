package com.example.jsonparsingexample;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import com.example.adapters.CustomListAdapter;
import com.example.bean.JSONBean;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.v4.app.NavUtils;

public class JSONParsingActivity extends Activity {

	ListView list_view;
	CustomListAdapter adapter;
	
	JSONBean item;
	ArrayList<JSONBean> contacts_arraylist = new ArrayList<JSONBean>();
	
	String url = "http://www.androidhive.info/2012/01/android-json-parsing-tutorial/";
	
    // JSON Node names
    private static final String TAG_CONTACTS = "contacts";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_PHONE_MOBILE = "mobile";
    private static final String TAG_PHONE_HOME = "home";
    private static final String TAG_PHONE_OFFICE = "office";
 
    // contacts JSONArray
    JSONArray contacts = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonparsing);
        
        list_view = (ListView) findViewById(R.id.list_view);
        
        
        
        //list_view.setAdapter(new CustomListAdapter(this, R.layout.list_row, objects));
    }
    
    public class AsyncTaskContacts extends AsyncTask<ArrayList<JSONBean>, Void, ArrayList<JSONBean>> {

    	ProgressDialog dialog = new ProgressDialog(JSONParsingActivity.this);
    	
		@Override
		protected ArrayList<JSONBean> doInBackground(
				ArrayList<JSONBean>... arg0) {
			
	        // Creating JSON Parser instance
	        JSONParser jParser = new JSONParser();
	 
	       
	        
	        try {
	            // Getting Array of Contacts
	            contacts = json.getJSONArray(TAG_CONTACTS);
	 
	            // looping through All Contacts
	            for(int i = 0; i < contacts.length(); i++){
	            	item = new JSONBean();
	                JSONObject c = contacts.getJSONObject(i);
	 
	                // Storing each json item in variable
	                item.setId(Integer.parseInt(c.getString(TAG_ID)));
	                item.setName(c.getString(TAG_NAME));
	                item.setEmail(c.getString(TAG_EMAIL));
	                String address = c.getString(TAG_ADDRESS);
	                String gender = c.getString(TAG_GENDER);
	 
	                // Phone number is agin JSON Object
	                JSONObject phone = c.getJSONObject(TAG_PHONE);
	                item.setMobile(phone.getString(TAG_PHONE_MOBILE));
	                String home = phone.getString(TAG_PHONE_HOME);
	                String office = phone.getString(TAG_PHONE_OFFICE);
	                
	                contacts_arraylist.add(item);
	            
	            }
	        }catch(Exception e) {
	        	e.printStackTrace();
	        }
			return contacts_arraylist;
		}

		@Override
		protected void onPostExecute(ArrayList<JSONBean> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(this.dialog.isShowing()) {
				this.dialog.dismiss();
			}
			
			list_view.setAdapter(new CustomListAdapter(JSONParsingActivity.this, R.layout.list_row, contacts_arraylist));

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			if(dialog == null) {
				this.dialog.setMessage("Loading...");	
				this.dialog.show();	
			}
		}
    	
		
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_jsonparsing, menu);
        return true;
    }

    
}
