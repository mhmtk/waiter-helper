package com.mhmt.waiterhelper.fragments;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import com.example.waiterhelper.R;
import com.mhmt.waiterhelper.database.DatabaseManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class PickDatabaseToLoadDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		final Activity activity = this.getActivity();
		final ArrayList<File> files = new ArrayList<File>(Arrays.asList(activity.getFilesDir().listFiles()));
		//make adapter
		ArrayAdapter<File> arrayAdapter = new ArrayAdapter<File>(activity, 
				android.R.layout.simple_list_item_1, 
				files);
		
		
		//use builder for dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.pick_database_table)
		.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            	Toast.makeText(activity,"Database loaded", Toast.LENGTH_SHORT).show();
            	DatabaseManager dbManager = new DatabaseManager(activity);
            	dbManager.loadDatabase(files.get(id).getName(), activity);
            }
        });
		return builder.create();
	}
}