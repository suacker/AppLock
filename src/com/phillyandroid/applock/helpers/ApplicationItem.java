package com.phillyandroid.applock.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.phillyandroid.applock.database.ProtectedApplicationsTable;

public class ApplicationItem {
	private String sTitle;
	private String sPackageName;
	private Drawable mImage;
	private Context mContext;
	
	private final static String TAG = "ApplicationItem";
	
	public String getTitle() {
		return sTitle;
	}
	
	public void setTitle(String title) {
		sTitle = title;
	}
	
	public String getPackageName() {
		return sPackageName;
	}
	
	public void setPackageName(String packageName) {
		sPackageName = packageName;
	}
	
	public Drawable getImage() {
		return mImage;
	}
	
	public void setImage(Drawable image) {
		mImage = image;
	}
	
	public Context getContext() {
		return mContext;
	}
	
	public void setContext(Context c) {
		mContext = c;
	}
	
	public void addValueToDatabase() {
		ProtectedApplicationsTable mTable = new ProtectedApplicationsTable(mContext);
		mTable.open();
		mTable.createRow(sTitle, sPackageName);
		mTable.close();
	}
	
	/**
	 * Default constructor
	 */
	public ApplicationItem() {
		
	}
	
	public ApplicationItem(Context context) {
		mContext = context;
	}
	
	public ApplicationItem(String title, String packageName, Drawable image, Context context) {
		sTitle = title;
		sPackageName = packageName;
		mImage = image;
		mContext = context;
	}
}
