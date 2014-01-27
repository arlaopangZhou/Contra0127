package com.player.boxplayer.util;

import android.os.AsyncTask;
//import android.util.Log;

public class HttpWorkTask<T> extends AsyncTask<Void, Void, T> {
	private ParseCallBack<T> parseCallBack;
	private PostCallBack<T> postCallBack;

	public HttpWorkTask(ParseCallBack<T> paramParseCallBack,
			PostCallBack<T> paramPostCallBack) {
		parseCallBack = paramParseCallBack;
		postCallBack = paramPostCallBack;
	}

	@Override
	protected T doInBackground(Void... params) {
		if (parseCallBack != null) {
			
			T ret = parseCallBack.onParse();
			//Log.e("test", "return in doInBackground" + ret.toString());
			return ret;
		}

		//Log.e("test", "return in doInBackground null");
		return null;
	}

	@Override
	protected void onPostExecute(T paramT) {
		if (postCallBack != null)
			postCallBack.onPost(paramT);
	}

	public static abstract interface ParseCallBack<T> {
		public abstract T onParse();
	}

	public static abstract interface PostCallBack<T> {
		public abstract void onPost(T paramT);
	}
}
