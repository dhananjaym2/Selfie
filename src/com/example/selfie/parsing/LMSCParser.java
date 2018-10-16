package com.example.selfie.parsing;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.selfie.R;
import com.example.selfie.model.ListingResponse_Model;

/**
 * Created by dhananjay on 7/2/15.
 */
public class LMSCParser {
	/**
	 * This class contains Server's Response Parsing methods for LMSC API calls
	 * on different pages
	 */
	Context mContext;
	String apiResult;

	public LMSCParser(Context mContext, String apiResult) {
		this.mContext = mContext;
		this.apiResult = apiResult;
	}

	public void listingResponseParsing(
			ArrayList<ListingResponse_Model> arraylist_ListingResponse_Model) {
		try {
			JSONObject jobjParent = new JSONObject(apiResult);
			String error = jobjParent.optString("error");
			if (error != null && error.equals("null")) {
				boolean new_user = jobjParent.getBoolean("new");
				if (new_user == true) {
					// new_user secret
				}
				if (jobjParent.has("result")) {
					// Utility.showAlert(mContext,
					// jobjParent.getString(ServerRequestConstants.data),
					// null, true, null);
					JSONObject jobjTemp;
					JSONArray jArrData = jobjParent.getJSONArray("result");
					for (int index = 0; index < jArrData.length(); index++) {

						jobjTemp = jArrData.getJSONObject(index);
						ListingResponse_Model mListingResponse_Model = new ListingResponse_Model();
						mListingResponse_Model.setId(jobjTemp.getString("id"));
						mListingResponse_Model.setImage_url(jobjTemp
								.getString("image"));
						mListingResponse_Model.setVideo_url(jobjTemp
								.getString("video"));
						// mCountryModel.setCountry_name(jobjTemp.getString(ServerRequestConstants.country_name));
						// Utility.debugLog(1, "LMSCParser", "id_:" +
						// mCountryModel.getId_CountryModel() +
						// " name:" + mCountryModel.getCountry_name());

						arraylist_ListingResponse_Model
								.add(mListingResponse_Model);
					}
					Log.d("LMSCParser",
							"arraylist_ListingResponse_Model.size():"
									+ arraylist_ListingResponse_Model.size());
				} else {
					Log.d("LMSCParser", "result null for status_1");
				}
				// if (jobjParent.has(ServerRequestConstants.msg)) {
				// Utility.showAlert(mContext,
				// jobjParent.getString(ServerRequestConstants.msg),
				// null, true, null);
				// } else {
				// Utility.debugLog(1, "LMSCParser",
				// "msg null for status_1");
				// }
			} else {
				// if (jobjParent.has(ServerRequestConstants.msg)) {
				if (error != null) {
					// Utility.showAlert(mContext, error, null, true, null);
					Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
					Log.d("LMSCParser", "error :" + error);
				} else {
					Log.d("LMSCParser", "msg null for status NOT = 1");
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
			// Utility.showAlert(mContext,
			// mContext.getString(R.string.OopsSomethingWentWrong), null,
			// true, null);
			Toast.makeText(mContext,
					mContext.getString(R.string.OopsSomethingWentWrong),
					Toast.LENGTH_SHORT).show();
		}
	}
}