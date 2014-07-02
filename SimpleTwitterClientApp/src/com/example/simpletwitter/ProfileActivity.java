package com.example.simpletwitter;

import org.json.JSONObject;

import com.example.simpletwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends FragmentActivity {
	ActionBar bar = null;
	
	TextView tvRealName = null;
	TextView tvTagName = null;
	TextView tvFollowers = null;
	TextView tvFollowing = null;
	ImageView profileIV = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		bar = getActionBar(); // or MainActivity.getInstance().getActionBar()
        bar.setBackgroundDrawable(new ColorDrawable(0xff00DDED));
        bar.setDisplayShowTitleEnabled(false);  // required to force redraw, without, gray color
        bar.setDisplayShowTitleEnabled(true);
		loadProfileInfo();
	}
	
	public void loadProfileInfo() {
		TwitterClientApp.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject obj) {
				User user = User.fromJSON(obj);
				bar.setTitle("@" + user.getScreenName());
			}
		});
	}
	
	public void setProfile(User user) {
		tvRealName = (TextView) findViewById(R.id.tvRealName);
		tvTagName = (TextView) findViewById(R.id.tvTagName);
		tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		profileIV = (ImageView) findViewById(R.id.ivUserProfile);
		tvRealName.setText(user.getName());
		tvTagName.setText(user.getTagLine());
		tvFollowing.setText(user.getFollowingCount() + " Following");
		ImageLoader.getInstance().displayImage(user.getImageUrl(), profileIV);
	}
}
