package com.example.googlesearchimage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult implements Serializable{
	private static final long serialVersionUID = -1312685710220327621L;
	private String fullUrl;
	private String thumbUrl;
	
	public ImageResult(JSONObject json){
		try {
			fullUrl = json.getString("url");
			thumbUrl = json.getString("tbUrl");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getFullUrl() {
		return fullUrl;
	}
	public String getThumbUrl() {
		return thumbUrl;
	}
	
	public String toString() {
		return thumbUrl;
	}
	
	public static List<ImageResult> formJsonArray(JSONArray array) {
		List<ImageResult> results = new ArrayList<ImageResult>();
		for(int i =0; i < array.length(); i++) {
			try {
				results.add(new ImageResult(array.getJSONObject(i)));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return results;
	}
}
