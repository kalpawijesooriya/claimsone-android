package com.irononetech.android.honeycomb.ui.components;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class IrondroidDialogFragment extends DialogFragment {
	public static IrondroidDialogFragment newInstance(String title) {
		IrondroidDialogFragment frag = new IrondroidDialogFragment();
		Bundle args = new Bundle();
		args.putString("text", title);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String text = getArguments().getString("text");

		return new AlertDialog.Builder(getActivity()).setTitle(
				"A Dialog of Awesome").setMessage(text).setPositiveButton(
				android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				}).create();
	}
}
