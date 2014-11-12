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

import com.example.JSONParser.JSONNote;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class NotesListActivity extends Activity {

	TextView txt;
	ListView lst;
	int id;
	String sid;
	String final_str;
	private static int itemIndex;

	ArrayList<HashMap<String, String>> Note_List = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> Note_List1 = new ArrayList<HashMap<String, String>>();

	public static final String URL = "http://192.168.0.2:8080/AndroidServletCall/shownote.jsp";
	public static final String Del_URL = "http://192.168.0.2:8080/AndroidServletCall/DeleteNote";

	private static String Stuff_Note = "notes";
	private static String Note_Title = "note";
	private static String Note_Id = "note_id";
	// private static String Note_Date = "date";
	// private static String Note_Time = "time";
	

	JSONArray jarray = null;

	JSONNote jparser = new JSONNote();

	// JSON string from URI
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
	.permitAll().build();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes_list);
		

		lst = (ListView)findViewById(R.id.lst_item);
		
	

		StrictMode.setThreadPolicy(policy);

		JSONObject json = jparser.getJSONFromUrl(URL);
		Log.e(">>>>>>", " >>>" + json.toString());
		try {
			JSONArray jarray = json.getJSONArray(Stuff_Note);
			Log.e(">>>>", ">>>>" + jarray.length());
			for (int i = 0; i < jarray.length(); i++) {
				JSONObject j = jarray.getJSONObject(i);

				
				// String date = j.getString(Note_Date);
				// String time = j.getString(Note_Time);
				id = j.getInt(Note_Id);

				String note = j.getString(Note_Title);
				sid = String.valueOf(id);
				Log.e("<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>",
						" iiiiiiiiiiiiiiddddddddd" + sid);

				HashMap<String, String> hmap = new HashMap<String, String>();
				HashMap<String, String> hmap1 = new HashMap<String, String>();

				hmap.put(Note_Title, note);
				// hmap.put(Note_Date, date);
				// hmap.put(Note_Time, time);
				hmap1.put(Note_Id, sid);

				Note_List.add(hmap);
				Note_List1.add(hmap1);

				Log.e("???????<<<<<<<>>>>>>>>>>??????????????", "NOTES" + note);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		ListAdapter adapter = new SimpleAdapter(NotesListActivity.this, Note_List,
				R.layout.notelistitem, new String[] { Note_Title },
				new int[] { R.id.tv_note_list });
		lst.setAdapter(adapter);
		registerForContextMenu(lst);
		Log.e("<<<<<<<<<<<<<<<<<<<<<<<", ">>>>>>>>>>>>>>>>>>>>>>>>>");

		
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == R.id.lst_item) {
			menu.add(0, 1, 1, "Delete");
			menu.add(0, 2, 2, "Cancel");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
			getMenuInflater().inflate(R.menu.notes_list, menu);
			
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
	
			Intent i=new Intent(NotesListActivity.this,NoteCreateActivity.class);
			startActivity(i);
			finish();
		
			return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		AdapterView.AdapterContextMenuInfo menuInfo;
		menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();

		itemIndex = menuInfo.position;

		final String userId = Note_List1.get(itemIndex).toString();
		Log.e("%%%%%%%%%%%%%%%%", "" + userId);

		final String[] str1 = userId.split("=");
		Log.e("0000000000000000000", "" + str1[1]);

		int s = str1[1].length();
		final_str = str1[1].substring(0, s - 1);

		Log.e("???????????????????????/////////////", "" + final_str);

		if (item.getItemId() == 1) {
			new AlertDialog.Builder(NotesListActivity.this)
					.setIcon(R.drawable.ic_launcher)
					.setTitle("Confirm Delete")
					.setMessage("Are You Sure?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									GetXMLTask task = new GetXMLTask();
									task.execute(new String[] { Del_URL });
								}
							}).setNegativeButton("No", null).show();
		}

		return super.onContextItemSelected(item);
	}

	class GetXMLTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			String output = null;
			for (String url : urls) {
				output = getOutputFromUrl(url);
			}
			return output;
		}

		private String getOutputFromUrl(String url) {
			String output = null;
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				// HttpGet httpGet = new HttpGet(url);
				HttpPost httpPost = new HttpPost(url);

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						1);
				nameValuePairs
						.add(new BasicNameValuePair("Note_ID", final_str));

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
			// outputTxt.setText(output);
			
			Intent i = new Intent(NotesListActivity.this, NotesListActivity.class);
			startActivity(i);
			
		}
	}

}
