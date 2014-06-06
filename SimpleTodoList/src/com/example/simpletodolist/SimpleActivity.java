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
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class SimpleActivity extends Activity {
	List<String> todoList = new ArrayList<String>();
	ListView listView1;
	ArrayAdapter<String> itemsAdapter;

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
				writeFile();
				return true;
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
