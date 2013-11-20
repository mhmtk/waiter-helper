package com.mhmt.waiterhelper.database;

import android.provider.BaseColumns;

public final class WaiterHelperContract {

	// give it an empty constructor.
	// To prevent someone from accidentally instantiating the contract class,
	public WaiterHelperContract() {}

	/* Inner class that defines the table contents */
	public static abstract class OrderEntry implements BaseColumns {
		public static final String TABLE_NAME = "WaiterHelpTable";
		public static final String COLUMN_NAME_TABLE_ID = "TableID";
		public static final String COLUMN_NAME_SEAT_NO = "SeatNo";
		public static final String COLUMN_NAME_MEAL = "MealSelection";
	}
}
