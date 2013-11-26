package com.mhmt.waiterhelper.activities;


import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.waiterhelper.R;
import com.mhmt.waiterhelper.database.WaiterHelperDbHelper;
import com.mhmt.waiterhelper.database.WaiterHelperContract.OrderEntry;

/*
 * The activity launched when "Enter Orders" is clicked on the home page.
 * 
 * @author Mehmet Kologlu
 * @version November 18, 2013
 */
public class EnterPatronsActivity extends Activity {

	//initialize the database helper
	WaiterHelperDbHelper mDbHelper;
	//initialize the database
	SQLiteDatabase db;
	
	//entry variables
	private String tableNoString;
	private String seatNoString;
	private String orderString;
	
	//layout variable
	private EditText seatNoEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_patrons);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//define the helper
		mDbHelper = new WaiterHelperDbHelper(this);
		//make db a writable database from the helper
		db = mDbHelper.getWritableDatabase();
		
		//initialize layout variable for seats no
		seatNoEditText = (EditText) findViewById(R.id.edit_seat_number);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enter_patrons, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			//Navigate to the home page
			NavUtils.navigateUpFromSameTask(this);
			//close database
			db.close();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/*
	 * Add method is called when the add button is clicked.
	 * Adds the user entered info to the database
	 */
	public void add(View view)
	{
		tableNoString = ((EditText) findViewById(R.id.edit_table)).getText().toString();
		seatNoString = seatNoEditText.getText().toString();
		orderString = ((Spinner) findViewById(R.id.spinner_meal)).getSelectedItem().toString();
		
		/*
		 *  if statements to check that every required field is filled,
		 *  followed by error display thru toasts 
		 */
		if(tableNoString.isEmpty()) //table no entered
		{
			Toast.makeText(getApplicationContext(), "Please enter a table ID", Toast.LENGTH_SHORT).show();
		}
		else if(seatNoString.isEmpty()) //seat no entered
		{
			Toast.makeText(getApplicationContext(), "Please enter a seat no", Toast.LENGTH_SHORT).show();
		}
		else if(orderString.equals("Select an order")) //meal selected
		{
			Toast.makeText(getApplicationContext(), "Please select an order", Toast.LENGTH_SHORT).show();
		}
		else //every field has a value entered
		{
			//variable to store values
			ContentValues values = new ContentValues();
			values.put(OrderEntry.COLUMN_NAME_TABLE_ID, tableNoString);
			values.put(OrderEntry.COLUMN_NAME_SEAT_NO, seatNoString);
			values.put(OrderEntry.COLUMN_NAME_MEAL, orderString);
			
			//insert these values
			db.insert(OrderEntry.TABLE_NAME, null, values);
			
			//increment the seat no field by 1 to make it easier for the next entry
			incrementSeatNo();
			
			//display toast to notify the user that the order is added
			Toast.makeText(getApplicationContext(), "Order added.", Toast.LENGTH_SHORT).show();
			//changes made, saving required
			WaitMain.SAVED = false;
		}
	}
	
	/*
	 * Called when the Done button is clicked, takes the user to the main activity
	 */
	public void done(View view)
	{
		//navigate up to main activity
		NavUtils.navigateUpFromSameTask(this);
		
		//close the database
		db.close();
	}
	
	/*
	 * increments the seat no by 1
	 * 
	 * @param seatNoEditText the EditText field for the seat no
	 */
	public void incrementSeatNo()
	{
		Integer nextSeatNo = Integer.parseInt(seatNoString) + 1;
		seatNoEditText.setText(nextSeatNo.toString());
	}
}
