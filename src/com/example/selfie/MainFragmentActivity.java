package com.example.selfie;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.selfie.fragments.BottomFragment;
import com.example.selfie.fragments.TopFragment;

public class MainFragmentActivity extends FragmentActivity {

	// private FrameLayout frameLayout_Top, frameLayout_Bottom;
	public static BottomFragment mBottomFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_fragment);
		// frameLayout_Top = (FrameLayout) findViewById(R.id.frameLayout_Top);
		// frameLayout_Bottom = (FrameLayout)
		// findViewById(R.id.frameLayout_Bottom);
		mBottomFragment = new BottomFragment();

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frameLayout_Top, new TopFragment()/* , "" */)
				.addToBackStack("TopFragment").commit();

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frameLayout_Bottom, mBottomFragment/* , "" */)
				.addToBackStack("BottomFragment").commit();
	}
}