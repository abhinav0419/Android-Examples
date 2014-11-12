package com.example.everlife_proj;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class VideoUploadActivity extends Activity {

	Button btn_upld;
	ImageButton btn;
	TextView tv, text_upld;

	int serverResponseCode = 0;
	ProgressDialog dialog = null;

	String upLoadServerUri = null;

	/********** File Path *************/
	final String uploadFilePath = Environment.getExternalStorageDirectory()
			.getPath() + "" + File.separator + "Movies" + File.separator;
	String uploadFileName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_upload);
		
		btn_upld = (Button)findViewById(R.id.btn_upload2);
		text_upld = (TextView)findViewById(R.id.text2);
		btn = (ImageButton)findViewById(R.id.imageButton2);
		tv = (TextView)findViewById(R.id.textView2);

		Intent i =getIntent();
		Bundle b = i.getExtras();
		uploadFileName = b.getString("Video_Name");

		Log.e("!!!!!!!!!!!!!!!!!!!!!!!!", "" + uploadFileName);

		text_upld.setText("Uploading file path :- '" + uploadFilePath
				+ uploadFileName + "'");

		upLoadServerUri = "http://192.168.0.2:8080/FileUpload/UploadAudio";

		btn_upld.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				dialog = ProgressDialog.show(VideoUploadActivity.this, "",
						"Uploading file...", true);

				new Thread(new Runnable() {
					public void run() {
					runOnUiThread(new Runnable() {
							public void run() {
								text_upld.setText("uploading started.....");
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
		int maxBufferSize = 1024 * 1024 * 1024 * 5;
		File sourceFile = new File(sourceFileUri);

		if (!sourceFile.isFile()) {

			dialog.dismiss();

			Log.e("uploadFile", "Source File not exist :" + uploadFilePath + ""
					+ uploadFileName);

			runOnUiThread(new Runnable() {
				public void run() {
					text_upld.setText("Source File not exist :"
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
				Log.e(">>", uploadFileName + ">>>" + fileName);
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
									+ "http://192.168.0.2:8080/FileUpload/audio/"
									+ uploadFileName;

							text_upld.setText(msg);
							Toast.makeText(VideoUploadActivity.this,
									"File Upload Complete.", Toast.LENGTH_SHORT)
									.show();
							Log.e(">>>>>", ">>>>>>>>.<<<<<<<<<<<<<<<<<<");

							final MediaPlayer mPlayer = new MediaPlayer();

							final String url = "http://192.168.0.2:8080/FileUpload/audio/"
									+ uploadFileName;

							tv.setText(url);
							btn.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

									try {
										mPlayer.setDataSource(url);
									} catch (IOException e) {
										e.printStackTrace();
									}

									try {
										mPlayer.prepare();
									} catch (IOException e) {
										Toast.makeText(
												VideoUploadActivity.this,
												"You might not set the uri correctly..",
												Toast.LENGTH_LONG);
									}
									mPlayer.start();

									Toast.makeText(VideoUploadActivity.this,
											"Playing Audio", Toast.LENGTH_LONG)
											.show();

								}
							});

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
						text_upld
								.setText("MalformedURLException Exception : check script url.");
						Toast.makeText(VideoUploadActivity.this, "MalformedURLException",
								Toast.LENGTH_SHORT).show();
					}
				});

				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {

				dialog.dismiss();
				e.printStackTrace();

				runOnUiThread(new Runnable() {
					public void run() {
						text_upld.setText("Got Exception : see logcat ");
						Toast.makeText(VideoUploadActivity.this,
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
}