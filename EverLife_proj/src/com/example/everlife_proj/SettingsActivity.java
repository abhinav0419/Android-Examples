package com.example.everlife_proj;

import android.media.audiofx.BassBoost.Settings;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity {

	ListPreference listpref;
	Preference reset;
	Preference wallpaper, about, help;
	private final String VIEW = "view";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings_preference);
		listpref = (ListPreference) findPreference("ListGrid");
		reset = (Preference) findPreference("reset");
		about = (Preference) findPreference("About");
		help = (Preference) findPreference("Help");
		wallpaper=(Preference)findPreference("wallpaper");

		listpref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference preferance,
					Object value) {
				// TODO Auto-generated method stub
				if (value.toString().equals("List")) {
					Toast.makeText(SettingsActivity.this, "List", Toast.LENGTH_LONG)
							.show();
					SharedPreferences sp = SettingsActivity.this.getSharedPreferences(
							"MyPref", MODE_WORLD_READABLE);
					SharedPreferences.Editor speditor = sp.edit();
					boolean v = true;
					speditor.putBoolean(VIEW, v);
					speditor.commit();
					Intent in = new Intent(SettingsActivity.this, GridListActivity.class);
					startActivity(in);
				} else {
					Toast.makeText(SettingsActivity.this, "Grid", Toast.LENGTH_LONG)
							.show();
					SharedPreferences sp = SettingsActivity.this.getSharedPreferences(
							"MyPref", MODE_WORLD_READABLE);
					SharedPreferences.Editor speditor = sp.edit();
					boolean v = false;
					speditor.putBoolean(VIEW, v);
					speditor.commit();
					Intent in = new Intent(SettingsActivity.this, GridLayoutActivity.class);
					startActivity(in);

				}
				return true;
			}
		});

		reset.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				Intent in = new Intent(SettingsActivity.this, ResetPassActivity.class);
				startActivity(in);
				return true;
			}
		});

		about.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				Intent in = new Intent(SettingsActivity.this, AboutActivity.class);
				startActivity(in);
				return true;
			}
		});

		help.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				Intent in = new Intent(SettingsActivity.this, HelpActivity.class);
				startActivity(in);

				return true;
			}
		});
		wallpaper.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
			//	Intent in = new Intent(SettingsActivity.this, Wallpaper.class);
			//	startActivity(in);

				return true;
			}
		});
	}



}
