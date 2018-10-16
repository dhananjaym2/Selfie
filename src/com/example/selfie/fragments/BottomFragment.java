package com.example.selfie.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.selfie.R;
import com.example.selfie.interfaces.TestClickListener;

public class BottomFragment extends Fragment implements TestClickListener {

	private View mRootView;
	private TextView txtFirst, txtSecond;

	// private TestClickListener mTestClickListener;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.top_fragment, container, false);

		txtFirst = (TextView) mRootView.findViewById(R.id.txtFirst);

		txtSecond = (TextView) mRootView.findViewById(R.id.txtSecond);

		// mTestClickListener = this;
		return mRootView;
	}

	@Override
	public void onTestClick(int idClicked) {
		switch (idClicked) {

		case R.id.btnFirst:
			txtFirst.setText("Button \"First\" was clicked in Top Fragment");
			txtSecond.setText("");
			break;

		case R.id.btnSecond:
			txtFirst.setText("");
			txtSecond.setText("Button \"Second\" was clicked in Top Fragment");
			break;
		}
	}
}