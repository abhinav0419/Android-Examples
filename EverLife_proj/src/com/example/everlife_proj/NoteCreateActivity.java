package com.example.everlife_proj;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
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

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NoteCreateActivity<GetXMLTask> extends Activity {

	TextView date, time, outputTxt;
	EditText edt_txt, edt_des;
	Button btnsave, btncncle;
	int y, m, d, min, h;
	String username;

	public static final String URL = "http://192.168.0.2:8080/AndroidServletCall/InsertNote";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_create);

		date = (TextView) findViewById(R.id.tv_date_note);
		time = (TextView) findViewById(R.id.tv_time);
		edt_txt = (EditText) findViewById(R.id.edt_line);
		edt_des = (EditText) findViewById(R.id.edtnoteinfo);
		btnsave = (Button) findViewById(R.id.btn_save);
		btncncle = (Button) findViewById(R.id.btn_cncle);
		outputTxt = (TextView) findViewById(R.id.outputTxt);

		final Calendar c = Calendar.getInstance();

		y = c.get(Calendar.YEAR);
		m = c.get(Calendar.MONTH);
		d = c.get(Calendar.DAY_OF_MONTH);
		h = c.get(Calendar.HOUR_OF_DAY);

		m = c.get(Calendar.MINUTE);

		date.setText(d + "/" + m + "/" + y);
		time.setText(h + ":" + m);

		btnsave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GetXMLTask task = new GetXMLTask();
				task.execute(new String[] { URL });
			}
		});

		SharedPreferences sp = this.getSharedPreferences("MyPref",
				MODE_WORLD_READABLE);
		username = sp.getString("username", "");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.note_create, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		Intent i = new Intent(NoteCreateActivity.this, NotesListActivity.class);
		startActivity(i);
		finish();

		return super.onOptionsItemSelected(item);
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
						5);
				nameValuePairs.add(new BasicNameValuePair("Note", edt_txt
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("Description",
						edt_des.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("Date", date
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("Time", time
						.getText().toString()));
				nameValuePairs
						.add(new BasicNameValuePair("UserName", username));

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
			outputTxt.setText(output);
			
			Intent i = new Intent(NoteCreateActivity.this, NotesListActivity.class);
			startActivity(i);
		}
	}

}
