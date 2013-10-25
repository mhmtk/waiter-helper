package com.example.waiterhelper;


import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.waiterhelper.WaiterHelperContract.OrderEntry;

/*
 * @author Mehmet Kologlu
 * @version 1.0.0
 */
public class EnterPatronsActivity extends Activity {

	//initialize the database helper
	WaiterHelperDbHelper mDbHelper;
	//initialize the database
	SQLiteDatabase db;
	
	//entry variables
	String tableNoString;
	String seatNoString;
	String orderString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_patrons);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//define the helper
		mDbHelper = new WaiterHelperDbHelper(this);
		//make db a writable database from the helper
		db = mDbHelper.getWritableDatabase();
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
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/*
	 * Add method is called when the add button is clicked.
	 */
	public void add(View view)
	{
		//variable to store values
		ContentValues values = new ContentValues();
		
		//table no variable
		tableNoString = ((EditText) findViewById(R.id.edit_table)).getText().toString();
		
		//seat no variables
		EditText seatNoEditText = (EditText) findViewById(R.id.edit_seat_number); //the edittext view
		seatNoString = seatNoEditText.getText().toString(); //the input string

		//order variable
		orderString = ((Spinner) findViewById(R.id.spinner_meal)).getSelectedItem().toString();
		
		//get values
		values.put(OrderEntry.COLUMN_NAME_TABLE_ID, tableNoString);
		values.put(OrderEntry.COLUMN_NAME_SEAT_NO, seatNoString);
		values.put(OrderEntry.COLUMN_NAME_MEAL, orderString);
		
		//insert these values
		db.insert(OrderEntry.TABLE_NAME, OrderEntry.COLUMN_NAME_NULLABLE, values);
		
		//increment the seat no field by 1 to make it easier for the next entry
		incrementSeatNo(seatNoEditText);
		
		//display toast to notify the user that the order is added
		displayToast("Order added.");
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
	 * display a toast in (Gravity.Center, 0, -50) with the input text
	 * and duration
	 * 
	 * @param text text to be displayed
	 * @param duration duration of the toast
	 */
	public void displayToast(String text)
	{
		//make a toast
		Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		//set the gravity
		toast.setGravity(Gravity.CENTER, 0, -50);
		//display
		toast.show();
	}
	
	/*
	 * increments the seat no by 1
	 * 
	 * @param seatNoEditText the EditText field for the seat no
	 */
	public void incrementSeatNo(EditText seatNoEditText)
	{
		Integer nextSeatNo = Integer.parseInt(seatNoString) + 1;
		seatNoEditText.setText(nextSeatNo.toString());
	}
}
