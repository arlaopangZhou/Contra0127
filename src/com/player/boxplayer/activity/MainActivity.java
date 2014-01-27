package com.player.boxplayer.activity;

import com.player.boxplayer.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		PreWorkerTask preWorker = new PreWorkerTask();
		preWorker.execute(new String[0]);
	}

	
	class PreWorkerTask extends AsyncTask<String, String, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			SystemClock.sleep(1000*5);
			return true;
		}
		
		protected void onPostExecute(Boolean loaded) {
			if (loaded) {
				Intent intent = new Intent(MainActivity.this, HomeActivity.class);
				
				MainActivity.this.startActivity(intent);
				MainActivity.this.finish();
			}
		}
	}

}
