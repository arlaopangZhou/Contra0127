package com.player.boxplayer.view;

import android.app.Activity;
import android.content.Intent;
//import android.content.Intent;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
//import android.view.View.OnFocusChangeListener;
//import android.widget.Button;
//import android.widget.LinearLayout;
import android.widget.TextView;

import com.player.boxplayer.R;

public class SubPage extends Activity implements OnClickListener {
	//private TextView version;
	TextView pageTitle;
	TextView pageText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub_page);
		pageTitle = (TextView) findViewById(R.id.sub_page_title);
		pageText = (TextView) findViewById(R.id.sub_page_text);

		Intent data = getIntent();
		String strTitle = data.getExtras().getString("subTitle");
		String strText = data.getExtras().getString("intent");
		pageTitle.setText(strTitle);
		pageText.setText(strText);
		
		//pageTitle.setText("test");
		//pageText.setText("Hell");
		//Button update = (Button) findViewById(R.id.set_check_update);
		//update.setOnClickListener(this);
	}
	/*
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			String strTitle = data.getExtras().getString("subTitle");
			String strText = data.getExtras().getString("subText");
			pageTitle.setText(strTitle);
			pageText.setText(strText);
		}
	}*/
/*
	private String getAPKVersionName() {
		PackageManager packageManager = this.getPackageManager();
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packInfo.versionName;
	}
*/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			//overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {

	}

}
