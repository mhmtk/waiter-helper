package com.mhmt.waiterhelper.activities;

import com.example.waiterhelper.R;
import com.mhmt.waiterhelper.database.DatabaseManager;
import com.mhmt.waiterhelper.dataobjects.Patron;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Activity that is launched when view is clicked on the homepage
 * displays the currently loaded database
 * 
 * @author Mehmet Kologlu
 * @version November 26, 2011
 *
 */
public class ViewActivity extends Activity {
	
	//layout variable declaration
	private ListView patronListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		
		//populate the list view with the database data
		populateDbView();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view, menu);
		return true;
	}
	
	/**
	 * populates the List View with the database data
	 */
	public void populateDbView(){
		
		//get the list view layout element
		patronListView = (ListView) findViewById(R.id.view_list);
		
		//get a database manager
		DatabaseManager dbManager = new DatabaseManager(getApplicationContext());
		
		if(dbManager.getPatronsArray().isEmpty()) //if the loaded database is empty
		{
			//error toast
			Toast.makeText(getApplicationContext(), "Database empty! Load one or enter info!", Toast.LENGTH_SHORT).show();
		}
		else //make adapter with the patron array from the manager
		{
		ArrayAdapter<Patron> arrayAdapter = new ArrayAdapter<Patron>(this, 
				android.R.layout.simple_list_item_1, dbManager.getPatronsArray());
		//set adapter
		patronListView.setAdapter(arrayAdapter);
		}
	}
}