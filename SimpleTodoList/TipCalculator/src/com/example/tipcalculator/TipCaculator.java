package com.example.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class TipCaculator extends Activity {

	private SeekBar skBar = null;
	private TextView perCentValueTF = null;
	private EditText billAmtTF= null;
	private EditText tipAmtTF = null;
	private EditText totalAmtTF = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_caculator);
		perCentValueTF = (TextView) findViewById(R.id.tipPercTF);
		skBar = (SeekBar) findViewById(R.id.tipChooserSB);
		billAmtTF = (EditText) findViewById(R.id.billAmtTF);
		tipAmtTF = (EditText) findViewById(R.id.tipValueTF);
		totalAmtTF = (EditText) findViewById(R.id.totalValueTF);
		skBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				perCentValueTF.setText(String.format("%d",progress) + "%");
				setValues();
			}
		});
	}
	
	public void calculateTipAmt(View v) {
		//skBar = (SeekBar) findViewById(R.id.tipChooserSB);
		setValues();
	}
	
	public void setValues() {
		int perCentVal = skBar.getProgress();
		float billAmt = Float.valueOf(billAmtTF.getText().toString());
		if(billAmt > 0 && perCentVal > 0) {
			float tip = calculateTipAmount(billAmt, perCentVal);
			tipAmtTF.setText(String.format("%.2f",tip));
			float total = billAmt + tip;
			totalAmtTF.setText(String.format("%.2f", total));
		} else {
			tipAmtTF.setText(String.format("%.2f", 0.0));
			totalAmtTF.setText(String.format("%.2f", billAmt));
		}
	}
	
	public float calculateTipAmount(float billAmt, int percent) {
		return billAmt/(100/percent);
	}
}
