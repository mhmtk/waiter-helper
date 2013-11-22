package com.mhmt.waiterhelper.activities;

import com.example.waiterhelper.R;
import com.mhmt.waiterhelper.database.DatabaseManager;
import com.mhmt.waiterhelper.dataobjects.Patron;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewActivity extends Activity {
	
	private ListView patronListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		
		populateDbView();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view, menu);
		return true;
	}
	
	public void populateDbView(){
		
		//get the list view layout element
		patronListView = (ListView) findViewById(R.id.view_list);
		
		//get a database manager
		DatabaseManager dbManager = new DatabaseManager(getApplicationContext());
		
		//make adapter with the patron array from the manager
		ArrayAdapter<Patron> arrayAdapter = new ArrayAdapter<Patron>(this, 
				android.R.layout.simple_list_item_1, dbManager.getPatronsArray());

		//set adapter
		patronListView.setAdapter(arrayAdapter);
	}
}