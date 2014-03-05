package com.player.boxplayer.view;

import java.util.ArrayList;
import java.util.List;

import com.player.boxplayer.R;
import com.player.boxplayer.activity.RecommendFilmActivity;
import com.player.boxplayer.tile.RemmondFilmTitle;
import com.player.boxplayer.util.ScaleBitmapTool;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FilmAdapter extends BaseAdapter {

	private Context context;
	private List<RemmondFilmTitle> reTileList;
	private int selectItem;
	public FilmAdapter(Context context,List<RemmondFilmTitle> reTileList) {
		super();
		// TODO Auto-generated constructor stub
		this.context = context;
		this.reTileList = reTileList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return reTileList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return reTileList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * 功能：设置焦点的选中的坐标。用于判断当前焦点获取的图片是否和getView里的Position一致。
	 * 该方法是用于对焦点选中的图片做一些处理。
	 * @param selectItem 获取焦点的视图坐标。
	 */
	public void setSelect(int selectItem){
		if (this.selectItem != selectItem) {
			this.selectItem = selectItem;
			notifyDataSetChanged();
		}
	}
	
	@SuppressWarnings("null")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stubs
		String imagePath = context.getExternalFilesDir(null)+"/"+reTileList.get(position).getImageUrl();
		Log.i("Contra", " imagePath =="+imagePath);
		String filmName = reTileList.get(position).getTitle();
		LayoutInflater inflater = LayoutInflater.from(context);
		Holder hold;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.recemmend_item, null);
			hold = new Holder();
			hold.img = (ImageView) convertView.findViewById(R.id.image_film);
//			hold.txt = (TextView) convertView.findViewById(R.id.film_name);
			convertView.setTag(hold);
		} else {
			hold = (Holder) convertView.getTag();
		}
		hold.img.setImageBitmap(ScaleBitmapTool.getImageView(imagePath));
		
		if (selectItem == position) {
			convertView.setBackgroundResource(R.drawable.white_border);
		} else {
			convertView.setBackgroundResource(R.drawable.white_border2);
		}
		
		return convertView;
	}

	class Holder {
		private View v;
		private ImageView img;
		private TextView txt;
	}
	
}
