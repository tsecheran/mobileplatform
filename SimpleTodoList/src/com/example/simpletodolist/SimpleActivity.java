package com.example.simpletodolist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class SimpleActivity extends Activity {
	List<String> todoList = new ArrayList<String>();
	ListView listView1;
	ArrayAdapter<String> itemsAdapter;
	// Stores the position selected for editing
	int selectedPosition = 0;
	public void launchEditView(String inputText) {
		Intent i = new Intent(this, EditItemActivity.class);
		i.putExtra("editingText", inputText);
		//startActivity(i);
		startActivityForResult(i, 200);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == 100 && requestCode == 200) {
			// This is a edited text
			todoList.remove(selectedPosition);
			// Then put the edited text into that
			todoList.add(selectedPosition, data.getStringExtra("editedText"));
			selectedPosition = 0;
			itemsAdapter.notifyDataSetChanged();
			writeFile();
		}
		// TODO in case if it's delete, remove that item
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple);
		listView1 = (ListView) findViewById(R.id.listView1);
		readFile();
		itemsAdapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, 
				todoList);
		listView1.setAdapter(itemsAdapter);
		//todoList.add("First Thing");
		//todoList.add("Second Thing");
		setUpItemsListener();
	}
	
	public void addTodoList(View v) {
		EditText text = (EditText) findViewById(R.id.tf);
		String str = text.getText().toString();
		if(!str.isEmpty()) {
			itemsAdapter.add(str);
			text.setText("");
			writeFile();
		}
	}
	
	private void setUpItemsListener() {
		listView1.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				todoList.remove(position);
				itemsAdapter.notifyDataSetChanged();
				// Ideally write only once when the app sleeps or getting closed, 
				//TODO find out that on app close something
				writeFile();
				return true;
			}
		});
		
		listView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectedPosition = position;
				launchEditView(todoList.get(position));
			}
		});
	}
	
	// TODO Find out how exactly use those external libraries in the eclipse
	// and use FileUtils
	private void readFile() {
		File fileDir = getFilesDir();
		File todoListFile = new File(fileDir, "todo.txt");
		BufferedReader reader = null;
		try {
			reader = 
					new BufferedReader(new InputStreamReader(
							new FileInputStream(todoListFile)));
			String line;
			while( (line = reader.readLine()) != null) {
				todoList.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(reader != null) reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void writeFile() {
		File fileDir = getFilesDir();
		File todoListFile = new File(fileDir, "todo.txt");
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(todoListFile)));
			for(String todoItem: todoList) {
				writer.write(todoItem);
				writer.newLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(writer != null) writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
