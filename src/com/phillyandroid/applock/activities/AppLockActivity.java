package com.phillyandroid.applock.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.phillyandroid.applock.R;

public class AppLockActivity extends Activity implements OnClickListener {
	private final static String TAG = "AddLockActivity";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ImageButton mButtonAddApplication = (ImageButton)findViewById(R.id.button_add_application);
        mButtonAddApplication.setOnClickListener(this);
    }

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.button_add_application:
			Intent i = new Intent(this, AddApplicationActivity.class);
			startActivity(i);
		}
		
	}
}