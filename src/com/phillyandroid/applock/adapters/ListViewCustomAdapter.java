package com.phillyandroid.applock.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.phillyandroid.applock.R;
import com.phillyandroid.applock.helpers.ApplicationItem;

public class ListViewCustomAdapter extends BaseAdapter {

	ArrayList<Object> itemList;
	
	public Activity context;
	public LayoutInflater inflater;
	
	public static class ViewHolder {
		ImageView imgViewLogo;
		TextView textViewTitle;
		CheckBox appCheckBox;
		TextView textHiddenAppPackage;
	}
	
	public ListViewCustomAdapter(Activity context,ArrayList<Object> itemList) {
		super();
		
		this.context = context;
		this.itemList = itemList;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		
		if(convertView == null) {
			holder = new ViewHolder();
			
			convertView = inflater.inflate(R.layout.application_listitem_row, null);
			
			holder.imgViewLogo = (ImageView)convertView.findViewById(R.id.imageView1);
			holder.textViewTitle = (TextView)convertView.findViewById(R.id.textView1);
			holder.textHiddenAppPackage = (TextView)convertView.findViewById(R.id.textHiddenAppPackage);
			holder.appCheckBox = (CheckBox)convertView.findViewById(R.id.checkBox1);
			
			holder.appCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked) {		
						ApplicationItem app_item = new ApplicationItem(context);
						app_item.setTitle(holder.textViewTitle.getText().toString());
						app_item.setPackageName(holder.textHiddenAppPackage.getText().toString());
						app_item.addValueToDatabase();
					}
				}
				
			});
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		ApplicationItem app_item = (ApplicationItem) itemList.get(position);
		
		holder.imgViewLogo.setImageDrawable(app_item.getImage());
		holder.textViewTitle.setText(app_item.getTitle());
		holder.textHiddenAppPackage.setText(app_item.getPackageName());
		
		return convertView;
	}
}
