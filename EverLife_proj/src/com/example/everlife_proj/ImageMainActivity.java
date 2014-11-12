package com.example.everlife_proj;

import java.io.File;
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
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageMainActivity extends Activity {

	File file;
	private ImageAdapter imageAdapter;
	ArrayList<String> f = new ArrayList<String>();// list of file paths
	File[] listFile;
	String[] finnal;

	public static final String Del_URL = "http://192.168.0.2:8080/FileUpload/UploadServlet";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_main);
		
		getFromSdcard();
		GridView imagegrid = (GridView)findViewById(R.id.lst_img);
		imageAdapter = new ImageAdapter();
		imagegrid.setAdapter(imageAdapter);

		registerForContextMenu(imagegrid);

		imagegrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				String i = f.get(position);

				Log.e("???????????", "" + i);

				int index = i.lastIndexOf("/");

				String split = i.substring(index);

				Log.e("<<<<<<<<<<<<<", "" + split);

				finnal = split.split("/");
				Log.e("<<<<<<<<<<<<<", "" + finnal[1]);
				Log.e("<<<<<<<<<<<<<", "" + finnal[0]);
				Log.e("<<<<<<<<<<<<<", "" + finnal);
			}

		});
		

	}

	public void getFromSdcard() {

		File directory = Environment.getExternalStorageDirectory();
		file = new File(directory + "");

		Toast.makeText(ImageMainActivity.this, "asss : " + file, Toast.LENGTH_LONG);
		if (file.isDirectory()) {
			listFile = file.listFiles();

			for (int i = 0; i < listFile.length; i++) {

				f.add(listFile[i].getAbsolutePath());

			}
		}
		
	}

	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_main, menu);
		return true;
	}

	public class ImageAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public ImageAdapter() {
			mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return f.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			// Log.e(">>>>>>>>>>>>>>>>",""+i);

			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.galleryitem, null);
				holder.imageview = (ImageView) convertView
						.findViewById(R.id.thumbImage);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Bitmap myBitmap = BitmapFactory.decodeFile(f.get(position));
			holder.imageview.setImageBitmap(myBitmap);
			return convertView;
		}
	}

	class ViewHolder {
		ImageView imageview;

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == R.id.lst_img) {
			menu.add(0, 1, 1, "Upload");
			menu.add(0, 2, 2, "Delete");
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		Log.e("*****************", "" + item.getItemId());
		if (item.getItemId() == 1) {
			Intent intent = new Intent(ImageMainActivity.this, ImageUploadActivity.class);
			intent.putExtra("Image_Name", finnal[1]);
			startActivity(intent);
		} else {
			GetXMLTask task = new GetXMLTask();
			task.execute(new String[] { Del_URL });
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
				nameValuePairs.add(new BasicNameValuePair("Image_Name",
						finnal[1]));
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
		Toast.makeText(getApplicationContext(), "deleted", Toast.LENGTH_LONG).show();
		}
	}

}
