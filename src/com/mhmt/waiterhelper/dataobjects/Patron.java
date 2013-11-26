package com.mhmt.waiterhelper.dataobjects;

/**
 * Patron class to hold the data for one patron
 * @author Mehmet Kologlu
 * @version November 18, 2013
 *
 */
public class Patron {

	private String tID;
	private String sNo;
	private String meal;
	
	public Patron(String tableID, String seatNo, String mealSelection)
	{
		tID = tableID;
		sNo = seatNo;
		meal = mealSelection;
	}

	public String getTableID() {
		return tID;
	}

	public String getSeatNo() {
		return sNo;
	}

	public String getMealSelection() {
		return meal;
	}
	
	public String toString() {
		return "Table ID: " + tID + "\n" +
				"Seat No: " + sNo + "\n" +
				"Meal: " + meal + "\n";
	}
	
}
