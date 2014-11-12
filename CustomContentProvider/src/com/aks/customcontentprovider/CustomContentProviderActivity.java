package com.aks.customcontentprovider;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CustomContentProviderActivity extends Activity implements OnClickListener {

	EditText editTextName, editTextPhone;
	
	Button btnAdd, btnGetData, btnUpdate, btnDelete;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnGetData =  (Button) findViewById(R.id.btnGetAllData);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        
        btnAdd.setOnClickListener(this);
        btnGetData.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        
    }

	@Override
	public void onClick(View view) {
		
		if(view == btnAdd) {
			
			String name = editTextName.getText().toString();
			String phone = editTextPhone.getText().toString();
			
			ContentValues values = new ContentValues();
			values.put(EmployeeProvider.EMP_NAME, name);
			values.put(EmployeeProvider.EMP_PHONE, phone);
			
			getContentResolver().insert(EmployeeProvider.CONTENT_URI, values);
			
			
		}else if(view == btnGetData) {
			
			String proj[] = {EmployeeProvider.EMP_NAME, EmployeeProvider.EMP_PHONE};
			
			Cursor cursor = managedQuery(EmployeeProvider.CONTENT_URI, proj, null, null, null);
			
			if(cursor != null) {
				
				if(cursor.moveToFirst()) {
					do {
						String name = cursor.getString(0);
						String phone = cursor.getString(1);
						Log.i("Test", "*******NAME**************" + name);
						Log.i("Test", "*******PHONE*************" + phone);
						
					}while(cursor.moveToNext());
				}
			}
			
			cursor.close();
			
		}else if(view == btnUpdate) {
			
			String name = editTextName.getText().toString();
			String phone = editTextPhone.getText().toString();
			
			ContentValues values = new ContentValues();
			values.put(EmployeeProvider.EMP_NAME, name);
			values.put(EmployeeProvider.EMP_PHONE, phone);
		
			getContentResolver().update(EmployeeProvider.CONTENT_URI, values, "id = ?", new String[]{1 + ""});
			
		}else if(view == btnDelete) {
			
			getContentResolver().delete(EmployeeProvider.CONTENT_URI, "id = ?", new String[]{1 + ""});
			
		}
		
	}

}