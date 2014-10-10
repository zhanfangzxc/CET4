package com.yingshibao.cet4.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yingshibao.cet4.R;
import com.yingshibao.cet4.activity.SplashActivity;
import com.yingshibao.util.UIUtil;

/**
 * 引导页
 * 
 * @author zhaoshan
 * 
 */
public class GuideFragment extends Fragment {

	private final static String RES_ID = "res_id";
	private final static String IS_SHOW = "is_show";

	private int resId;
	private boolean isShow;

	public static final GuideFragment newInstance(int resId, boolean isShow) {
		GuideFragment fragment = new GuideFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(RES_ID, resId);
		bundle.putBoolean(IS_SHOW, isShow);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		resId = getArguments().getInt(RES_ID);
		isShow = getArguments().getBoolean(IS_SHOW);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_guide, container, false);
		ImageView guideImageView = UIUtil.getView(view, R.id.iv_guide);
		ImageView startIV = UIUtil.getView(view, R.id.iv_start);
		if (isShow) {
			startIV.setVisibility(View.VISIBLE);

		}
		startIV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.iv_start:
					startActivity(new Intent(getActivity(),
							SplashActivity.class));
					getActivity().finish();
					break;

				default:
					break;
				}

			}
		});
		guideImageView.setImageResource(resId);
		return view;
	}
}
