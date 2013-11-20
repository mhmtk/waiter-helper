package com.mhmt.waiterhelper.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.example.waiterhelper.R;
import com.mhmt.waiterhelper.database.DatabaseManager;
import com.mhmt.waiterhelper.database.WaiterHelperDbHelper;
import com.mhmt.waiterhelper.database.WaiterHelperContract.OrderEntry;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
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
	private DatabaseManager dbManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wait_main);
		dbManager = new DatabaseManager(this.getApplicationContext());
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

	/**
	 * launch an activity where the loaded database will be in display
	 * @param view
	 */
	public void view(View view)
	{
		Intent viewIntent = new Intent(this, ViewActivity.class);
		startActivity(viewIntent);
	}

	/**
	 * ask user which database to load, and load it
	 * 
	 * @param view
	 */
	public void load(View view)
	{
		//		DialogFragment newFragment = new PickDatabaseTableToLoadDialogFragment();
		//		newFragment.show(getSupportFragmentManager(), "pickADatabase");

		//HARDCODED FILENAME GET THIS FROM THE USER
		String filename = "yolo";
		loadDatabase(filename);
	}

	private void loadDatabase(String filename) {
		File file = new File(this.getFilesDir(), filename);
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			WaiterHelperDbHelper myDbHelper = new WaiterHelperDbHelper(this);
			SQLiteDatabase db = myDbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();

			while(scanner.hasNext())
			{
				String line = scanner.nextLine();
				String[] cellArray = line.split(",");
				values.put(OrderEntry.COLUMN_NAME_TABLE_ID, cellArray[0]);
				values.put(OrderEntry.COLUMN_NAME_SEAT_NO, cellArray[1]);
				values.put(OrderEntry.COLUMN_NAME_MEAL, cellArray[2]);
				db.insert(OrderEntry.TABLE_NAME, null, values);
			}
			Toast.makeText(getApplicationContext(),"Database laoded", Toast.LENGTH_SHORT).show();
		} catch (FileNotFoundException e) { }
	}

	public void exit(View view)
	{

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

	/*
	 * called when the user hits save on the enter file name dialog fragment,
	 * stores the data
	 */
	@Override
	public void store(DialogFragment dialog, String filename) {
		//create the file
		File file = new File(this.getFilesDir()+"/"+filename);
		try{
			PrintWriter printWriter = new PrintWriter(file);
			String dBString = dbManager.printOutDb();
			printWriter.write(dBString);
			printWriter.close();
			Toast.makeText(getApplicationContext(), "File stored", Toast.LENGTH_SHORT).show();
		}
		catch(Exception e){}
	}

//
//	/*
//	 * called by the store() method to return the data in the database
//	 * table as a String
//	 */
//	public String printOutDb(){
//		//make helper
//		WaiterHelperDbHelper mDbHelper = new WaiterHelperDbHelper(this);
//
//		//Get a readable database
//		SQLiteDatabase db = mDbHelper.getReadableDatabase();
//
//		//define a projection that specifies which columns from the database to use
//		String[] projection = {
//				OrderEntry.COLUMN_NAME_TABLE_ID,
//				OrderEntry.COLUMN_NAME_SEAT_NO,
//				OrderEntry.COLUMN_NAME_MEAL
//		};
//
//		//sort descending
//		String sortOrder = OrderEntry.COLUMN_NAME_TABLE_ID + " DESC";
//
//		//create string builder to write string
//		StringBuilder sb = new StringBuilder();
//
//		//create cursor with the whole database
//		Cursor c = db.query(
//				OrderEntry.TABLE_NAME,  // The table to query
//				projection,				// The columns to return
//				null,		            // The columns for the WHERE clause
//				null,                   // The values for the WHERE clause
//				null,                   // don't group the rows
//				null,					// don't filter by row groups
//				sortOrder               // The sort order
//				);
//
//		//move cursor to the beginning
//		c.moveToFirst();
//
//		while(!c.isAfterLast())
//		{
//			sb.append(c.getString(c.getColumnIndexOrThrow(OrderEntry.COLUMN_NAME_TABLE_ID)));
//			sb.append(",");
//			sb.append(c.getString(c.getColumnIndexOrThrow(OrderEntry.COLUMN_NAME_SEAT_NO)));
//			sb.append(",");
//			sb.append(c.getString(c.getColumnIndexOrThrow(OrderEntry.COLUMN_NAME_MEAL)));
//			sb.append("\n");
//			c.moveToNext();
//		}
//		return sb.toString();
//	}
}
