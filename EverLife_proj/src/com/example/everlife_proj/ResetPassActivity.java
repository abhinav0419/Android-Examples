package com.example.everlife_proj;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ResetPassActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_pass);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reset_pass, menu);
		return true;
	}

}
