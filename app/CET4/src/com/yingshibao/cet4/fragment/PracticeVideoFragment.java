package com.yingshibao.cet4.fragment;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.umeng.analytics.MobclickAgent;
import com.yingshibao.cet4.R;
import com.yingshibao.cet4.util.FileUtil;

public class PracticeVideoFragment extends Fragment {
	private View view;
	private String vediopath = "http://www.55youjiao.com/uploadfile/resources/listen/voice/sp/10103.mp4";
	private String saveVediopath = "";

	private VideoView videoView;
	// private ProgressDialog progress;
	private ProgressDialogFragment pdFragment;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				videoView.setVideoURI(Uri.parse(vediopath));
				videoView.start();
				videoView.requestFocus();
				// progress.dismiss();
				dimissDialog();
				break;
			case 2:
				videoView.setVideoURI(Uri.parse(saveVediopath));
				videoView.start();
				dimissDialog();
				// progress.dismiss();
				break;
			case 3:

				break;
			case -1:
				// progress.dismiss();
				dimissDialog();
				if (saveVediopath != null && saveVediopath.length() > 0) {
					File file = new File(saveVediopath);// 视频文件
					if (!file.exists()) {// 无效文件
						file.delete();
					}
				}
				Toast.makeText(getActivity().getApplicationContext(),
						"无法加载视频！", Toast.LENGTH_LONG).show();
				break;
			case -2:
				dimissDialog();
				Toast.makeText(getActivity().getApplicationContext(),
						"该题型无相关视频！", Toast.LENGTH_LONG).show();
				// progress.dismiss();
				break;
			default:
				break;
			}
		}
	};

	public PracticeVideoFragment() {
	}

	public static PracticeVideoFragment instance(Bundle bundle) {
		PracticeVideoFragment vFragment = new PracticeVideoFragment();
		vFragment.setArguments(bundle);
		return vFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.view = inflater.inflate(R.layout.practivevedio_fragment,
				container, false);
		String vediourl = getArguments().getString("vediourl");
		this.videoView = (VideoView) this.view.findViewById(R.id.vedioView);
		if (vediourl != null && vediourl.length() > 0) {
			vediopath = vediourl.replace("\\", "/");
		}
		videoView.setMediaController(new MediaController(getActivity()));
		// progress = new ProgressDialog(getActivity());
		// progress.setMessage("正在加载，请稍后...");
		initialData();
		getActivity().getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
						| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
						| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		return this.view;
	}

	protected void initialData() {
		// progress.show();
		// progress.setCancelable(false);
		showProgressDialog();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					if (vediopath != null && vediopath.length() > 0) {
						File file = new File(saveVediopath);//
						if (!file.exists() && FileUtil.existSDCard()) {//
							handler.sendEmptyMessage(1);
						} else {
							handler.sendEmptyMessage(2);
						}
					} else {
						handler.sendEmptyMessage(-2);
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(-1);
					e.printStackTrace();
					Log.i("VedioFragment", "" + e.getMessage());
				}
			}
		}).start();
	}

	private void showProgressDialog() {
		FragmentTransaction ft = getActivity().getSupportFragmentManager()
				.beginTransaction();
		Bundle bundle = new Bundle();
		bundle.putString("msg", "正在加载，请稍候...");
		pdFragment = ProgressDialogFragment.instance(bundle);
		pdFragment.show(ft, "dialog");
	}

	/**
	 * dimissDialog 取消对话框
	 */
	private void dimissDialog() {
		if (pdFragment != null)
			pdFragment.dismiss();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("MainScreen"); // 统计页面
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}
}
