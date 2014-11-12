package com.example.everlife_proj;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ContactRetriveActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_retrive);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_retrive, menu);
		return true;
	}

}
