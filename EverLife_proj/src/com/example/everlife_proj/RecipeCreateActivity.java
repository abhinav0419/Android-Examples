package com.example.everlife_proj;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import android.app.Fragment;
import android.content.Intent;
import android.sax.RootElement;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RecipeCreateActivity extends Activity {
	
	TextView name,ingrdnt,recipe;
	EditText Name,Ingrdnt,Recipe;
	Button save,cancle;
	
	public static final String URL = "http://192.168.0.2:8080/AndroidServletCall/InsertRecipe";



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_create);
		
		name=(TextView)findViewById(R.id.tv_name);
		ingrdnt=(TextView)findViewById(R.id.tv_ing);
		recipe=(TextView)findViewById(R.id.tv_Recepie);
		Name=(EditText)findViewById(R.id.edt_name);
		Ingrdnt=(EditText)findViewById(R.id.edt_ing);
		Recipe=(EditText)findViewById(R.id.edt_Recipe);
		save=(Button)findViewById(R.id.btn_save);
		cancle=(Button)findViewById(R.id.btn_Cancle);
		
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
			getMenuInflater().inflate(R.menu.recipe_create, menu);
			
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
	
			Intent i=new Intent(RecipeCreateActivity.this,RecipeListActivity.class);
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
				// HttpGet httpGet = new HttpGet(url);
				HttpPost httpPost = new HttpPost(url);

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						3);
				nameValuePairs.add(new BasicNameValuePair("R_name", Name.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("R_ingrdnt",Ingrdnt.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("R_recipe", Recipe.getText().toString()));
				
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
			//Toast.makeText(this, output,Toast.LENGTH_LONG).show();
		}
	}

	
	

}
