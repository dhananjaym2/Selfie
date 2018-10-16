package com.example.selfie.async;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.util.Log;

import com.example.selfie.activity.Seren_Application;
import com.example.selfie.constants.ServerRequestConstants;
import com.example.selfie.interfaces.OnReciveServerResponse;
import com.example.selfie.model.SEREN_PostModel;
import com.example.selfie.utilities.CheckInternet;

/**
 * SEREN_POSTASYNC class is used to execute POST Request By Using this class we
 * can send Data(image, text, multimedia content etc) on Server
 */
public class SEREN_POSTASYNC extends AsyncTask<String, Void, String> {

	/**
	 * SEREN_GETASYNC Members Declarations
	 */
	private ProgressDialog newprogress;
	private Context mContext;
	private OnReciveServerResponse mOnReciveServerResponse;
	private String str_PostApiName;
	private String str_PostRequestURL;
	private ArrayList<SEREN_PostModel> arr_PostModels;
	private Seren_Application mSERENApplication;
	/* public static */
	private boolean isShowProgressDialog;

	/**
	 * Constructor Implementations
	 */
	public SEREN_POSTASYNC(Context context,
			OnReciveServerResponse onReciveServerResponse, String mPostApiName,
			String mPostRequestURL, ArrayList<SEREN_PostModel> mPostModelArray,
			boolean isShowProgressDialog) {

		this.mContext = context;
		this.mOnReciveServerResponse = onReciveServerResponse;
		this.str_PostApiName = mPostApiName;
		this.str_PostRequestURL = mPostRequestURL;
		this.arr_PostModels = mPostModelArray;
		mSERENApplication = Seren_Application.getApplicationInstance(mContext);
		this.isShowProgressDialog = isShowProgressDialog;

	}

	/**
	 * This Method is called before the execution start
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// ProgressHUD.show(mContext,true);

		if (isShowProgressDialog) {
			newprogress = new ProgressDialog(mContext);
			newprogress.setMessage("Loading...");
			newprogress.setCancelable(false);
			if (!newprogress.isShowing()) {
				newprogress.show();
			}
		}
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		Log.d("POSTASYNC", "in onCancelled() of SEREN_POSTASYNC");
		if (newprogress != null) {
			if (newprogress.isShowing()) {
				try {
					newprogress.dismiss();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This Method is called during the execution
	 */
	@Override
	protected String doInBackground(String... params) {
		if (!CheckInternet.hasConnection(mContext)) {
			return "No_Internet_Connection";
			// return;
		} else {
			try {
				/**
				 * Dj changes for language parameter for each API
				 */
				SEREN_PostModel mSeren_PostModel = new SEREN_PostModel();
				mSeren_PostModel.setStr_PostParamKey("language");
				mSeren_PostModel
						.setStr_PostParamType(ServerRequestConstants.Key_PostStrValue);
				mSeren_PostModel
						.setObj_PostParamValue(Seren_Application.tag_language);
				arr_PostModels.add(mSeren_PostModel);
				/**
				 * Dj changes upto here
				 */

				/**
				 * Establishing Connection with Server
				 */
				HttpClient mHttpClient = new DefaultHttpClient();
				HttpContext mHttpContext = new BasicHttpContext();
				HttpPost mHttpPost = new HttpPost(str_PostRequestURL);

				/**
				 * Setting up Parameters with the request
				 */
				MultipartEntity mMultipartEntity = new MultipartEntity(
						HttpMultipartMode.BROWSER_COMPATIBLE);

				/**
				 * Process to get Request Parameter Values from ArrayList
				 * ArryList Value str_PostParamMIME Represent the MIME Type of
				 * Data and str_PostParamKey Represent the Request Param Key and
				 * Obj_PostParamValue Represent the their values
				 */

				StringBuilder builder = null;
				for (int i = 0; i < arr_PostModels.size(); i++) {
					if (arr_PostModels
							.get(i)
							.getStr_PostParamType()
							.equalsIgnoreCase(
									(ServerRequestConstants.Key_PostStrValue))) {
						mMultipartEntity.addPart(arr_PostModels.get(i)
								.getStr_PostParamKey(), new StringBody(
								(String) arr_PostModels.get(i)
										.getObj_PostParamValue()));
						String param = arr_PostModels.get(i)
								.getStr_PostParamKey()
								+ " = "
								+ arr_PostModels.get(i).getObj_PostParamValue();

						if (builder == null) {
							builder = new StringBuilder();
							builder.append(param);
						} else {
							builder.append(", " + param);
						}
					} else if (arr_PostModels
							.get(i)
							.getStr_PostParamType()
							.equalsIgnoreCase(
									(ServerRequestConstants.Key_PostbyteValue))) {
						Bitmap thePic = (Bitmap) arr_PostModels.get(i)
								.getObj_PostParamValue();

						byte[] photo = null;
						Log.d("POSTASYNC", "Image is selected");
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						thePic.compress(CompressFormat.JPEG, 80, bos);
						photo = bos.toByteArray();

						ByteArrayBody mBody = new ByteArrayBody((byte[]) photo,
								"image1" + ".jpg");

						mMultipartEntity.addPart(arr_PostModels.get(i)
								.getStr_PostParamKey(), mBody);

						String param = arr_PostModels.get(i)
								.getStr_PostParamKey()
								+ " = "
								+ arr_PostModels.get(i).getObj_PostParamValue();

						if (builder == null) {
							builder = new StringBuilder();
							builder.append(param);
						} else {
							builder.append(", " + param);
						}

					} else if (arr_PostModels
							.get(i)
							.getStr_PostParamType()
							.equalsIgnoreCase(
									(ServerRequestConstants.Key_PostFileValue))) {

					}

				}

				Log.d("post", "post param => " + builder.toString());
				mHttpPost.setEntity(mMultipartEntity);
				HttpResponse mHttpResponse = null;
				try {
					mHttpResponse = mHttpClient
							.execute(mHttpPost, mHttpContext);
				} catch (HttpHostConnectException e) {
					e.printStackTrace();
					return "Network_Error";
				} catch (Exception e) {
					e.printStackTrace();
					return "Network_Error";
				}
				if (mHttpResponse != null) {

					HttpEntity mHttpEntity = mHttpResponse.getEntity();
					InputStream mInputStream = mHttpEntity.getContent();
					if (mInputStream != null) {
						String mPostResult = mSERENApplication
								.convertStreamToString(mInputStream);
						if (mPostResult != null) {

							Log.d("POSTASYNC", "Response Message : "
									+ mPostResult);
							return mPostResult;
						} else {
							return null;
						}
					} else {
						return "Network_Error";
					}
				} else {
					return "Network_Error";
				}

			} catch (Exception e) {
				e.printStackTrace();
				Log.d("POSTASYNC", " JSONException Post ");
			}
			return null;
		}
	}

	/**
	 * This Method is called after the execution finish
	 */
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		// ProgressHUD.hideProgressDialog(mContext);
		if (isShowProgressDialog && newprogress.isShowing()) {
			newprogress.dismiss();
		}
		if (result != null) {
			if (result.equalsIgnoreCase("No_Internet_Connection")) {
				CheckInternet.showAlert(mContext);
			} else {
				this.mOnReciveServerResponse.setOnReciveResult(str_PostApiName,
						result);
			}
		}
	}
}