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
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	EditText username, password;
	Button login, signup;
	TextView outputTxt;
	String name, paswrd, success, msg;
	public static final String URL = "http://192.168.0.2:8080/AndroidServletCall/LoginData";

	private static String Login = "login";
	private static String Login_Msg = "message";

	String user_name;
	String user_pass;

	String user_pref, pass_pref;

	Login_JSON jparser = new Login_JSON();

	// JSON string from URI
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
			.permitAll().build();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		SharedPreferences sp = this.getSharedPreferences("MyPref",
				MODE_WORLD_READABLE);
		user_pref = sp.getString("username", "");
		pass_pref = sp.getString("password", "");

		Log.e(">>>>>>>>>>.", "" + user_pref);

		if ((!user_pref.equals("")) && (!pass_pref.equals(""))) {
			Intent i = new Intent(LoginActivity.this, TabLayoutActivity.class);
			startActivity(i);
		}

		outputTxt = (TextView) findViewById(R.id.outputTxt2);
		username = (EditText) findViewById(R.id.edt_username);
		password = (EditText) findViewById(R.id.edt_password);

		login = (Button) findViewById(R.id.btn_login);
		signup = (Button) findViewById(R.id.btn_signup);

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stubJSONObject json =
				// jparser.getJSONFromUrl(URL);

				user_name = username.getText().toString().trim();
				user_pass = password.getText().toString().trim();

				
				Toast.makeText(LoginActivity.this,"Login click", Toast.LENGTH_LONG).show();
				
				GetXMLTask task = new GetXMLTask();
				task.execute(new String[] { URL });

			}
		});

		signup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent i = new Intent(LoginActivity.this,
						SignupActivity.class);
				startActivity(i);

			}
		});

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
						2);
				nameValuePairs.add(new BasicNameValuePair("uname", user_name));
				nameValuePairs.add(new BasicNameValuePair("paswrd", user_pass));
				Log.e("><>>>>>>>>>>>>>>>>>>>", ">>>>>>>>>>" + user_name
						+ ">>>>>>." + user_pass);
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

			StrictMode.setThreadPolicy(policy);

			try {
				JSONObject json = new JSONObject(output);
				Log.e("Login dattaaaa", " >>>" + json.toString());

				try {

					success = json.getString(Login);
					msg = json.getString(Login_Msg);
					Log.e("LLLLLLLLLLLLLLLLLL", "" + success);

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (success.equals("true")) {

					Log.e("DDAATTTTTTTTTTTTTAAAAAAAAAAAA", "" + success);
					SharedPreferences prefs = LoginActivity.this
							.getSharedPreferences("MyPref", MODE_WORLD_READABLE);
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString("username", user_name);
					editor.putString("password", user_pass);
					editor.commit();

					Intent i = new Intent(LoginActivity.this,
							TabLayoutActivity.class);

					Log.e("DDAATTTTTTTTTTTTTAAAAAAAAAAAA", "" + success);

					startActivity(i);
				} else {

				}

			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

}
