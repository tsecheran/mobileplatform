package com.example.googlesearchimage;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.database.DataSetObserver;
import android.support.v4.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class SettingsDialog extends DialogFragment {
	public SettingsDialog() {}
	//private static SettingsDialog dialog;
	public static SettingsDialog newInstance(String title) {
		//if(dialog != null) {
		SettingsDialog dialog = new SettingsDialog();
		//}
		Bundle args = new Bundle();
		args.putString("title", title);
		dialog.setArguments(args);
		return dialog;
	}
	
	public static SettingsDialog newInstance(String title, FilterSettings settings){
		SettingsDialog dialog = newInstance(title);
		Bundle args = new Bundle();
		args.putString("title", title);
		args.putSerializable("settings", settings);
		dialog.setArguments(args);
		return dialog; 
	}
	
	Button submitButton;
	FilterSettings settings;
	Spinner imageSizeSp;
	Spinner imageColorSp;
	Spinner imageTypeSp;
	ArrayAdapter<CharSequence> sizeSpAdapter;
	ArrayAdapter<CharSequence> colorAdapter;
	ArrayAdapter<CharSequence> typepAdapter;
	FilterSettings filterSettings;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_view, container);
		String title = getArguments().getString("title");
		if(title != null) {
			getDialog().setTitle(title);
		}
		setUpSpinners(view);
		submitButton = (Button) view.findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SettingsDialogListener listener = (SettingsDialogListener) getActivity();
				//TODO: Pass the new FilterSettings Object
				//filterSettings = new FilterSettings();
				String size = (String) imageSizeSp.getSelectedItem();
				String color = (String) imageColorSp.getSelectedItem();
				String type = (String) imageTypeSp.getSelectedItem();
				listener.onSettingsDialogFinished(new FilterSettings(size, color, type, null));
				dismiss();
			}
		});
		getDialog().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		return view;
	}
	
	public void setUpSpinners(View view) {
		imageSizeSp = (Spinner) view.findViewById(R.id.imgSizeSP);
		/**imageSizeSp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});**/
		sizeSpAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
				R.array.image_size_array,
				R.layout.spinner_item);
				//android.R.layout.simple_spinner_item);
		//sizeSpAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
		//sizeSpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		imageSizeSp.setAdapter(sizeSpAdapter);
		
		imageColorSp = (Spinner) view.findViewById(R.id.imgColorSP);
		colorAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.image_color_array,
				R.layout.spinner_item);
		imageColorSp.setAdapter(colorAdapter);
		
		imageTypeSp = (Spinner) view.findViewById(R.id.imgTypeSP);
		typepAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.image_type_array,
				R.layout.spinner_item);
		imageTypeSp.setAdapter(typepAdapter);
	}
	
	public interface SettingsDialogListener {
		public void onSettingsDialogFinished(FilterSettings settings);
	}
	
}
