package com.phillyandroid.applock.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;

import com.phillyandroid.applock.R;
import com.phillyandroid.applock.adapters.ListViewCustomAdapter;
import com.phillyandroid.applock.helpers.ApplicationItem;

public class AddApplicationActivity extends Activity implements OnClickListener {
	ListView lview3;
	ListViewCustomAdapter adapter;
	CheckBox cb;
	
	private ArrayList<Object> itemList;
	private ApplicationItem item;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.application_list);
		
		prepareArrayLists();
		
		Collections.sort(itemList, new Comparator<Object>() {

			@Override
			public int compare(Object appItem1, Object appItem2) {
				ApplicationItem item1 = (ApplicationItem) appItem1;
				ApplicationItem item2 = (ApplicationItem) appItem2;
				
				return item1.getTitle().compareToIgnoreCase(item2.getTitle());
			}
			
		});
		
		lview3 = (ListView)findViewById(R.id.listView1);
		adapter = new ListViewCustomAdapter(this, itemList);
		lview3.setAdapter(adapter);
		
		ImageButton imageButton2 = (ImageButton)findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(this);
	}

	private void prepareArrayLists() {
		itemList = new ArrayList<Object>();
		
		PackageManager pm = this.getPackageManager();
		
		Intent i = new Intent(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_LAUNCHER);
		
		ArrayList<ResolveInfo> list = (ArrayList<ResolveInfo>)pm.queryIntentActivities(i, PackageManager.PERMISSION_GRANTED);
		
		for(ResolveInfo resInfo : list) {
			AddObjectToList(resInfo.activityInfo.applicationInfo.loadIcon(pm), resInfo.activityInfo.applicationInfo.loadLabel(pm).toString(),resInfo.activityInfo.applicationInfo.packageName);
		}
	}

	private void AddObjectToList(Drawable image, String title, String packageName) {
		item = new ApplicationItem();
		item.setTitle(title);
		item.setPackageName(packageName);
		item.setImage(image);
		
		itemList.add(item);
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.imageButton2:
			/**
			 * Get all checked applications and send to next activity.
			 */
			Intent i = new Intent(this, AppLockSettingsActivity.class);
			startActivity(i);
		}
	}
}
