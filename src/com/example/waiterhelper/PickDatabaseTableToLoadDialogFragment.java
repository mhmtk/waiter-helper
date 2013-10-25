package com.example.waiterhelper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ListAdapter;

public class PickDatabaseTableToLoadDialogFragment extends DialogFragment {

	ListAdapter adapter;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		//use builder for dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.pick_database_table)
		.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// The 'which' argument contains the index position
				// of the selected item
			}
		});
		return builder.create();
	}
}
