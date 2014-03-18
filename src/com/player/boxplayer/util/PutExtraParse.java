package com.player.boxplayer.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import com.player.boxplayer.tile.BundleTile;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public enum PutExtraParse {

	SINGLE;
	
	public static String KEYTYPE;
	
	public String getKeyType(int type){
		switch (type) {
		case 0:
			return "string";
		case 1:
			return "int";
		case 2:
			return "boolean";
		case 3:
			return "float";
		case 4:
			return "char";
		}
		return null;
	}
	
	public Bundle getBundleData(Context context,String bundlePath){
		List<BundleTile> bundleTiles = null;
		try {
			File configFile = new File(context.getExternalFilesDir(null), bundlePath);
			FileInputStream videoStream = new FileInputStream(configFile);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			PutExtraXmlHandler saxHandler = new PutExtraXmlHandler();
			parser.parse(videoStream, saxHandler);
			bundleTiles = saxHandler.getBundleDataInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Bundle bundle = new Bundle();
		if (bundleTiles.size() == 0) {
			Log.i("Contra",
					"bundleTiles ====  null!!!!!" );
			return null;
		}
		
		for (int i = 0; i < bundleTiles.size()-1; i++) {
			String bundleKey = bundleTiles.get(i).getBundleKey();
			String valueType = bundleTiles.get(i).getValueType();
			String valueString = bundleTiles.get(i).getValue();
			getString(bundle,valueType,bundleKey,valueString);
		}
		return bundle;
	}
	
	
	/**
	 * 功能：用于获取数据添加到bundle中。
	 * @param bundle bundle对象
	 * @param valueType bundle的值的️类型名称
	 * @param bundleKey  bundle的键的名称
	 * @param valueString bundle的值
	 */
	public void getString(Bundle bundle,String valueType,String bundleKey,String valueString){
		if ("string".equals(valueType)) {
			bundle.putString(bundleKey, valueString);
		} else if("int".equals(valueType)){
			int extraI = Integer.valueOf(valueString);
			bundle.putInt(bundleKey, extraI);
		}else if ("float".equals(valueType)) {
			float extraF = Float.valueOf(valueString);
			bundle.putFloat(bundleKey, extraF);
		}else if ("boolean".equals(valueType)) {
			boolean extraB = Boolean.valueOf(valueString);
			bundle.putBoolean(bundleKey, extraB);
		}else if ("char".equals(valueType)) {
			char extraC = valueString.charAt(0);
			bundle.putChar(bundleKey, extraC);
		}
	}
	
	/**
	 * 功能：用于获取extra的值，根据值的数字类型代号进行转化，返回相应类型的值。
	 * @param type extra值类型
	 * @param typeString extra的值
	 * @return
	 */
	public Object getExtraString(int type,String typeString){
		switch (type) {
		case 0:
			return typeString;
		case 1:
			int extraI = Integer.valueOf(typeString);
			return extraI;
		case 2:
			boolean extraB = Boolean.valueOf(typeString);
			return extraB;
		case 3:
			float extraF = Float.valueOf(typeString);
			return extraF;
		case 4:
			char extraC = typeString.charAt(0);
			return extraC;
		}
		return null;
	}
}
