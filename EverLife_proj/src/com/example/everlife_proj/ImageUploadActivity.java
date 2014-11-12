package com.example.everlife_proj;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ImageUploadActivity extends Activity {
	
	TextView messageText;
	Button uploadButton;
	int serverResponseCode = 0;
	ProgressDialog dialog = null;

	String upLoadServerUri = null;

	/********** File Path *************/
	final String uploadFilePath = Environment.getExternalStorageDirectory()
			.getPath() + "" + File.separator;
	
	String uploadFileName=null;
	

	//final String uploadFileName = "123.jpg";
	private ImageView showimage;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_upload);
		
		uploadButton = (Button)findViewById(R.id.uploadButton);
		messageText = (TextView)findViewById(R.id.messageText);
		showimage = (ImageView)findViewById(R.id.showimage);

		Intent i = getIntent();
		Bundle b = i.getExtras();
		uploadFileName = b.getString("Image_Name");
	
		Log.e("!!!!!!!!!!!!!!!!!!!!!!!!", ""+uploadFileName);
		
		messageText.setText("Uploading file path :- '" + uploadFilePath
				+ uploadFileName + "'");

		/************* Php script path ****************/
		upLoadServerUri = "http://192.168.0.2:8080/FileUpload/UploadServlet";

		uploadButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				dialog = ProgressDialog.show(ImageUploadActivity.this, "",
						"Uploading file...", true);

				new Thread(new Runnable() {
					public void run() {
						runOnUiThread(new Runnable() {
							public void run() {
								messageText.setText("uploading started.....");
							}
						});

						uploadFile(uploadFilePath + "" + uploadFileName);

					}
				}).start();
			}
		});
		
	
	}
	
	public int uploadFile(String sourceFileUri) {

		String fileName = sourceFileUri;

		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 2 * 1024 * 1024;
		File sourceFile = new File(sourceFileUri);

		if (!sourceFile.isFile()) {

			dialog.dismiss();

			Log.e("uploadFile", "Source File not exist :" + uploadFilePath + ""
					+ uploadFileName);

			runOnUiThread(new Runnable() {
				public void run() {
					messageText.setText("Source File not exist :"
							+ uploadFilePath + "" + uploadFileName);
				}
			});

			return 0;

		} else {
			try {

				// open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(
						sourceFile);
				URL url = new URL(upLoadServerUri);

				// Open a HTTP connection to the URL
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("fileName", uploadFileName);

				dos = new DataOutputStream(conn.getOutputStream());

				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"fileName\";filename=\""
						+ uploadFileName + "\"" + lineEnd);
				Log.e(">>",uploadFileName+">>>" + fileName);
				dos.writeBytes(lineEnd);

				// create a buffer of maximum size
				bytesAvailable = fileInputStream.available();

				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {

					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				}

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();
				String serverResponseMessage = conn.getResponseMessage();

				Log.i("uploadFile", "HTTP Response is : "
						+ serverResponseMessage + ": " + serverResponseCode);

				if (serverResponseCode == 200) {

				runOnUiThread(new Runnable() {
						public void run() {

							String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
									+ "http://192.168.0.2:8080/FileUpload/data/"
									+ uploadFileName;

							messageText.setText(msg);
							Toast.makeText(ImageUploadActivity.this,
									"File Upload Complete.", Toast.LENGTH_SHORT)
									.show();
							Log.e(">>>>>", ">>>>>>>>.<<<<<<<<<<<<<<<<<<");
							Drawable drawable = LoadImageFromWebOperations("http://192.168.0.2:8080/FileUpload/data/"
									+ uploadFileName);
							Log.e(">>>>>", ">>>>>>>>.<<<<<<<<<<<<<<<<<<");
							showimage.setImageDrawable(drawable);

						}
					});
				}

				// close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (MalformedURLException ex) {

				dialog.dismiss();
				ex.printStackTrace();

				runOnUiThread(new Runnable() {
					public void run() {
						messageText
								.setText("MalformedURLException Exception : check script url.");
						Toast.makeText(ImageUploadActivity.this,
								"MalformedURLException", Toast.LENGTH_SHORT)
								.show();
					}
				});

				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {

				dialog.dismiss();
				e.printStackTrace();

				runOnUiThread(new Runnable() {
					public void run() {
						messageText.setText("Got Exception : see logcat ");
						Toast.makeText(ImageUploadActivity.this,
								"Got Exception : see logcat ",
								Toast.LENGTH_SHORT).show();
					}
				});
				Log.e("Upload file to server Exception",
						"Exception : " + e.getMessage(), e);
			}
			dialog.dismiss();
			return serverResponseCode;

		} // End else block
	}

	
	
	protected Drawable LoadImageFromWebOperations(String url) {
		Log.e(">>>>", ">>>>" + url);
		try {
	
			ThreadPolicy threadPolicy = new ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(threadPolicy);
					
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}