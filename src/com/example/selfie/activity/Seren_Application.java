package com.example.selfie.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.entity.mime.content.ContentBody;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.selfie.constants.SEREN_SharedPreferences;
import com.example.selfie.model.SEREN_PostModel;

public class Seren_Application extends Application {
	/**
	 * Seren Application delegate class
	 */

	/**
	 * Seren Application members declarations
	 */
	public static Seren_Application SerenApplicationInstance = null;
	public static String device_lang, tag_language;
	// public static String[] contactnamelist;
	public static String userid = "";
	// public ImageLoader mImageLoader;
	// public DisplayImageOptions mDisplayImageOptions;
	public static SEREN_SharedPreferences mSharedPreferences;

	@Override
	public void onCreate() {
		super.onCreate();
		getApplicationInstance(this);

		/*
		 * gets the device language and its code
		 */
		// device_lang = Locale.getDefault().getDisplayLanguage();
		// String code = Locale.getDefault().toString();
		//
		// SEREN_Debug.Debug(2, "Device language : " + device_lang);
		// if (device_lang.equals("English")) {
		// tag_language = "eng";
		// SEREN_Debug.Debug(2, "Language tag : " + tag_language);
		// } else if (device_lang.equals("français")) {
		// tag_language = "fra";
		// SEREN_Debug.Debug(2, "Language tag : " + tag_language);
		// } else if (device_lang.equals("中文") ||
		// code.equalsIgnoreCase("zh_CN")) {
		// tag_language = "zho";
		// SEREN_Debug.Debug(2, "Language tag : " + tag_language);
		// } else {
		// tag_language = "eng";
		// SEREN_Debug.Debug(2, "default Language tag : " + tag_language);
		// }
	}

	/**
	 * Method to initilaize an object
	 * 
	 * @param mContext
	 * @return application object
	 */
	public static Seren_Application getApplicationInstance(Context mContext) {

		if (SerenApplicationInstance == null) {
			SerenApplicationInstance = new Seren_Application();
		}

		return SerenApplicationInstance;

	}

	/**
	 * Method to check email is valid or not
	 * 
	 * @param email
	 * @return boolean value
	 */
	public boolean emailValidator(String email) {
		Pattern mPattern;
		Matcher mMatcher;
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		mPattern = Pattern.compile(EMAIL_PATTERN);
		mMatcher = mPattern.matcher(email);
		Log.d("test", "email mMatcher.matches() result:" + mMatcher.matches());
		return mMatcher.matches();
	}

	/**
	 * Method to Convert stream to string
	 * 
	 * @param is
	 * @return string
	 * @throws Exception
	 */
	public String convertStreamToString(InputStream is) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;

		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		is.close();
		return sb.toString();
	}

	/**
	 * Method to add modal class object in arraylist
	 * 
	 * @param mArrPostParam
	 * @param key
	 * @param Value
	 * @param ParamType
	 * @return ArrayList
	 */
	public ArrayList<SEREN_PostModel> SEREN_setPostParams(
			ArrayList<SEREN_PostModel> mArrPostParam, String key, String Value,
			String ParamType) {

		SEREN_PostModel mSEREN_POSTModel = new SEREN_PostModel();

		mSEREN_POSTModel.setStr_PostParamKey(key);

		mSEREN_POSTModel.setObj_PostParamValue(Value);

		mSEREN_POSTModel.setStr_PostParamType(ParamType);

		mArrPostParam.add(mSEREN_POSTModel);

		return mArrPostParam;
	}

	/**
	 * Method to add modal class object in arraylist
	 * 
	 * @param mArrPostParam
	 * @param key
	 * @param Value
	 * @param ParamType
	 * @return ArrayList
	 */
	public ArrayList<SEREN_PostModel> SEREN_setPostParams(
			ArrayList<SEREN_PostModel> mArrPostParam, String key,
			Integer Value, String ParamType) {

		SEREN_PostModel mSEREN_POSTModel = new SEREN_PostModel();

		mSEREN_POSTModel.setStr_PostParamKey(key);

		mSEREN_POSTModel.setObj_PostParamValue(Value);

		mSEREN_POSTModel.setStr_PostParamType(ParamType);

		mArrPostParam.add(mSEREN_POSTModel);

		return mArrPostParam;
	}

	/**
	 * Method to add modal class object in arraylist
	 * 
	 * @param mArrPostParam
	 * @param key
	 * @param Value
	 * @param ParamType
	 * @return ArrayList
	 */
	public ArrayList<SEREN_PostModel> SEREN_setPostParams(
			ArrayList<SEREN_PostModel> mArrPostParam, String key, Bitmap Value,
			String ParamType) {

		SEREN_PostModel mSEREN_POSTModel = new SEREN_PostModel();

		mSEREN_POSTModel.setStr_PostParamKey(key);

		mSEREN_POSTModel.setObj_PostParamValue(Value);

		mSEREN_POSTModel.setStr_PostParamType(ParamType);

		mArrPostParam.add(mSEREN_POSTModel);

		return mArrPostParam;
	}

	/**
	 * Method to add modal class object in arraylist for register page
	 * 
	 * @param mArrPostParam
	 * @param key
	 * @param Value
	 * @param ParamType
	 * @return ArrayList
	 */
	public ArrayList<SEREN_PostModel> SEREN_setPostParamsForRegister(
			ArrayList<SEREN_PostModel> mArrPostParam, String key,
			ContentBody Value, String ParamType) {

		SEREN_PostModel mSEREN_POSTModel = new SEREN_PostModel();

		mSEREN_POSTModel.setStr_PostParamKey(key);

		mSEREN_POSTModel.setObj_PostParamValue(Value);

		mSEREN_POSTModel.setStr_PostParamType(ParamType);

		mArrPostParam.add(mSEREN_POSTModel);

		return mArrPostParam;
	}

}