package com.phillyandroid.applock.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.phillyandroid.applock.R;
import com.phillyandroid.applock.adapters.ListViewCustomAdapter;
import com.phillyandroid.applock.helpers.ApplicationItem;

public class AddApplicationActivity extends Activity implements OnClickListener {
	private final static String TAG = "AddApplicationActivity";
	
	private ListView mApplicationList;
	private ListViewCustomAdapter mAdapter;
	private ArrayList<Object> mItemList;
	private ApplicationItem mApplicationItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.application_list);
		
		ProgressDialog mProgressDialog = ProgressDialog.show(AddApplicationActivity.this,"",getString(R.string.loading_application_list));
		
		prepareArrayLists();
		
		Collections.sort(mItemList, new Comparator<Object>() {

			@Override
			public int compare(Object appItem1, Object appItem2) {
				final ApplicationItem applicationItem1 = (ApplicationItem) appItem1;
				final ApplicationItem applicationItem2 = (ApplicationItem) appItem2;
				
				return applicationItem1.getTitle().compareToIgnoreCase(applicationItem2.getTitle());
			}
			
		});
		
		Set<Object> mItemSet = new LinkedHashSet<Object>(mItemList);
		
		mItemList.clear();
		mItemList.addAll(mItemSet);
		
		mApplicationList = (ListView)findViewById(R.id.application_list);
		mAdapter = new ListViewCustomAdapter(this, mItemList);
		mApplicationList.setAdapter(mAdapter);
		
		mProgressDialog.dismiss();
		
		ImageButton mButtonSettings = (ImageButton)findViewById(R.id.button_settings);
        mButtonSettings.setOnClickListener(this);
	}

	private void prepareArrayLists() {
		mItemList = new ArrayList<Object>();
		
		PackageManager pm = this.getPackageManager();
		
		Intent i = new Intent(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_LAUNCHER);
		
		ArrayList<ResolveInfo> mList = (ArrayList<ResolveInfo>)pm.queryIntentActivities(i, PackageManager.PERMISSION_GRANTED);
		
		for(ResolveInfo resInfo : mList) {
			AddObjectToList(resInfo.activityInfo.applicationInfo.loadIcon(pm), resInfo.activityInfo.applicationInfo.loadLabel(pm).toString(),resInfo.activityInfo.applicationInfo.packageName);
		}
	}

	private void AddObjectToList(Drawable image, String title, String packageName) {
		mApplicationItem = new ApplicationItem();
		mApplicationItem.setTitle(title);
		mApplicationItem.setPackageName(packageName);
		mApplicationItem.setImage(image);
		
		mItemList.add(mApplicationItem);
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.button_settings:
			/**
			 * Get all checked applications and send to next activity.
			 */
			Intent i = new Intent(this, AppLockSettingsActivity.class);
			startActivity(i);
		}
	}
}
