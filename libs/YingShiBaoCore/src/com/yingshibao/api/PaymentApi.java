package com.yingshibao.api;

import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yingshibao.util.Constants;
import com.yingshibao.util.LogUtil;

public class PaymentApi extends BaseApi {

	private PaymentListener mPaymentListener;

	public PaymentApi(Context context) {
		super(context);
	}

	public void payment(Map<String, String> info) {
		try {
			String paramsStr = mGson.toJson(info);
			byte[] data = paramsStr.toString().getBytes("UTF-8");
			HttpEntity entity = new ByteArrayEntity(data);
			client.post(context, Constants.STARTTRADE_URL, entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, Header[] headers,
								String content) {
							mPaymentListener.pay(content);

						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							super.onFailure(arg0, arg1, arg2, arg3);

						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e(e.toString());
		}

	}

	public PaymentListener getmPaymentListener() {
		return mPaymentListener;
	}

	public void setmPaymentListener(PaymentListener mPaymentListener) {
		this.mPaymentListener = mPaymentListener;
	}

}
