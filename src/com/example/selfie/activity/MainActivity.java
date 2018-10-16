package com.example.selfie.activity;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.selfie.R;
import com.example.selfie.adapter.Listing_ListView_Adapter;
import com.example.selfie.constants.SEREN_SharedPreferences;
import com.example.selfie.constants.ServerRequestConstants;
import com.example.selfie.model.ListingResponse_Model;
import com.example.selfie.parsing.LMSCParser;
import com.example.selfie.utilities.CheckInternet;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class MainActivity extends Activity {
	private ListView lv_listing;
	private TextView txt_Facebook;
	// private View mView;
	private Listing_ListView_Adapter mListing_ListView_Adapter;
	private ArrayList<ListingResponse_Model> arraylist_ListingResponse_Model;

	Seren_Application mSeren_Application;
	SEREN_SharedPreferences mSeren_SharedPreferences;
	// private String facebook_userId;
	private String facebook_access_token;
	private Facebook facebook;
	private AsyncFacebookRunner mAsyncRunner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		mSeren_Application = new Seren_Application();
		mSeren_SharedPreferences = new SEREN_SharedPreferences(
				MainActivity.this, null);
		// app_userId = mSeren_SharedPreferences.getStringValue(
		// SEREN_Constants.Seren_UserID, null);
		facebook = new Facebook(ServerRequestConstants.FACEBOOK_APP_ID);
		mAsyncRunner = new AsyncFacebookRunner(facebook);
		Log.d("test", "onCreateView()");

		lv_listing = (ListView) findViewById(R.id.lv_listing);
		lv_listing.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Log.d("test", "position" + position);
				if (arraylist_ListingResponse_Model != null) {
					Log.d("test", "video:"
							+ arraylist_ListingResponse_Model.get(position)
									.getVideo_url());

					for (int i = 0; i < arraylist_ListingResponse_Model.size(); i++) {
						arraylist_ListingResponse_Model.get(position)
								.setIsVideoVisible("0");
					}
					arraylist_ListingResponse_Model.get(position)
							.setIsVideoVisible("1");

					// lv_listing.invalidate();//TODO
					// mListing_ListView_Adapter.notifyDataSetChanged();
				} else
					Log.d("test", "al null");
			}
		});
		txt_Facebook = (TextView) findViewById(R.id.txt_Facebook);
		txt_Facebook.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loginToFacebook();
			}
		});

		// facebook_userId = mSeren_SharedPreferences.getStringValue(
		// ServerRequestConstants.Facebook_UserID, null);
		// if (facebook_userId != null)
		// Log.d("test", "facebook_userId is" + facebook_userId);
	}

	@SuppressWarnings("deprecation")
	public void loginToFacebook() {
		// mSeren_SharedPreferences = getPreferences(MODE_PRIVATE);
		String access_token = mSeren_SharedPreferences.getStringValue(
				"access_token", null);
		long expires = Long.valueOf(mSeren_SharedPreferences.getStringValue(
				"access_expires", "0"));

		if (access_token != null) {
			facebook.setAccessToken(access_token);
		}

		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}

		if (!facebook.isSessionValid()) {
			facebook.authorize(this, null, new DialogListener() {

				@Override
				public void onCancel() {
					// Function to handle cancel event
				}

				@Override
				public void onComplete(Bundle values) {
					// Function to handle complete event
					// Edit Preferences and update facebook acess_token
					// SharedPreferences.Editor editor = mPrefs.edit();
					mSeren_SharedPreferences.putStringValue("access_token",
							facebook.getAccessToken());
					mSeren_SharedPreferences.putStringValue("access_expires",
							"" + facebook.getAccessExpires());
					// mSeren_SharedPreferences.commit();
				}

				@Override
				public void onError(DialogError error) {
					// Function to handle error

				}

				@Override
				public void onFacebookError(FacebookError fberror) {
					// Function to handle Facebook errors
				}
			});
		}
	}

	private void asyncListing() {
		new AsyncTask<String, Void, String>() {

			private ProgressDialog newprogress;
			private String listing_url = "http://www.ykotakselfiedev.appspot.com/api/connect-fb.json";
			private String listing_param_accessToken = "accessToken";

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				// ProgressHUD.show(mContext,true);

				// if (isShowProgressDialog) {
				newprogress = new ProgressDialog(MainActivity.this);
				newprogress.setMessage("Loading...");
				newprogress.setCancelable(false);
				if (!newprogress.isShowing()) {
					newprogress.show();
				}
				// }
			}

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				HttpClient mHttpClient = new DefaultHttpClient();
				HttpContext mHttpContext = new BasicHttpContext();
				HttpPost mHttpPost = new HttpPost(listing_url);

				MultipartEntity mMultipartEntity = new MultipartEntity(
						HttpMultipartMode.BROWSER_COMPATIBLE);
				try {
					mMultipartEntity.addPart(listing_param_accessToken,
							new StringBody((String) facebook_access_token));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				Log.d("post", "post param => " + facebook_access_token);
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
				try {
					if (mHttpResponse != null) {

						HttpEntity mHttpEntity = mHttpResponse.getEntity();
						InputStream mInputStream = mHttpEntity.getContent();
						if (mInputStream != null) {
							String mPostResult = mSeren_Application
									.convertStreamToString(mInputStream);
							if (mPostResult != null) {

								Log.d("POSTASYNC", "Response Message : "
										+ mPostResult);
								arraylist_ListingResponse_Model = new ArrayList<ListingResponse_Model>();
								LMSCParser mLmscParser_Country = new LMSCParser(
										MainActivity.this, mPostResult);
								mLmscParser_Country
										.listingResponseParsing(arraylist_ListingResponse_Model);
								Log.d("POSTASYNC",
										"after parsing in activity arraylist_ListingResponse_Model.size():"
												+ arraylist_ListingResponse_Model
														.size());

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
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				// ProgressHUD.hideProgressDialog(mContext);
				if (newprogress.isShowing()) {
					newprogress.dismiss();
				}
				if (result != null) {
					if (result.equalsIgnoreCase("No_Internet_Connection")) {
						CheckInternet.showAlert(MainActivity.this);
					} else {
						// this.mOnReciveServerResponse.setOnReciveResult(
						// str_PostApiName, result);// notify adapter
						if (arraylist_ListingResponse_Model != null
								&& arraylist_ListingResponse_Model.size() > 0) {
							mListing_ListView_Adapter = new Listing_ListView_Adapter(
									MainActivity.this,
									arraylist_ListingResponse_Model);
							lv_listing.setAdapter(mListing_ListView_Adapter);
						} else {
							Log.d("test", "in post execute al null");
						}
					}
				}
			}
		};
	}
	// @Override
	// public View onCreateView(String name, Context context, AttributeSet
	// attrs) {
	// //
	// // /*** ? try this if no permission want to set. */
	// // Session.openActiveSession(this, true, new Session.StatusCallback() {
	// //
	// // // callback when session changes state
	// // @Override
	// // public void call(Session session, SessionState state,
	// // Exception exception) {
	// // if (session.isOpened()) {
	// //
	// // // make request to the /me API
	// // Request.newMeRequest(session,
	// // new Request.GraphUserCallback() {
	// //
	// // // callback after Graph API response with user
	// // // object
	// // @Override
	// // public void onCompleted(GraphUser user,
	// // Response response) {
	// // if (user != null) {
	// // doSomethingfUNC(user);
	// // }
	// // }
	// // }).executeAsync();
	// // }
	// // }
	// // });
	// // ///////
	// return mView;
	// }

	// public void doSomethingfUNC(GraphUser user) {
	// /*** Getting User for fetching user info */
	//
	// Log.d("FACE",
	// "USERNAME:" + user.getName() + "  BIRTHDAY:"
	// + user.getBirthday() + "  ID" + user.getId() + " LINK"
	// + user.getLink() + "  " + user.getProperty("email")
	// + " ;;;; " + user.toString());
	// facebook_user_id = user.getId();
	// // mSeren_SharedPreferences = new SEREN_SharedPreferences(
	// // Facebook_Login.this, null);ServerRequestConstants.Facebook_UserID
	// mSeren_SharedPreferences.putStringValue(
	// ServerRequestConstants.Facebook_UserID, facebook_user_id);
	//
	// // if (isFromLogin) {
	// // requestFacebookAsync(facebook_user_id); // from login page
	// // // }
	// // } else {
	// // requestAsync(); // from setting page
	// // }
	// }
}
// DLtAk+EBAD2CnubIqq1C3r+kFYk= // Hash key for facebook home
// FACEBOOK_APP_ID