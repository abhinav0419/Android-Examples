package com.example.everlife_proj;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AudioMainActivity extends Activity {
	
	private List<String> mylist;
	File file;
	private MediaPlayer mp = new MediaPlayer();
	String audio_name="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio_main);

		
		ListView listview = (ListView)findViewById(R.id.lstvw1);
		mylist = new ArrayList<String>();
		
		File directory = Environment.getExternalStorageDirectory();
		file = new File(directory + "/Music/");
		File list[] = file.listFiles();
		
		
		for(int i=0;i<list.length;i++)
		{
			mylist.add(list[i].getName());
		}
		
		ArrayAdapter<String>  adpater = new ArrayAdapter<String>(AudioMainActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,mylist);
		listview.setAdapter(adpater);
		registerForContextMenu(listview);
		
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				String path=parent.getItemAtPosition(position).toString();
				
				Toast.makeText(AudioMainActivity.this, "Currently Selected Song:"+path, Toast.LENGTH_LONG).show();
				try {
					mp.reset();
					mp.setDataSource(file + "/"+ path);
					Toast.makeText(AudioMainActivity.this, " Song:"+ file + path, Toast.LENGTH_SHORT).show();
					mp.setOnPreparedListener(new OnPreparedListener() {
						
						@Override
						public void onPrepared(MediaPlayer mp) {
							// TODO Auto-generated method stub
												}						
					});
					mp.prepare();
					mp.start();
				}
				catch (IllegalArgumentException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IllegalStateException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	            //final String item=(String) parent.getItemAtPosition(position);
	            //Toast.makeText(getApplicationContext(), "Currently Selected Song:"+item, Toast.LENGTH_SHORT).show();
				catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				// TODO Auto-generated method stub
				audio_name = mylist.get(position);
				
				Log.e("???????????", ""+audio_name);
			}
		});
		}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == R.id.lstvw1) {
			menu.add(0, 1, 1, "Upload");
			menu.add(0, 2, 2, "Cancel");
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		Log.e("*****************", ""+item.getItemId());
		if(item.getItemId() == 1)
		{
			Intent intent = new Intent(AudioMainActivity.this,AudioUploadActivity.class);
			intent.putExtra("Audio_Name", audio_name);
			startActivity(intent);
		}
		else
		{
			finish();
		}
		
		return super.onContextItemSelected(item);
	}

	

	
	

}
