package com.player.boxplayer.view;

import android.app.ListActivity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class HoriScrollView extends LinearLayout {

	private Context context;
	private FilmAdapter adapter;
	public HoriScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
//		this.requestChildFocus(child, focused)
	}

	public void setAdapter (FilmAdapter adapter){
		this.adapter = adapter;
		for (int i = 0; i < adapter.getCount(); i++) {
			View view = adapter.getView(i, null, null);
			view.setPadding(20, 0, 20, 0);
//			view.setLayoutParams(new LinearLayout.LayoutParams(300, 400));
			
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.i("Contra", "HoriScrollView is click");
				}
			});
			
			view.setOnFocusChangeListener(new OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub
					Log.i("Contra", "onFocusChange is select");
				}
			});
			this.setGravity(Gravity.CENTER);
			this.addView(view);
		}
	}
	
}
