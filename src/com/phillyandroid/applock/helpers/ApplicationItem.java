package com.phillyandroid.applock.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.phillyandroid.applock.database.ProtectedApplicationsTable;

public class ApplicationItem {
	private String title;
	private String packageName;
	private Drawable image;
	private Context context;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public Drawable getImage() {
		return image;
	}
	
	public void setImage(Drawable image) {
		this.image = image;
	}
	
	public Context getContext() {
		return this.context;
	}
	
	public void setContext(Context c) {
		this.context = c;
	}
	
	public void addValueToDatabase() {
		ProtectedApplicationsTable table = new ProtectedApplicationsTable(context);
		table.open();
		table.createRow(title, packageName);
		table.close();
	}
	
	/**
	 * Default constructor
	 */
	public ApplicationItem() {
		
	}
	
	public ApplicationItem(Context context) {
		this.context = context;
	}
	
	public ApplicationItem(String title, String packageName, Drawable image, Context context) {
		this.title = title;
		this.packageName = packageName;
		this.image = image;
		this.context = context;
	}
}
