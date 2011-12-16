package fr.ecn.common.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class DialogHelper {
	
	/**
	 * Show an error dialog with the given message then end the activity
	 * 
	 * @param activity
	 * @param message
	 */
	public static void errorDialog(final Activity activity, String message) {
		new AlertDialog.Builder(activity)
			.setTitle("Erreur")
			.setMessage(message)
			.setNeutralButton("Fermer", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					activity.finish();
				}
			})
			.show();
	}
	
}
