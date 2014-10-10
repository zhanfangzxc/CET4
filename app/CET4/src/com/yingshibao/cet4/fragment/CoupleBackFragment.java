package com.yingshibao.cet4.fragment;

import org.apache.http.entity.ByteArrayEntity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.AppConfig;
import com.yingshibao.cet4.AppContext;
import com.yingshibao.cet4.AppException;
import com.yingshibao.cet4.R;
import com.yingshibao.cet4.constants.Constants;

public class CoupleBackFragment extends Fragment implements OnClickListener {
	private View view;
	/**
	 * back_btn 返回按钮
	 */
	private ImageButton back_btn;
	private EditText content;
	private TextView submit_tv;

	/**
	 * pdFragment 进度对话框
	 */
	private ProgressDialogFragment pdFragment;

	public static CoupleBackFragment instance(Bundle bundle) {
		CoupleBackFragment wgFragment = new CoupleBackFragment();
		wgFragment.setArguments(bundle);
		return wgFragment;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.view = inflater.inflate(R.layout.assess, null);
		this.back_btn = (ImageButton) view.findViewById(R.id.back_btn);
		this.back_btn.setOnClickListener(this);
		this.content = (EditText) view.findViewById(R.id.content);
		this.submit_tv = (TextView) view.findViewById(R.id.submit_tv);
		this.submit_tv.setOnClickListener(this);
		return this.view;
	}

	public void onClick(View v) {

		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		switch (v.getId()) {
		case R.id.back_btn:
			imm.hideSoftInputFromWindow(content.getWindowToken(), 0);
			getActivity().getSupportFragmentManager().popBackStack();
			break;
		case R.id.submit_tv:
			MobclickAgent.onEvent(getActivity(), "提交用户反馈");
			submitInfo();
			imm.hideSoftInputFromWindow(content.getWindowToken(), 0);
			break;
		default:
			break;
		}
	}

	private void submitInfo() {
		String info = this.content.getText().toString();
		if (info == null || info.trim().length() <= 0) {
			Toast.makeText(getActivity().getApplicationContext(), "请输入您的宝贵意见!",
					Toast.LENGTH_LONG).show();
			return;
		}
		try {
			showDialog();
			HttpUtils http = new HttpUtils();
			http.configTimeout(30 * 1000);
			RequestParams params = new RequestParams();
			params.setContentType(AppConfig.CONTENT_TYPE);
			String ticket = AppContext.getInstance().getLoginInfo().getToken();
			byte[] body = AppConfig.assess(ticket, 0, info,
					Integer.parseInt(Constants.LEVEL));
			params.setBodyEntity(new ByteArrayEntity(body));
			http.send(HttpMethod.POST,
					AppConfig.requestURL(AppConfig.coursecomment_action),
					params, new RequestCallBack<String>() {
						@Override
						public void onFailure(HttpException arg0, String arg1) {
							dimissDialog();
							Toast.makeText(
									getActivity().getApplicationContext(),
									"对不起，评价失败!", Toast.LENGTH_LONG).show();
						}

						@Override
						public void onSuccess(ResponseInfo<String> reinfo) {
							Toast.makeText(
									getActivity().getApplicationContext(),
									"谢谢，评价成功!", Toast.LENGTH_LONG).show();
							dimissDialog();
							getActivity().finish();
						}
					});
		} catch (AppException e1) {
			e1.printStackTrace();
			dimissDialog();
			Toast.makeText(getActivity().getApplicationContext(), "对不起，评价失败!",
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * showDialog 显示对话框
	 */
	private void showDialog() {
		FragmentTransaction ft = getActivity().getSupportFragmentManager()
				.beginTransaction();
		Bundle bundle = new Bundle();
		bundle.putString("msg", "正在提交，请稍候...");
		pdFragment = ProgressDialogFragment.instance(bundle);
		pdFragment.show(ft, "dialog");
	}

	/**
	 * dimissDialog 取消对话框
	 */
	private void dimissDialog() {
		if (pdFragment != null) {
			pdFragment.dismiss();
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		getActivity().finish();
	}
}
