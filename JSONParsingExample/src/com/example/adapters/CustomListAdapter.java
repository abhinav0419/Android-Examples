package com.example.adapters;

import java.util.ArrayList;
import java.util.List;

import com.example.bean.JSONBean;
import com.example.jsonparsingexample.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<JSONBean>{

	Context context;
	ArrayList<JSONBean> objects;
	
	LayoutInflater inflater;
	
	public CustomListAdapter(Context context, int textViewResourceId,
			ArrayList<JSONBean> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		
		this.context = context;
		this.objects = objects;
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public JSONBean getItem(int position) {
		// TODO Auto-generated method stub
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return objects.get(position).getId();
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		
		View convertView = inflater.inflate(R.layout.list_row, parent, false);
		
		TextView txt_view_name = (TextView) convertView.findViewById(R.id.textView1);
		TextView txt_view_email = (TextView) convertView.findViewById(R.id.textView2);
		TextView txt_view_mobile = (TextView) convertView.findViewById(R.id.textView3);
		
		txt_view_name.setText(objects.get(position).getName());
		txt_view_email.setText(objects.get(position).getEmail());
		txt_view_mobile.setText("Mobile: " + objects.get(position).getMobile());
		
		return convertView;
	}
	
	
	

}
