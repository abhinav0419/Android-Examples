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

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;



public class TaskCreateActivity extends Activity{
	
	EditText Name, Des;
	Button Date, Time, save,cancle;
	int y, m, d, h, min;
	TextView dateset, timeset,rmndr;
	//Spinner spn;

	public static final String URL = "http://192.168.0.2:8080/AndroidServletCall/InsertTask";
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_create);
		
		Name = (EditText) findViewById(R.id.editName);
		Des = (EditText) findViewById(R.id.editDes);

		Date = (Button) findViewById(R.id.btn_date);
		Time = (Button) findViewById(R.id.btn_time);
		save = (Button) findViewById(R.id.btn_save);
		cancle = (Button) findViewById(R.id.btn_cancle);

		dateset = (TextView) findViewById(R.id.tv_date);
		timeset = (TextView) findViewById(R.id.tv_time);
	//	rmndr = (TextView) findViewById(R.id.tv_rmnd);

		//spn=(Spinner)findViewById(R.id.spn);

		final Calendar c = Calendar.getInstance();

		y = c.get(Calendar.YEAR);
		m = c.get(Calendar.MONTH);
		d = c.get(Calendar.DAY_OF_MONTH);
		h = c.get(Calendar.HOUR_OF_DAY);
		min = c.get(Calendar.MINUTE);

		final DatePickerDialog.OnDateSetListener datepicker = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub

				y = year;
				m = monthOfYear;
				d = dayOfMonth;

				dateset.setText(d + "/" + m + "/" + y);
			}
		};

		final TimePickerDialog.OnTimeSetListener timepicker = new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub

				h = hourOfDay;
				min = minute;

				timeset.setText(h + " : " + min);

			}
		};

		Date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				DatePickerDialog dp = new DatePickerDialog(TaskCreateActivity.this,
						datepicker, c.get(Calendar.YEAR),
						c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

				dp.show();
			}
		});

		Time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				TimePickerDialog tp = new TimePickerDialog(TaskCreateActivity.this,
						timepicker, c.get(Calendar.HOUR_OF_DAY), c
								.get(Calendar.MINUTE), false);

				tp.show();
			}
		});
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				GetXMLTask task = new GetXMLTask();
				task.execute(new String[] { URL });
			}
		});
		
	
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
			getMenuInflater().inflate(R.menu.task_create, menu);
			
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
	
			Intent i=new Intent(TaskCreateActivity.this,TaskListActivity.class);
			startActivity(i);
			finish();
		
			return super.onOptionsItemSelected(item);
	}
	
	private class GetXMLTask extends AsyncTask<String, Void, String> {
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

				HttpPost httpPost = new HttpPost(url);

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						4);
				nameValuePairs.add(new BasicNameValuePair("TaskName", Name
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("TaskDes", Des
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("Date", timeset
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("Time", dateset
						.getText().toString()));

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
			//outputTxt.setText(output);
		}
	}

}