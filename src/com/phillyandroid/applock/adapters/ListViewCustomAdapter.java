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
	private final static String TAG = "ListViewCustomAdapter";
	
	private ArrayList<Object> mItemList;
	private Activity mContext;
	private LayoutInflater mInflater;
	
	public static class ViewHolder {
		private ImageView mImgViewHolder;
		private TextView mTextViewTitle;
		private CheckBox mAppCheckBox;
		private TextView mTextViewHiddenPackageName;
	}
	
	public ListViewCustomAdapter(Activity context,ArrayList<Object> itemList) {
		super();
		
		mContext = context;
		mItemList = itemList;
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return mItemList.size();
	}

	@Override
	public Object getItem(int position) {
		return mItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder mHolder;
		
		if(convertView == null) {
			mHolder = new ViewHolder();
			
			convertView = mInflater.inflate(R.layout.application_listitem_row, null);
			
			mHolder.mImgViewHolder = (ImageView)convertView.findViewById(R.id.application_image);
			mHolder.mTextViewTitle = (TextView)convertView.findViewById(R.id.application_name);
			mHolder.mTextViewHiddenPackageName = (TextView)convertView.findViewById(R.id.text_hidden_package_name);
			mHolder.mAppCheckBox = (CheckBox)convertView.findViewById(R.id.application_checkbox);
			
			mHolder.mAppCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked) {		
						ApplicationItem app_item = new ApplicationItem(mContext);
						
						app_item.setTitle(mHolder.mTextViewTitle.getText().toString());
						app_item.setPackageName(mHolder.mTextViewHiddenPackageName.getText().toString());
						app_item.addValueToDatabase();
					}
				}
				
			});
			
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder)convertView.getTag();
		}
		
		ApplicationItem app_item = (ApplicationItem) mItemList.get(position);
		
		mHolder.mImgViewHolder.setImageDrawable(app_item.getImage());
		mHolder.mTextViewTitle.setText(app_item.getTitle());
		mHolder.mTextViewHiddenPackageName.setText(app_item.getPackageName());
		
		return convertView;
	}
}
