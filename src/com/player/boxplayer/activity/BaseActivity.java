package com.player.boxplayer.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;
//import com.player.boxplayer.R;


public class BaseActivity extends Activity {
	public boolean isRunning = false;
	private NumKeyClickListener listener = null;
	private boolean numWaiting = false;
	private LoadingDialog progressDialog;

	public WindowManager wm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LOW_PROFILE);
		isRunning = true;
		progressDialog=new LoadingDialog(this);
	}

	public void progressShow() {
		if (isRunning() && !progressDialog.isShowing()) {
			progressDialog.show();
		}
	}

	public void progressDismiss() {
		if (isRunning() && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		isRunning = false;
	}

	protected boolean isRunning() {
		return isRunning;
	}

	StringBuilder sb = new StringBuilder();

	private int MSG_NUM_END = 0;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == MSG_NUM_END) {
				if (!TextUtils.isEmpty(sb.toString())) {
					int num = Integer.parseInt(sb.toString());
					listener.multeKeyDown(num);
				}
				sb = new StringBuilder();
				numWaiting = false;
			}
		}

	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		System.out.println("----------------------keyCode = " + keyCode);
		boolean b = keyCode == KeyEvent.KEYCODE_0
				|| keyCode == KeyEvent.KEYCODE_1
				|| keyCode == KeyEvent.KEYCODE_2
				|| keyCode == KeyEvent.KEYCODE_3
				|| keyCode == KeyEvent.KEYCODE_4
				|| keyCode == KeyEvent.KEYCODE_5
				|| keyCode == KeyEvent.KEYCODE_6
				|| keyCode == KeyEvent.KEYCODE_7
				|| keyCode == KeyEvent.KEYCODE_8
				|| keyCode == KeyEvent.KEYCODE_9;
		if (sb.length() > 4) {
			return false;
		}
		if (b && listener != null) {
			numWaiting = true;
			switch (keyCode) {
			case KeyEvent.KEYCODE_0:
				sb.append(0);
				break;
			case KeyEvent.KEYCODE_1:
				sb.append(1);
				break;
			case KeyEvent.KEYCODE_2:
				sb.append(2);
				break;
			case KeyEvent.KEYCODE_3:
				sb.append(3);
				break;
			case KeyEvent.KEYCODE_4:
				sb.append(4);
				break;
			case KeyEvent.KEYCODE_5:
				sb.append(5);
				break;
			case KeyEvent.KEYCODE_6:
				sb.append(6);
				break;
			case KeyEvent.KEYCODE_7:
				sb.append(7);
				break;
			case KeyEvent.KEYCODE_8:
				sb.append(8);
				break;
			case KeyEvent.KEYCODE_9:
				sb.append(9);
				break;
			}
			if (!TextUtils.isEmpty(sb.toString())) {
				int num = Integer.parseInt(sb.toString());
				listener.singleKeyDown(num);
			}
			if (numWaiting) {
				handler.removeMessages(MSG_NUM_END);
				handler.sendEmptyMessageDelayed(MSG_NUM_END, 2000);
			}
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			return super.onKeyDown(keyCode, event);
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			return super.onKeyDown(keyCode, event);
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			return super.onKeyDown(keyCode, event);
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			return super.onKeyDown(keyCode, event);
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			return super.onKeyDown(keyCode, event);
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			return super.onKeyDown(keyCode, event);
		}
		return super.onKeyDown(keyCode, event);
	}

	public void setOnNumKeyClickListener(NumKeyClickListener listener) {
		this.listener = listener;
	}

	public interface NumKeyClickListener {
		void singleKeyDown(int num);

		void multeKeyDown(int num);
	}
}
