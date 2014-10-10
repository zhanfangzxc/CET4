package com.yingshibao.cet4.activity;

import com.desarrollodroide.twopanels.R;
import com.desarrollodroide.twopanels.TwoPanelsBaseActivity;
import com.yingshibao.cet4.fragment.LeftPracticeFragment;
import com.yingshibao.cet4.fragment.RightPracticeFragment;



import android.os.Bundle;
import android.widget.LinearLayout;

public class PracticeActivity extends TwoPanelsBaseActivity {

	protected String str = "���";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseOrientation(LinearLayout.VERTICAL);
		RightPracticeFragment mRightPracticeFragment = new RightPracticeFragment();
		LeftPracticeFragment mLeftPracticeFragment = new LeftPracticeFragment();
		getFragmentManager().beginTransaction().add(R.id.right, mRightPracticeFragment);
		getFragmentManager().beginTransaction().add(R.id.left, mLeftPracticeFragment);
	}
	
}
