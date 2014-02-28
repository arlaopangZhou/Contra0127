package com.player.boxplayer.view;

import java.util.ArrayList;
import java.util.List;

import com.player.boxplayer.R;
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
	
	public FilmAdapter(Context context) {
		super();
		// TODO Auto-generated constructor stub
		reTileList = new ArrayList<RemmondFilmTitle>();
		this.context = context;
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

	public void addObject (RemmondFilmTitle object){
		reTileList.add(object);
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stubs
		String imagePath = context.getExternalFilesDir(null)+"/"+reTileList.get(position).getImageUrl();
		Log.i("Contra", " imagePath =="+imagePath);
		String filmName = reTileList.get(position).getTitle();
		LayoutInflater inflater = LayoutInflater.from(context);
		View layout = inflater.inflate(R.layout.recemmend_item, null);
		Holder hold = new Holder();
		hold.img = (ImageView) layout.findViewById(R.id.image_film);
		hold.txt = (TextView) layout.findViewById(R.id.film_name);
		hold.img.setImageBitmap(ScaleBitmapTool.getImageView(imagePath));
		hold.txt.setText(filmName);
//		hold.img.setOnFocusChangeListener(this);
//		LinearLayout linearLayout = new LinearLayout(getApplicationContext());
		layout.setLayoutParams(new LayoutParams(300, 400));
		((LinearLayout) layout).setGravity(Gravity.CENTER);
		return layout;
	}

	class Holder {
		private View v;
		private ImageView img;
		private TextView txt;
	}
	
}
