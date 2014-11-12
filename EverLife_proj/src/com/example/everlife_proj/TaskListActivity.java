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

import com.example.JSONParser.JSONTask;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class TaskListActivity extends Activity {

	TextView txt;
	ListView lst;
	int id;
	String sid;
	String final_str;
	private static int itemIndex;

	public static final String URL = "http://192.168.0.2:8080/AndroidServletCall/showtask.jsp";
	public static final String Del_URL = "http://192.168.0.2:8080/AndroidServletCall/DeleteTask";
	
	ArrayList<HashMap<String, String>> Task_List = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> Task_List1 = new ArrayList<HashMap<String, String>>();
	
	private static String Stuff_Task = "tasks";
	private static String Task_Title = "name";
	private static String Task_Id = "task_id";
	// private static String Note_Date="date";
	// private static String Note_Time="time";

	JSONArray jarray = null;

	JSONTask jparser = new JSONTask();

	// getting JSON string from URI
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
			.permitAll().build();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_create);
		
		txt = (TextView) findViewById(R.id.tv_task);
		lst = (ListView)findViewById(R.id.lst_task);

		StrictMode.setThreadPolicy(policy);
		
		JSONObject json = jparser.getJSONFromUrl(URL);

		try {
			JSONArray jarray = json.getJSONArray(Stuff_Task);

			for (int i = 0; i < jarray.length(); i++) {
				JSONObject j = jarray.getJSONObject(i);

				String name = j.getString(Task_Title);
				
				id = j.getInt(Task_Id);

				sid = String.valueOf(id);
				Log.e("<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>",
						" iiiiiiiiiiiiiiddddddddd" + sid);

				// String date=j.getString(Note_Date);
				// String time=j.getString(Note_Time);

				HashMap<String, String> hmap = new HashMap<String, String>();
				HashMap<String, String> hmap1 = new HashMap<String, String>();
				
				
				hmap.put(Task_Title, name);
				hmap1.put(Task_Id, sid);
				// hmap.put(Note_Date, date);
				// hmap.put(Note_Time, time);

				Task_List.add(hmap);
				Task_List1.add(hmap1);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		ListAdapter adapter = new SimpleAdapter(TaskListActivity.this, Task_List,
				R.layout.tasklistitem, new String[] { Task_Title },
				new int[] { R.id.tv_task });
		lst.setAdapter(adapter);
		registerForContextMenu(lst);
	
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == R.id.lst_task) {
			menu.add(0, 1, 1, "Delete");
			menu.add(0, 2, 2, "Cancel");
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		AdapterView.AdapterContextMenuInfo menuInfo;
		menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();

		itemIndex = menuInfo.position;

		final String userId = Task_List1.get(itemIndex).toString();
		Log.e("%%%%%%%%%%%%%%%%", "" + userId);

		final String[] str1 = userId.split("=");
		Log.e("0000000000000000000", "" + str1[1]);

		int s = str1[1].length();
		final_str = str1[1].substring(0, s - 1);

		Log.e("???????????????????????/////////////", "" + final_str);

		if (item.getItemId() == 1) {
			new AlertDialog.Builder(TaskListActivity.this)
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
						.add(new BasicNameValuePair("Task_ID", final_str));

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
		}
	}

}