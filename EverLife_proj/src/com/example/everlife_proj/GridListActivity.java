package com.example.everlife_proj;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GridListActivity extends Activity {

/*	private List<Constant> lst = new ArrayList<Constant>();
	private ListView lv;
	CustomAdapter csa;
	private final String VIEW = "view";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid);
		SharedPreferences sp = GridActivity.this.getSharedPreferences("MyPref",
				MODE_WORLD_READABLE);
		boolean v = sp.getBoolean(VIEW, true);
		if (v == false) {
			Intent in = new Intent(GridActivity.this, ListGridActivity.class);
			startActivity(in);
			finish();
		}

		Bitmap Icon1 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.product);
		Bitmap Icon2 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.suppliers1);
		Bitmap Icon3 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.bills);
		Bitmap Icon4 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.bills);
		Bitmap Icon5 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.settings);
		Bitmap Icon6 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.report);

		lst.add(new Constant(Icon1, "Products"));
		lst.add(new Constant(Icon2, "Suppliers"));
		lst.add(new Constant(Icon3, "Sales Bills"));
		lst.add(new Constant(Icon4, "Purchase Bills"));
		lst.add(new Constant(Icon5, "Settings"));
		lst.add(new Constant(Icon6, "Report"));

		lv = (ListView) findViewById(R.id.list_view);
		csa = new CustomAdapter(this, lst);
		lv.setAdapter(csa);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				// TODO Auto-generated method stub
				if (lst.get(position).getText() == "Products") {
					Intent in = new Intent(GridActivity.this,
							MainActivity.class);
					startActivity(in);
				}

				if (lst.get(position).getText() == "Suppliers") {
					Intent in = new Intent(GridActivity.this, MainCompany.class);
					startActivity(in);
				}
				if (lst.get(position).getText() == "Sales Bills") {
					Intent in = new Intent(GridActivity.this,
							ListSalesBill.class);
					startActivity(in);
				}
				if (lst.get(position).getText() == "Purchase Bills") {
					Intent in = new Intent(GridActivity.this,
							ListPurchaseBill.class);
					startActivity(in);
				}
				if (lst.get(position).getText() == "Settings") {
					Intent in = new Intent(GridActivity.this, Settings.class);
					startActivity(in);
				}
				if (lst.get(position).getText() == "Report") {
					Intent in = new Intent(GridActivity.this,
							ReportActivity.class);
					startActivity(in);
				}
			}
		});
	}*/
}
