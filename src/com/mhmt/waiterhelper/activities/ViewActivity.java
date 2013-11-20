package com.mhmt.waiterhelper.activities;

import com.example.waiterhelper.R;
import com.mhmt.waiterhelper.database.DatabaseManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class ViewActivity extends Activity {

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
		DatabaseManager dbManager = new DatabaseManager(getApplicationContext());
		((TextView) findViewById(R.id.view_text)).setText(dbManager.printOutDb());
	}
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