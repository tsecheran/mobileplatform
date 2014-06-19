package com.example.googlesearchimage;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.googlesearchimage.SettingsDialog.SettingsDialogListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class SearchMainActivity extends FragmentActivity implements SettingsDialogListener {

	EditText searchTF;
	GridView gridViewResults;
	Button searchButton;
	ArrayList<ImageResult> results = new ArrayList<ImageResult>();
	ImageResultAdapter imgAdapter;
	AsyncHttpClient client = new AsyncHttpClient();
	String termEntered = null;
	FilterSettings filterSettings;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_settings, menu);
		return true;
	}

	public void onSettingsAction(MenuItem item) {
		FragmentManager fm = getSupportFragmentManager();
		SettingsDialog settingsDialog = SettingsDialog.newInstance("Settings Dialog");
		settingsDialog.show(fm, "settings_dialog");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_main);
		setUpViews();
		imgAdapter = new ImageResultAdapter(this, results);
		gridViewResults.setAdapter(imgAdapter);
		gridViewResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent fullImageIntent = new Intent(getApplicationContext(), 
						DisplayImageActivity.class);
				ImageResult result = results.get(position);
				fullImageIntent.putExtra("result", result);
				startActivity(fullImageIntent);
			}
			
		});
		
		gridViewResults.setOnScrollListener(new ResultsScrollViewListenter() {
			@Override
			public void loadMore(int page, int totalCount) {
				morePageLoad(page);
			}
		});
	}
	
	public void setUpViews() {
		searchTF = (EditText) findViewById(R.id.searchTF);
		gridViewResults = (GridView) findViewById(R.id.gridViewResults);
		searchButton = (Button) findViewById(R.id.searchButton);
	}
	
	@SuppressLint("NewApi")
	public void onSearchImages(View V) {
		if(filterSettings != null) {
			// Just unset the stored filters
			filterSettings = null;
		}
		termEntered = searchTF.getText().toString();
		if(termEntered == null || termEntered.isEmpty()) {
			Toast.makeText(this, "Enter a search term", Toast.LENGTH_SHORT).show();
			return;
		}
		
		StringBuilder urlBuilder = new StringBuilder("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&");
		urlBuilder.append("start=0").append("&v=1.0&q=").append(Uri.encode(termEntered));
		Toast.makeText(this, "Serching for images " + termEntered, Toast.LENGTH_SHORT).show();
		executeQuery(urlBuilder.toString(), true);
	}
	
	@SuppressLint("NewApi")
	public void morePageLoad(int pageIndex) {
		termEntered = searchTF.getText().toString();
		if(termEntered == null || termEntered.isEmpty()) {
			return;
		}
		StringBuilder urlBuilder = new StringBuilder("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&");
		urlBuilder.append("start=" + pageIndex).append("&v=1.0&q=").append(Uri.encode(termEntered));
		executeQuery(urlBuilder.toString(), false);
	}
	
	public void executeQuery(String url, final boolean clearFlag) {
		StringBuilder uriBuilder = new StringBuilder(url);
		if(filterSettings != null) {
			if(filterSettings.getImgSize() != null) {
				uriBuilder.append("&imgsz="+filterSettings.getImgSize());
			}
			if(filterSettings.getImgColor() != null) {
				uriBuilder.append("&imgcolor="+filterSettings.getImgColor());
			}
			if(filterSettings.getImgType() != null) {
				uriBuilder.append("&imgtype="+filterSettings.getImgType());
			}
		}
		
		client.get(uriBuilder.toString(), new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject response) {
				JSONArray imagesResults = null;
				try {
					imagesResults = response.getJSONObject("responseData")
							.getJSONArray("results");
					if(clearFlag) {
						results.clear();
					}
					//results.addAll(ImageResult.formJsonArray(imagesResults));
					imgAdapter.addAll(ImageResult.formJsonArray(imagesResults));
					//imgAdapter.notify();
					Log.d("DEBUG", "Image Results " + results.size());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressLint("NewApi")
	@Override
	public void onSettingsDialogFinished(FilterSettings settings) {
		//TODO: Just get the settings object and relaunch the search 
		Toast.makeText(this, "Appying Filter " + settings, Toast.LENGTH_SHORT).show();
		filterSettings = settings;
		termEntered = searchTF.getText().toString();
		if(termEntered == null || termEntered.isEmpty()) {
			return;
		}
		StringBuilder urlBuilder = new StringBuilder("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&");
		urlBuilder.append("start=0").append("&v=1.0&q=").append(Uri.encode(termEntered));
		executeQuery(urlBuilder.toString(), true);
	}
}
