package com.mhmt.waiterhelper.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mhmt.waiterhelper.database.WaiterHelperContract.OrderEntry;
import com.mhmt.waiterhelper.dataobjects.Patron;

/**
 * Database Manager class that is used by the front end classes 
 * to get info from the database
 * 
 * @author Mehmet Kologlu
 * @version November 26, 2013
 *
 */
public class DatabaseManager {

	private WaiterHelperDbHelper mDbHelper;
	private SQLiteDatabase db;
	private ArrayList<Patron> patronList;

	public DatabaseManager(Context context){

		this.mDbHelper = new WaiterHelperDbHelper(context);
	}

	/**
	 * clear the data in the database
	 */
	public void cleanDb()
	{
		mDbHelper.delete(mDbHelper.getReadableDatabase());
	}
	/**
	 * loads the database saved in the passed file 
	 * @param filename Name of the file the database is saved
	 * @param context
	 */
	public void loadDatabase(String filename, Context context) {
		File file = new File(context.getFilesDir(), filename);
		Scanner scanner;
		try {
			//create a scanner on the passed file
			scanner = new Scanner(file);
			//get a writable database and clean it
			db = mDbHelper.getWritableDatabase();
			this.cleanDb();
			//write the values from the file into the database
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
			db.close();
		} catch (FileNotFoundException e) { }
	}

	/**
	 * 
	 * @return an ArrayList<Patron> containing the patron objects of the current database
	 */
	public ArrayList<Patron> getPatronsArray() {
		db = mDbHelper.getReadableDatabase();
		patronList = new ArrayList<Patron>();

		//define a projection that specifies which columns from the database to use
		String[] projection = {
				OrderEntry.COLUMN_NAME_TABLE_ID,
				OrderEntry.COLUMN_NAME_SEAT_NO,
				OrderEntry.COLUMN_NAME_MEAL
		};

		//sort descending
		String sortOrder = OrderEntry.COLUMN_NAME_TABLE_ID + " DESC";

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
			Patron p = new Patron(c.getString(c.getColumnIndexOrThrow(OrderEntry.COLUMN_NAME_TABLE_ID)),
					c.getString(c.getColumnIndexOrThrow(OrderEntry.COLUMN_NAME_SEAT_NO)),
					c.getString(c.getColumnIndexOrThrow(OrderEntry.COLUMN_NAME_MEAL)));
			patronList.add(p);
			c.moveToNext();
		}
		db.close();
		return patronList;
	}

	/**
	 * called by the store() method to return the data in the database
	 * table as a String
	 */
	public String printOutDb(){
		db = mDbHelper.getReadableDatabase();
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
