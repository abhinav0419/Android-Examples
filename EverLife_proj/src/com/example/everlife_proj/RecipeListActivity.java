package com.example.everlife_proj;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.JSONParser.JSONRecipe;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class RecipeListActivity extends Activity {

	TextView txt;
	ListView lst;
	
	
	public static final String URL = "http://192.168.0.2:8080/AndroidServletCall/showrecipe.jsp";

	private static String Stuff_Recipe = "recipes";
	private static String Recipe_Title = "Rc_Name";
	//private static String Note_Date = "date";
	//private static String Note_Time = "time";

	JSONArray jarray = null;

	JSONRecipe jparser = new JSONRecipe();

	// JSON string from URI
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
			.permitAll().build();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_list);
		

		txt = (TextView)findViewById(R.id.tv_Recepie);
		lst = (ListView)findViewById(R.id.lst_recipe);


		StrictMode.setThreadPolicy(policy);
		ArrayList<HashMap<String, String>> Recipe_List = new ArrayList<HashMap<String, String>>();
		JSONObject json = jparser.getJSONFromUrl(URL);
		Log.e(">>>>>>", " >>>" + json.toString());
		try {
			JSONArray jarray = json.getJSONArray(Stuff_Recipe);
				Log.e(">>>>", ">>>>" + jarray.length());
			for (int i = 0; i < jarray.length(); i++) {
				JSONObject j = jarray.getJSONObject(i);

				String recipe = j.getString(Recipe_Title);
				//String date = j.getString(Note_Date);
				//String time = j.getString(Note_Time);

				HashMap<String, String> hmap = new HashMap<String, String>();
				hmap.put(Recipe_Title, recipe);
				//hmap.put(Note_Date, date);
				//hmap.put(Note_Time, time);

				Recipe_List.add(hmap);
				
				Log.e("???????<<<<<<<>>>>>>>>>>??????????????", "NOTES"+recipe);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		ListAdapter adapter = new SimpleAdapter(RecipeListActivity.this, Recipe_List,
				R.layout.recipelistitem, new String[] { Recipe_Title }, new int[] { R.id.tv_recipe});
		lst.setAdapter(adapter);
		
		Log.e("<<<<<<<<<<<<<<<<<<<<<<<", ">>>>>>>>>>>>>>>>>>>>>>>>>");
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
			getMenuInflater().inflate(R.menu.recipe_list, menu);
			
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
	
			Intent i=new Intent(RecipeListActivity.this,RecipeCreateActivity.class);
			startActivity(i);
			finish();
		
			return super.onOptionsItemSelected(item);
	}

}
