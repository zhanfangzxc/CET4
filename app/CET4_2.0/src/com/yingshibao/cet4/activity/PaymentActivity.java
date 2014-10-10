package com.yingshibao.cet4.activity;

import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.alipay.android.app.sdk.AliPay;
import com.google.gson.Gson;
import com.yingshibao.AppContext;
import com.yingshibao.api.PaymentApi;
import com.yingshibao.api.PaymentListener;
import com.yingshibao.bean.Order;
import com.yingshibao.cet4.R;
import com.yingshibao.cet4.constans.Keys;
import com.yingshibao.cet4.constans.Result;
import com.yingshibao.cet4.constans.Rsa;
import com.yingshibao.util.Constants;

public class PaymentActivity extends BaseActivity implements PaymentListener {

	@InjectView(R.id.pay_tv)
	TextView pay_tv;
	@InjectView(R.id.pay_btn)
	Button pay_btn;
	private PaymentApi api;

	private static final int RQF_PAY = 1;

	private static final int RQF_LOGIN = 2;
	private String orderId;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Result result = new Result((String) msg.obj);

			switch (msg.what) {
			case RQF_PAY:
				pay_tv.setText(result.toString());
			case RQF_LOGIN: {
				Toast.makeText(PaymentActivity.this, result.getResult(),
						Toast.LENGTH_SHORT).show();
			}
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		ButterKnife.inject(this);
		api = new PaymentApi(this);
		api.setmPaymentListener(this);
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("token", AppContext.getInstance().getUserInfo().getToken());
		map.put("fee", "0.01");
		api.payment(map);
		// initView();
	}

	public void initView() {
		// actionBarTitle.setText("课程支付");
	}

	@OnClick(R.id.pay_btn)
	public void payment() {
		String info = getOrderInfo();
		String sign = Rsa.sign(info, Keys.PRIVATE);
		sign = URLEncoder.encode(sign);
		info += "&sign=\"" + sign + "\"&" + getSignType();
		final String orderInfo = info;
		new Thread() {
			public void run() {
				AliPay alipay = new AliPay(PaymentActivity.this, mHandler);

				// 设置为沙箱模式，不设置默认为线上环境
				// alipay.setSandBox(true);

				String result = alipay.pay(orderInfo);
				Message msg = new Message();
				msg.what = RQF_PAY;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		}.start();
	}

	// 获取签名方式
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

	// 获取订单编号
	@Override
	public void pay(String str) {
		Gson gson = AppContext.getGson();
		Order orderInfo = gson.fromJson(str, Order.class);
		if (orderInfo.getErrorCode() == Constants.ERROR_NO && orderInfo != null) {
			orderId = orderInfo.getTradeId();
		} else {
			orderId = null;
		}

	}

	// 获取订单信息
	public String getOrderInfo() {
		String orderInfo = "";
		if (orderId != null) {
			@SuppressWarnings("deprecation")
			String notify_url = URLEncoder
					.encode("http://www.yingshibao.com:8080/wwyj/wsjson/v2/alipaycallback");
			orderInfo = "partner=\"2088411049478088\"&out_trade_no=\""
					+ orderId
					+ "\"&subject=\"英语四六级翻译2M15\"&body=\"英语四六级翻译英语四六级翻译英语四六级翻译 英语四六级翻译2M15\"&total_fee=\"0.01\"&notify_url=\""
					+ notify_url
					+ "\"&service=\"mobile.securitypay.pay\"&_input_charset=\"UTF-8\"&return_url=\"http%3A%2F%2Fm.alipay.com\"&payment_type=\"1\"&seller_id=\"liubin@yingshibao.com\"&it_b_pay=\"1m\"";
		}
		return orderInfo;
	}

}
