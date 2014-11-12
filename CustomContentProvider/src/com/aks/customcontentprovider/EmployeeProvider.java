package com.aks.customcontentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class EmployeeProvider extends ContentProvider {
	
	//databse version
	public static final int DATABASE_VERSION = 1;
		
	//database name
	public static final String DATABASE_NAME = "EmployeeManager";
		
	//database table name
	public static final String TABLE_NAME = "Employee";
		
	//database coloms;
	public static final String EMP_ID = "id";
	public static final String EMP_NAME = "emp_name";
	public static final String EMP_PHONE = "emp_phone";
	
	private SQLiteDatabase sqlDB;
	
	public static final String AUTHORITY = "com.aks.customcontentprovider";
    
    public static final Uri CONTENT_URI = Uri.parse("content://com.aks.customcontentprovider");
	
	class DatabaseHelper extends SQLiteOpenHelper{

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			String CREATE_TABLE_STAT = "CREATE TABLE " + TABLE_NAME + "(" + 
					EMP_ID + " INTEGER PRIMARY KEY," +
					EMP_NAME + " TEXT," +
					EMP_PHONE + " TEXT)";
	
			db.execSQL(CREATE_TABLE_STAT);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
		
	}
	
	DatabaseHelper dbHelper;

	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();  
		int count;  
		count = db.delete(TABLE_NAME, where, whereArgs);  
		getContext().getContentResolver().notifyChange(uri, null);  
		return count;  
	}

	@Override
	public String getType(Uri arg0) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentValues) {
		sqlDB = dbHelper.getWritableDatabase();
        long rowId = sqlDB.insert(TABLE_NAME, "", contentValues);
        if (rowId > 0) {
            Uri rowUri = ContentUris.appendId(CONTENT_URI.buildUpon(), rowId).build();
            getContext().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DatabaseHelper(getContext());
        return (dbHelper == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		 
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		 
	    SQLiteDatabase db = dbHelper.getReadableDatabase();
	     
	    Cursor c = qb.query(db, projection, selection, selectionArgs , null, null, null);
	     
	    c.setNotificationUri(getContext().getContentResolver(), uri);
	    
	    return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();  
		int count;  
		count = db.update(TABLE_NAME, values, where, whereArgs);  
		getContext().getContentResolver().notifyChange(uri, null);  
		return count; 
	}

}
