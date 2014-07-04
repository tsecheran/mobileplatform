package com.example.simpletwitter;

import org.json.JSONObject;

import com.example.simpletwitter.fragments.UserTimeLineFragment;
import com.example.simpletwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
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
	
	private User user = null;
	private SimpleTwtrClient client = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterClientApp.getRestClient();
		setContentView(R.layout.activity_profile);
		bar = getActionBar(); // or MainActivity.getInstance().getActionBar()
        bar.setBackgroundDrawable(new ColorDrawable(0xff00DDED));
        bar.setDisplayShowTitleEnabled(false);  // required to force redraw, without, gray color
        bar.setDisplayShowTitleEnabled(true);
        
        if( getIntent().getSerializableExtra("userprofile") != null ) {
        	user = (User) getIntent().getSerializableExtra("userprofile");
        } 
        loadProfileInfo();
        displayFragment();
 	}
	
	public void loadProfileInfo() {
		if(user == null) {
			client.getMyInfo(userRespHandler);
		} else {
			client.getUserInfo(userRespHandler, user.getScreenName());
		}
	}
	
	public void setProfile(User user) {
		tvRealName = (TextView) findViewById(R.id.tvRealName);
		tvTagName = (TextView) findViewById(R.id.tvTagName);
		tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		profileIV = (ImageView) findViewById(R.id.ivUserProfile);
		tvRealName.setText(user.getName());
		tvTagName.setText(user.getTagLine());
		tvFollowers.setText(user.getFollowersCount() + "Followers");
		tvFollowing.setText(user.getFollowingCount() + " Following");
		ImageLoader.getInstance().displayImage(user.getImageUrl(), profileIV);
	}
	
	void displayFragment() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment fragment = UserTimeLineFragment.newInstance(user);
		ft.replace(R.id.flFragmentUserTL, fragment);
		//ft.addToBackStack(null);
		ft.commit();
	}
	
	JsonHttpResponseHandler userRespHandler = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject obj) {
			User user = User.fromJSON(obj);
			bar.setTitle("@" + user.getScreenName());
			setProfile(user);
		}
	};
}
