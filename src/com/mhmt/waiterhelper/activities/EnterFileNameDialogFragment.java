package com.mhmt.waiterhelper.activities;

import com.example.waiterhelper.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class EnterFileNameDialogFragment extends DialogFragment {
	
	public interface EnterFileNameDialogListener{
		public void store(DialogFragment dialog, String filename);
//		public void onDialogNegativeClick(DialogFragment dialog);
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

		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		final View promptView = inflater.inflate(R.layout.enter_file_name_dialog_layout, null);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		
		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(promptView)
		// Add action buttons
		.setPositiveButton(R.string.enterfilenamedialog_save, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// user clicked Save
				EditText editFilename = (EditText) promptView.findViewById(R.id.edit_filenamedialog);
				mListener.store(EnterFileNameDialogFragment.this, editFilename.getText().toString());
			}
		})
		.setNegativeButton(R.string.enterfilenamedialog_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// user clicked Cancel
				EnterFileNameDialogFragment.this.getDialog().cancel();
//				mListener.onDialogNegativeClick(EnterFileNameDialogFragment.this);
			}
		});   
		return builder.create();
	}
}