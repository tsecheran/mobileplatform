package com.example.simpletwitter.fragments;

import com.example.simpletwitter.R;
import com.example.simpletwitter.R.id;
import com.example.simpletwitter.R.layout;
import com.example.simpletwitter.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetDialogFragment extends DialogFragment {

	public TweetDialogFragment() {}
	
	public static TweetDialogFragment newInstance() {
		TweetDialogFragment dialog = new TweetDialogFragment();
		return dialog;
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);

		  // request a window without the title
		  dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		  return dialog;
	}

	public static TweetDialogFragment newInstance(User userDetails) {
		TweetDialogFragment dialog = new TweetDialogFragment();
		Bundle args = new Bundle();
		args.putSerializable("userdetails", userDetails);
		dialog.setArguments(args);
		return dialog;
	}
	
	private Button tweetButton = null;
	private EditText tweetText = null;
	
	private TextView screenName = null;
	private TextView name = null;
	private ImageView userImage = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tweet_compose, container);
		screenName = (TextView) view.findViewById(R.id.tvUserScrName);
		name = (TextView) view.findViewById(R.id.tvUserRealName);
		userImage = (ImageView) view.findViewById(R.id.ivUserImage);
		screenName.setText("@tsecheran");
		name.setText("Cheran");
		tweetText = (EditText) view.findViewById(R.id.mltTweetEdit);
		tweetButton = (Button) view.findViewById(R.id.bTweet);
		tweetButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TweetDialogListener listener = (TweetDialogListener) getActivity();
				if(!tweetText.getText().toString().isEmpty()) {
					listener.onTweetDialogFinished(tweetText.getText().toString());
				}
				dismiss();
			}
		});
		getDialog().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		return view;
	}

	public interface TweetDialogListener {
		public void onTweetDialogFinished(String tweetBody);
	}
	
}
