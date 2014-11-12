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
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class EventCreateActivity extends Activity {

	EditText ev_name, ev_des, ev_loc;
	Button btn_date, btn_time, btn_date_save, btn_time_save, btn_save,
			btn_cncl;

	int y, m, d, min, h;
	String username;

	public static final String URL = "http://192.168.0.2:8080/AndroidServletCall/InsertEvent";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_create);

		ev_name = (EditText) findViewById(R.id.edt_name);
		ev_des = (EditText) findViewById(R.id.edt_desc);
		ev_loc = (EditText) findViewById(R.id.edt_location);

		btn_date = (Button) findViewById(R.id.btn_date);
		btn_date_save = (Button) findViewById(R.id.btn_date_set);
		btn_time = (Button) findViewById(R.id.btn_time);
		btn_time_save = (Button) findViewById(R.id.btn_time_set);
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_cncl = (Button) findViewById(R.id.btn_cncl);

		final Calendar c = Calendar.getInstance();

		y = c.get(Calendar.YEAR);
		m = c.get(Calendar.MONTH);
		d = c.get(Calendar.DAY_OF_MONTH);
		h = c.get(Calendar.HOUR_OF_DAY);
		min = c.get(Calendar.MINUTE);

		btn_date_save.setText(d + "/" + (m + 1) + "/" + y);
		btn_time_save.setText(h + ":" + min);

		final DatePickerDialog.OnDateSetListener datepicker = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {

				y = year;
				m = monthOfYear;
				d = dayOfMonth;

				btn_date_save.setText(d + "/" + (m + 1) + "/" + y);
				// TODO Auto-generated method stub

			}
		};

		final TimePickerDialog.OnTimeSetListener timepicker = new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub

				h = hourOfDay;
				min = minute;

				btn_time_save.setText(h + " : " + min);

			}
		};

		btn_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DatePickerDialog dp = new DatePickerDialog(
						EventCreateActivity.this, datepicker, c
								.get(Calendar.YEAR), c.get(Calendar.MONTH), c
								.get(Calendar.DAY_OF_MONTH));

				dp.show();

			}
		});

		btn_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				TimePickerDialog tp = new TimePickerDialog(
						EventCreateActivity.this, timepicker, c
								.get(Calendar.HOUR_OF_DAY), c
								.get(Calendar.MINUTE), false);

				tp.show();

			}
		});

		SharedPreferences sp = this.getSharedPreferences("MyPref",
				MODE_WORLD_READABLE);
		username = sp.getString("username", "");

		btn_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GetXMLTask task = new GetXMLTask();
				task.execute(new String[] { URL });
			}
		});

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
						6);
				nameValuePairs.add(new BasicNameValuePair("Event_Name", ev_name
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("Event_Description",
						ev_des.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("Event_Location",
						ev_loc.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("Event_Date",
						btn_date_save.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("Event_Time",
						btn_time_save.getText().toString()));
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
			// outputTxt.setText(output);
			Intent i = new Intent(EventCreateActivity.this,
					EventlistActivity.class);
			startActivity(i);
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		Intent i = new Intent(EventCreateActivity.this, EventlistActivity.class);
		startActivity(i);
		finish();

		return super.onOptionsItemSelected(item);
	}

}
