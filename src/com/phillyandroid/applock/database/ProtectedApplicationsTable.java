package com.phillyandroid.applock.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ProtectedApplicationsTable {
	private final static String TAG = "ProtectedApplicationsTable";
	
	private static final String DATABASE_NAME = "ProtectedApplicationsDatabase";
	private static final String DATABASE_TABLE = "ProtectedApplications";
	private static final int DATABASE_VERSION = 1;
	
	public static final String COL_ID = "id";
	public static final String COL_APPNAME = "application_name";
	public static final String COL_PACKAGE = "application_package";
	
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDatabase;
	
	private static final String DB_CREATE = "CREATE TABLE " + DATABASE_TABLE + 
			" (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COL_APPNAME + " TEXT NOT NULL, " + COL_PACKAGE + " TEXT NOT NULL);";
	
	private final Context mContext;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DB_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}
	
	public ProtectedApplicationsTable(Context context) {
		mContext = context;
	}
	
	public ProtectedApplicationsTable open() throws SQLException {
		mDbHelper = new DatabaseHelper(mContext);
		mDatabase = mDbHelper.getReadableDatabase();
		
		return this;
	}
	
	public void close() {
		mDbHelper.close();
		mDatabase.close();
	}
	
	public long createRow(String applicationName, String applicationPackage) {
		
		long insertId;
		ArrayList<String> rows = new ArrayList<String>();
		Cursor c = mDatabase.query(DATABASE_TABLE, new String[] {COL_ID}, COL_PACKAGE + " = '" + applicationPackage + "'", null, null, null, null);
		
		c.moveToFirst();
		while(c.isAfterLast() == false) {
			rows.add(c.getString(0));
			c.moveToNext();
		}
		
		c.close();
		
		if(rows.size() <= 0) {
			ContentValues initialValues = new ContentValues();
			
			initialValues.put(COL_APPNAME, applicationName);
			initialValues.put(COL_PACKAGE, applicationPackage);
			
			insertId = mDatabase.insert(DATABASE_TABLE, null, initialValues);
		} else {
			insertId = 0;
			Log.d("ProtectedApplicationsTable", applicationPackage + " already found in mDatabase; not adding.");
		}
		
		return insertId;
	}
	
	public boolean deleteRow(long rowId) {
		return mDatabase.delete(DATABASE_TABLE, COL_ID + " = " + rowId , null) > 0;
	}
	
	public Cursor fetchAllRows() {
		return mDatabase.query(DATABASE_TABLE, new String[] {COL_ID, COL_APPNAME, COL_PACKAGE}, null, null, null, null, null);
	}
	
	public Cursor fetchRowById(long rowId) throws SQLException {
		return null;
	}
}
