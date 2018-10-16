package com.example.selfie.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.selfie.MainFragmentActivity;
import com.example.selfie.R;

public class TopFragment extends Fragment implements View.OnClickListener {

	private View mRootView;
	private Button btnFirst, btnSecond;
	// private TestClickListener mTestClickListener;
	private BottomFragment mBottomFragment;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.top_fragment, container, false);

		btnFirst = (Button) mRootView.findViewById(R.id.btnFirst);
		btnFirst.setOnClickListener(this);

		btnSecond = (Button) mRootView.findViewById(R.id.btnSecond);
		btnSecond.setOnClickListener(this);

		// mBottomFragment = new BottomFragment();// TODO check this

		return mRootView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btnFirst:
			MainFragmentActivity.mBottomFragment.onTestClick(R.id.btnFirst);
			break;

		case R.id.btnSecond:
			MainFragmentActivity.mBottomFragment.onTestClick(R.id.btnSecond);
			break;
		}
	}
}