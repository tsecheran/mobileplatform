package com.example.simpletodolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {

	EditText editText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		editText = (EditText) findViewById(R.id.editText1);
		String editingText = getIntent().getStringExtra("editingText");
		editText.setText(editingText);
	}
	
	public void saveEditedItem(View v) {
		// Now just handle the Edited Item
		Intent i = new Intent();
		i.putExtra("editedText", editText.getText().toString());
		//TODO In case if the user deletes complete text, just 
		// set a different result code and use that delete the item
		this.setResult(100, i);
		this.finish();
	}
}
