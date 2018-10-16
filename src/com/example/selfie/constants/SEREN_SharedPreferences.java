package com.example.selfie.constants;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;

public class SEREN_SharedPreferences {
	/**
	 * SEREN_SharedPreferences Class is used to maintain sharedpreferences
	 * 
	 */

	/*
	 * SEREN_SharedPreferences Members Declarations
	 */
	private Context mContext;
	private SharedPreferences mSharedPreferences;
	private Editor mEditor;

	private String str_PrefName = "serenpref";

	private boolean is_logged_in = true;

	/**
	 * SEREN_SharedPreferences Constructor Implementation
	 */
	public SEREN_SharedPreferences(Context context,
			OnSharedPreferenceChangeListener mOnSharedPreferenceChangeListener) {
		this.mContext = context;
		if (mContext != null) {
			if (str_PrefName != null) {
				mSharedPreferences = mContext.getSharedPreferences(
						str_PrefName, Context.MODE_WORLD_WRITEABLE);
				if (mOnSharedPreferenceChangeListener != null) {
					mSharedPreferences
							.registerOnSharedPreferenceChangeListener(mOnSharedPreferenceChangeListener);
				}
			} else {
				Log.d("SharedPreferences", "str_PrefName null");
			}
		} else {
			Log.d("SharedPreferences", "mContext null");
		}
	}

	/**
	 * This method is used to store String value in SharedPreferences
	 * 
	 * @param String
	 *            editorkey
	 * @param String
	 *            editorvalue
	 */

	public void putStringValue(String editorkey, String editorvalue) {
		mEditor = mSharedPreferences.edit();
		mEditor.putString(editorkey, editorvalue);
		mEditor.commit();
	}

	/**
	 * This method is used to store int value in SharedPreferences
	 * 
	 * @param String
	 *            editorkey
	 * @param String
	 *            editorvalue
	 */

	public void putIntValue(String editorkey, int editorvalue) {
		mEditor = mSharedPreferences.edit();
		mEditor.putInt(editorkey, editorvalue);
		mEditor.commit();
	}

	/**
	 * This method is used to store boolean value in SharedPreferences
	 * 
	 * @param String
	 *            editorkey
	 * @param String
	 *            editorvalue
	 */

	public void putBooleanValue(String editorkey, boolean editorvalue) {
		mEditor = mSharedPreferences.edit();
		mEditor.putBoolean(editorkey, editorvalue);
		mEditor.commit();
	}

	/**
	 * This method is used to get String value from SharedPreferences
	 * 
	 * @param String
	 *            editorkey
	 * @param String
	 *            defValue
	 * @return String PrefValue
	 */

	public String getStringValue(String editorkey, String defValue) {
		String PrefValue = mSharedPreferences.getString(editorkey, defValue);

		return PrefValue;

	}

	/**
	 * This method is used to get int value from SharedPreferences
	 * 
	 * @param String
	 *            editorkey
	 * @param String
	 *            defValue
	 * @return int PrefValue
	 */

	public int getIntValue(String editorkey, int defValue) {
		int PrefValue = mSharedPreferences.getInt(editorkey, defValue);

		return PrefValue;

	}

	/**
	 * This method is used to get boolean value from SharedPreferences
	 * 
	 * @param String
	 *            editorkey
	 * @param String
	 *            defValue
	 * @return boolean PrefValue
	 */

	public boolean getBooleanValue(String editorkey, boolean defValue) {
		boolean PrefValue = mSharedPreferences.getBoolean(editorkey, defValue);

		return PrefValue;

	}

	public void putCategoryValue(String editorkey, String editorvalue) {
		mEditor = mSharedPreferences.edit();
		mEditor.putString(editorkey, editorvalue);
		mEditor.commit();
	}

	/**
	 * This method is used to get String value from SharedPreferences
	 * 
	 * @param String
	 *            editorkey
	 * @param String
	 *            defValue
	 * @return String PrefValue
	 */

	public String getCategoryValue(String editorkey, String defValue) {
		String PrefValue = mSharedPreferences.getString(editorkey, defValue);

		return PrefValue;

	}

	public void clearSharedPreference() {

		Log.d("SharedPreferences", "in clear shared preferences");
		mEditor.clear();
		mEditor.commit();
	}

}
