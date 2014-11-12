package com.example.everlife_proj;

import java.util.ListIterator;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class GridLayoutActivity extends Activity implements OnClickListener {

	ImageView notesbtn, taskbtn, contctbtn, locbtn;
	ImageView recipebtn, Imagebtn, eventbtn;
	ImageView videobtn, audiobtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridlist);

		audiobtn = (ImageView) findViewById(R.id.Home_audio);
		contctbtn = (ImageView) findViewById(R.id.Home_contact);
		eventbtn = (ImageView) findViewById(R.id.Home_event);
		Imagebtn = (ImageView) findViewById(R.id.Home_img);
		locbtn = (ImageView) findViewById(R.id.Home_currentlocation);
		notesbtn = (ImageView) findViewById(R.id.home_note);
		recipebtn = (ImageView) findViewById(R.id.Home_recipe);
		taskbtn = (ImageView) findViewById(R.id.Home_task);
		videobtn = (ImageView) findViewById(R.id.Home_video);

		audiobtn.setOnClickListener(this);
		contctbtn.setOnClickListener(this);
		eventbtn.setOnClickListener(this);
		Imagebtn.setOnClickListener(this);
		locbtn.setOnClickListener(this);
		notesbtn.setOnClickListener(this);
		recipebtn.setOnClickListener(this);
		taskbtn.setOnClickListener(this);
		videobtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v == notesbtn) {
			Intent in = new Intent(GridLayoutActivity.this,
					NoteCreateActivity.class);
			finish();
			startActivity(in);

		} else if (v == taskbtn) {

			Intent task = new Intent(GridLayoutActivity.this,
					TaskCreateActivity.class);
			finish();
			startActivity(task);

		} else if (v == contctbtn) {

			Intent bar = new Intent(GridLayoutActivity.this,
					ContactFetchActivity.class);
			finish();
			startActivity(bar);

		} else if (v == locbtn) {

			Intent map = new Intent(GridLayoutActivity.this,
					CurrentLocationActivity.class);
			finish();
			startActivity(map);

		} else if (v == recipebtn) {

			Intent in = new Intent(GridLayoutActivity.this,
					RecipeCreateActivity.class);
			finish();
			startActivity(in);

		} else if (v == Imagebtn) {
			Intent img = new Intent(GridLayoutActivity.this,
					ImageMainActivity.class);
			finish();
			startActivity(img);

		} else if (v == eventbtn) {

			Intent in = new Intent(GridLayoutActivity.this,
					EventCreateActivity.class);
			finish();
			startActivity(in);

		} else if (v == videobtn) {

			Intent in = new Intent(GridLayoutActivity.this,
					VideoMainActivity.class);
			finish();
			startActivity(in);

		} else if (v == audiobtn) {

			Intent in = new Intent(GridLayoutActivity.this,
					AudioMainActivity.class);
			finish();
			startActivity(in);

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		menu.add(0, 1, 1, "LogOut");

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		Toast.makeText(GridLayoutActivity.this, "logout", Toast.LENGTH_LONG).show();
		
		SharedPreferences sp = this.getSharedPreferences("MyPref", MODE_WORLD_WRITEABLE);
				Editor e = sp.edit();

		String s = sp.getString("username", "");
		
		e.putString("username", "");
		e.putString("password", "");
		
		Log.e("****************", ""+s);

		e.commit();

		Intent i = new Intent(GridLayoutActivity.this, LoginActivity.class);
		startActivity(i);
		
		return super.onOptionsItemSelected(item);
	}

	/*public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			finish();

		}
		return super.onKeyDown(keyCode, event);
	}*/

}
