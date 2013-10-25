package com.example.waiterhelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

public class EnterFileNameDialogFragment extends DialogFragment {
	
	public interface EnterFileNameDialogListener{
		public void onDialogPositiveClick(DialogFragment dialog);
		public void onDialogNegativeClick(DialogFragment dialog);
	}
	
	EnterFileNameDialogListener mListener;
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		//Verify that the host activity implements the callback interface
		try{
			//Instantiate the EnterFileNameDialogListener so we can send events to the host
			mListener = (EnterFileNameDialogListener) activity;
		}
		catch(ClassCastException e) {
			// the activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
				+ " must implement EnterFileNameSDialogListener");
		}
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(inflater.inflate(R.layout.enter_file_name_dialog_layout, null))
		// Add action buttons
		.setPositiveButton(R.string.enterfilenamedialog_save, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// user clicked Save
				mListener.onDialogPositiveClick(EnterFileNameDialogFragment.this);
			}
		})
		.setNegativeButton(R.string.enterfilenamedialog_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// user clicked Cancel
				mListener.onDialogNegativeClick(EnterFileNameDialogFragment.this);
			}
		});      
		return builder.create();
	}
}
