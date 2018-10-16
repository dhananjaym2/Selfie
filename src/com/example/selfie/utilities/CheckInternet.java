package com.example.selfie.utilities;

import com.example.selfie.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternet {

	private static Activity mainActivity;

	public static boolean hasConnection(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo wifiNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetwork != null && wifiNetwork.isConnected()) {
			return true;
		}

		NetworkInfo mobileNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileNetwork != null && mobileNetwork.isConnected()) {
			return true;
		}

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			return true;
		}

		return false;
	}

	public static void showAlert(Context c) {
		AlertDialog.Builder alert = new AlertDialog.Builder(c);
		alert.setTitle(R.string.InternetConnectionHeading);

		alert.setMessage(R.string.InternetConnectionMessage);
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				dialog.cancel();
			}
		});
		AlertDialog alertDialog = alert.create();
		alertDialog.show();
	}

	public static void showAlertForMain(Context c, final Activity mainActivity) {
		CheckInternet.mainActivity = mainActivity;
		AlertDialog.Builder alert = new AlertDialog.Builder(c);
		alert.setTitle(R.string.InternetConnectionHeading);

		alert.setMessage(R.string.InternetConnectionMessage);
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int whichButton) {

				dialog.cancel();

				mainActivity.finish();
			}
		});
		AlertDialog alertDialog = alert.create();
		alertDialog.show();
	}

	public static void showTimeOutAlert(Context c) {
		AlertDialog.Builder alert = new AlertDialog.Builder(c);
		alert.setTitle(R.string.InternetConnectionHeading);

		alert.setMessage(R.string.InternetConnectionMessage);
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				dialog.cancel();
			}
		});
		AlertDialog alertDialog = alert.create();
		alertDialog.show();
	}
}
