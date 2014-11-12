package com.example.everlife_proj;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends Activity {

	TextView outputText;
	EditText uname, password, repassword, emailid;
	Button sign;
	String v_uname, v_pass, v_mail, v_repass;
	

	public final Pattern EMAIL_ADDS_PATTERN = Pattern
			.compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");

	public static final String URL = "http://192.168.0.2:8080/AndroidServletCall/InsertData";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		uname = (EditText) findViewById(R.id.s_edtusername);
		emailid = (EditText) findViewById(R.id.s_edtemail);
		password = (EditText) findViewById(R.id.s_edtpassword);
		repassword = (EditText) findViewById(R.id.s_etrepass);
		sign = (Button) findViewById(R.id.btnsign);
		outputText = (TextView) findViewById(R.id.outputTxt1);

		sign.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				v_uname = uname.getText().toString();
				v_pass = password.getText().toString();
				v_repass = repassword.getText().toString();
				v_mail = emailid.getText().toString();
				
				boolean iseeror=false;

				if (CheckEmail(v_mail)) {
					Toast.makeText(SignupActivity.this, "Valid Email Addresss",
							1).show();

					if (v_pass.equalsIgnoreCase(v_repass)) {
						
						Toast.makeText(SignupActivity.this, "Password Matched",
								1).show();
					} else {
						iseeror=true;
						repassword.setError("Not Match");
					}
				} else {
					iseeror=true;
					emailid.setError("Invalid Email Addresss");
					Toast.makeText(SignupActivity.this,
							"Invalid Email Addresss", 1).show();
				}
				
				if(!iseeror)
				{
				GetXMLTask task = new GetXMLTask();
				task.execute(new String[] { URL });
				}

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
				// HttpGet httpGet = new HttpGet(url);
				HttpPost httpPost = new HttpPost(url);

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						4);
				nameValuePairs.add(new BasicNameValuePair("username", uname
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("email", emailid
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("password", password
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("repassword",
						repassword.getText().toString()));
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
			outputText.setText(output);
			finish();
		}
	}

	private boolean CheckEmail(String email) {
		return EMAIL_ADDS_PATTERN.matcher(email).matches();

	}

}
