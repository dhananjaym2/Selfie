package com.example.selfie.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.selfie.R;
import com.example.selfie.model.ListingResponse_Model;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class Listing_ListView_Adapter extends BaseAdapter {

	private ArrayList<ListingResponse_Model> al_ListingResponse_Model;
	private Context mContext;
	private LayoutInflater inflater;
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;

	// private int griditem_Width;

	public Listing_ListView_Adapter(Context mContext,
			ArrayList<ListingResponse_Model> al_ListingResponse_Model) {
		Log.d("test", "in Listing_ListView_Adapter()");
		this.mContext = mContext;
		this.al_ListingResponse_Model = al_ListingResponse_Model;

		mImageLoader = ImageLoader.getInstance();
		mDisplayImageOptions = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.placeholder_image)
				.showImageOnFail(R.drawable.placeholder_image)
				.showImageOnLoading(R.drawable.placeholder_image)
				/* dj changes */
				.considerExifParams(true).cacheInMemory(true).cacheOnDisk(true)
				.build();
		// griditem_Width = SEREN_Constants.DEVICE_WIDTH;
		// griditem_Width = (int) ((griditem_Width * 0.9) / 2);
		// SEREN_Debug.Debug(1, "SEREN_Constants.DEVICE_WIDTH:" +
		// griditem_Width);
		// SEREN_Debug.Debug(1, "griditem_Width:" + griditem_Width);
	}

	@Override
	public int getCount() {
		Log.d("test", "al_ListingResponse_Model.size():"
				+ al_ListingResponse_Model.size());
		return al_ListingResponse_Model.size();
	}

	@Override
	public Object getItem(int position) {
		return al_ListingResponse_Model.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
		ImageView imgView_image;
		VideoView videoView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder mViewHolder;
		Log.d("test", "in getView() position:" + position);
		if (convertView == null) {
			inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.list_item_listing, null);

			mViewHolder = new ViewHolder();

			// mViewHolder.parent_rl = (RelativeLayout) convertView
			// .findViewById(R.id.parent_rl_griditem_seeallalbums);

			// mViewHolder.txtAlbumName = (TextView) convertView
			// .findViewById(R.id.txtAlbumName_griditem);

			mViewHolder.imgView_image = (ImageView) convertView
					.findViewById(R.id.imgView_image);

			mViewHolder.videoView = (VideoView) convertView
					.findViewById(R.id.videoView);

			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		Log.d("test", "al_ListingResponse_Model position:" + position
				+ " title :" + al_ListingResponse_Model.get(position).getId()
				+ " image_path():"
				+ al_ListingResponse_Model.get(position).getImage_url());

		/**
		 * setting height width of all views: img, txt and parent_rl
		 */
		// RelativeLayout.LayoutParams mParams = new
		// RelativeLayout.LayoutParams(
		// griditem_Width, griditem_Width);
		// mViewHolder.parent_rl.setLayoutParams(mParams);

		// mViewHolder.imgView_image.getLayoutParams().width = griditem_Width;
		// mViewHolder.imgView_image.getLayoutParams().height = griditem_Width;
		try {
			mImageLoader.displayImage(al_ListingResponse_Model.get(position)
					.getImage_url(), mViewHolder.imgView_image,
					mDisplayImageOptions);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (al_ListingResponse_Model.get(position).getIsVideoVisible()
				.equals("1")) {
			mViewHolder.videoView.setVisibility(View.VISIBLE);
			mViewHolder.imgView_image.setVisibility(View.GONE);
		} else {
			mViewHolder.videoView.setVisibility(View.GONE);
			mViewHolder.imgView_image.setVisibility(View.VISIBLE);
		}

		return convertView;
	}
}// Log.d("test", 