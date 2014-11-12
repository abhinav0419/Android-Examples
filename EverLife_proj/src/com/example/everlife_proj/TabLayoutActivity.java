package com.example.everlife_proj;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class TabLayoutActivity extends TabActivity {

	String user_name, pass_word;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_layout);

		/*
		 * SharedPreferences sp = this.getSharedPreferences("MyPref",
		 * MODE_WORLD_READABLE); user_name = sp.getString("username", "");
		 * pass_word = sp.getString("password", "");
		 * 
		 * Log.e(">>>>>>>>>>.", "" + user_name);
		 */

		Resources res = getResources();
		TabHost tabhost = getTabHost();

		Intent home = new Intent().setClass(TabLayoutActivity.this,
				GridLayoutActivity.class);

		TabSpec firstspec = tabhost.newTabSpec("tb1")
				.setIndicator("", res.getDrawable(R.drawable.ic_launcher))
				.setContent(home);

		// .setContent(new Intent(this, First.class));

		Intent add = new Intent().setClass(TabLayoutActivity.this,
				SettingsActivity.class);

		TabSpec secondspec = tabhost.newTabSpec("tb2")
				.setIndicator("", res.getDrawable(R.drawable.ic_launcher))
				.setContent(add);

		
		// .setContent(new Intent(this, Three.class));

		tabhost.addTab(firstspec);
		tabhost.addTab(secondspec);
		
		tabhost.setCurrentTab(0);

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

		SharedPreferences sp = this.getPreferences(MODE_WORLD_READABLE);
		Editor e = sp.edit();

		e.putString("username", "");
		e.putString("password", "");

		e.commit();

	//	Intent i = new Intent(TabLayoutActivity.this, Login_Activity.class);
	//	startActivity(i);

		return super.onOptionsItemSelected(item);
	}

	/*
	 * @Override public void onBackPressed() { // TODO Auto-generated method
	 * stub if ((!user_name.equals("")) && (!pass_word.equals(""))) {
	 * 
	 * Intent i = new Intent(Tab_Activity.this, Tab_Activity.class);
	 * startActivity(i);
	 * 
	 * }
	 * 
	 * super.onBackPressed(); }
	 */

}
