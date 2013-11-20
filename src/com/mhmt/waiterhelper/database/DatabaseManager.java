package com.mhmt.waiterhelper.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mhmt.waiterhelper.database.WaiterHelperContract.OrderEntry;

public class DatabaseManager {

	private WaiterHelperDbHelper mDbHelper;
	private SQLiteDatabase db;

	public DatabaseManager(Context context){

		this.mDbHelper = new WaiterHelperDbHelper(context);
		this.db = mDbHelper.getReadableDatabase();
	}

	/*
	 * called by the store() method to return the data in the database
	 * table as a String
	 */
	public String printOutDb(){
		//define a projection that specifies which columns from the database to use
		String[] projection = {
				OrderEntry.COLUMN_NAME_TABLE_ID,
				OrderEntry.COLUMN_NAME_SEAT_NO,
				OrderEntry.COLUMN_NAME_MEAL
		};

		//sort descending
		String sortOrder = OrderEntry.COLUMN_NAME_TABLE_ID + " DESC";

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
		db.close();
		return sb.toString();
	}

}
