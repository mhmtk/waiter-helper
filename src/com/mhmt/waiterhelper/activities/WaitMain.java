package com.mhmt.waiterhelper.activities;

import java.io.File;
import java.io.PrintWriter;

import com.example.waiterhelper.R;
import com.mhmt.waiterhelper.database.DatabaseManager;
import com.mhmt.waiterhelper.fragments.EnterFileNameDialogFragment;
import com.mhmt.waiterhelper.fragments.PickDatabaseToLoadDialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

/**
 * 
 * @author Mehmet Kologlu
 * @version November 22 2013
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

	/**
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
		DialogFragment newFragment = new PickDatabaseToLoadDialogFragment();
		newFragment.show(getSupportFragmentManager(), "pickADatabase");
	}

	public void exit(View view)
	{
		finish();
		System.exit(0);
	}

	/**
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

	/**
	 * called when the user hits save on the enter file name dialog fragment,
	 * stores the data
	 */
	@Override
	public void store(DialogFragment dialog, String filename) {
		//create the file
		File file = new File(this.getFilesDir()+"/"+filename);
		String dBString = dbManager.printOutDb();
		try{
			PrintWriter printWriter = new PrintWriter(file);
			printWriter.write(dBString);
			printWriter.close();
			Toast.makeText(getApplicationContext(), "File stored", Toast.LENGTH_SHORT).show();
		}
		catch(Exception e){
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
		}
		dbManager.cleanDb();
	}
}
