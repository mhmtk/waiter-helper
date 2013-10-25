package com.example.waiterhelper;

import java.io.File;
import java.io.PrintWriter;

import com.example.waiterhelper.WaiterHelperContract.OrderEntry;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author Mehmet Kologlu
 * @version October 24 2013
 *
 */

public class WaitMain extends FragmentActivity 
implements EnterFileNameDialogFragment.EnterFileNameDialogListener{

	//instance variables
	String filename; //the filename for store

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wait_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wait_main, menu);
		return true;
	}


	/*
	 * This method is called when the Enter Patrons button is clicked. It opens
	 * the activity EnterPatronsActivity
	 */
	public void enterPatrons(View view)
	{
		//Create an intent and start the activity
		Intent enterIntent = new Intent(this, EnterPatronsActivity.class);
		startActivity(enterIntent);
	}

	/*
	 * called when the store button is clicked, prompts the user
	 * for a filename input by displaying a dialog fragment.
	 * if the user hits cancel on the fragment nothing happens;
	 * if the user enters a file name and hits save, then the
	 * onDialogPositiveClick() method is called, which calls
	 * the store() method, which does the actual storing
	 */
	public void initializeStore(View view)
	{
		//display dialog to get file name
		EnterFileNameDialogFragment filenamedialogfragment = new EnterFileNameDialogFragment();
		filenamedialogfragment.show(this.getSupportFragmentManager(), "EnterFileNameDialogFragment");

	}

	public void view(View view)
	{
		Intent viewIntent = new Intent(this, ViewActivity.class);
		startActivity(viewIntent);
	}

	public void load(View view)
	{
		
	}

	public void exit(View view)
	{

	}

	/*
	 * called when the user hits cancel on the enter file name dialog fragment,
	 * does nothing
	 */
	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// do nothing?	
	}

	/*
	 * called when the inputs a filename to store the database table in.
	 * prints the database into a file.
	 */
	public void store()
	{
		//create the file
		File file = new File(this.getFilesDir()+"/"+filename);
		try{
			PrintWriter printWriter = new PrintWriter(file);
			String dBString = this.printOutDb();
			printWriter.write(dBString);
			printWriter.close();
			Toast fileAddedToast = Toast.makeText(getApplicationContext(), "File stored", Toast.LENGTH_SHORT);
			fileAddedToast.show();
		}
		catch(Exception e){}
	}

	/*
	 * called when the user hits save on the enter file name dialog fragment,
	 * calls the store() method to store the data as a file with the input
	 * filename
	 */
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		// save the file name
		EditText fileNameEditText = (EditText) findViewById(R.id.edit_filenamedialog);
		filename = fileNameEditText.getText().toString();
		this.store();

	}

	/*
	 * called by the store() method to return the data in the database
	 * table as a String
	 */
	public String printOutDb(){
		//make helper
		WaiterHelperDbHelper mDbHelper = new WaiterHelperDbHelper(this);

		//Get a readable database
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		//define a projection that specifies which columns from the database to use
		String[] projection = {
				OrderEntry._ID,
				OrderEntry.COLUMN_NAME_TABLE_ID,
				OrderEntry.COLUMN_NAME_SEAT_NO,
				OrderEntry.COLUMN_NAME_MEAL
		};

		//sort descending
		String sortOrder = OrderEntry.COLUMN_NAME_TABLE_ID + "DESC";

		//create string builder to write string
		StringBuilder sb = new StringBuilder();

		//create cursor with the whole database
		Cursor c = db.query(
				OrderEntry.TABLE_NAME,  // The table to query
				projection,				// The columns to return
				null,		            // The columns for the WHERE clause
				null,                   // The values for the WHERE clause
				null,                   // don't group the rows
				null,					// don't filter by row groups
				sortOrder               // The sort order
				);

		//move cursor to the beginning
		c.moveToFirst();

		while(!c.isAfterLast())
		{
			sb.append(c.getString(c.getColumnIndexOrThrow(OrderEntry.COLUMN_NAME_TABLE_ID)));
			sb.append(",");
			sb.append(c.getString(c.getColumnIndexOrThrow(OrderEntry.COLUMN_NAME_SEAT_NO)));
			sb.append(",");
			sb.append(c.getString(c.getColumnIndexOrThrow(OrderEntry.COLUMN_NAME_MEAL)));
			sb.append("\n");
			c.moveToNext();
		}
		return sb.toString();
	}
}
