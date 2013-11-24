package com.mhmt.waiterhelper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mhmt.waiterhelper.database.WaiterHelperContract.OrderEntry;

public class WaiterHelperDbHelper extends SQLiteOpenHelper{

	// If you change the database schema, you must increment the database version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "OrderEntry.db";

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + OrderEntry.TABLE_NAME + " (" +
					OrderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
					OrderEntry.COLUMN_NAME_TABLE_ID + TEXT_TYPE + COMMA_SEP +
					OrderEntry.COLUMN_NAME_SEAT_NO + TEXT_TYPE + COMMA_SEP +
					OrderEntry.COLUMN_NAME_MEAL + TEXT_TYPE +
					")";

	private static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + OrderEntry.TABLE_NAME;

	public WaiterHelperDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy is
		// to simply to discard the data and start over
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

	public void delete(SQLiteDatabase db)
	{

		db.execSQL("DELETE FROM " + OrderEntry.TABLE_NAME );
	}
}