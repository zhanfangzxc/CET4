package com.yingshibao.cet4.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ProgressDialogFragment extends DialogFragment {

	public static ProgressDialogFragment instance(Bundle bundle) {
		ProgressDialogFragment pdFragment = new ProgressDialogFragment();
		pdFragment.setArguments(bundle);
		return pdFragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String msg = getArguments().getString("msg");
		boolean show = getArguments().getBoolean("show");
		ProgressDialog dialog = new ProgressDialog(getActivity());
		dialog.setMessage(msg);
		if (show)
			dialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
		return dialog;
	}

}
