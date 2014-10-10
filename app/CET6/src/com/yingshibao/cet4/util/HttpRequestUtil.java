package com.yingshibao.cet4.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import com.yingshibao.cet4.AppConfig;

public class HttpRequestUtil {

	private byte[] sendPostRequestBytes(String requestURL, byte[] params,
			String encode, int REQUEST_TIME_OUT, int Conect_TIME_OUT)
			throws Exception {
		InputStream inputStream = null;
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams,
				REQUEST_TIME_OUT * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, Conect_TIME_OUT * 1000);
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		HttpPost httpPost = new HttpPost(requestURL);
		httpPost.setHeader("Charset", encode);
		httpPost.setHeader("Content-Type", "application/json");
		byte[] result = null;
		httpPost.setEntity(new ByteArrayEntity(params));
		HttpResponse response = httpClient.execute(httpPost);
		response.setHeader("Charset", "UTF-8");
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			inputStream = entity.getContent();
			result = readInputStream(inputStream);
		}
		return result;
	}

	private byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	/**
	 * 
	 * @param requestURL
	 * @param params
	 * @param callback
	 */
	public void send(final String requestURL, final byte[] params,
			final MyRequestCallBack callback) {
		new Thread(new Runnable() {
			public void run() {
				try {
					byte[] result = sendPostRequestBytes(requestURL, params,
							"UTF-8", 30, 30);
					String resultStr = DESUtil.decryptDESBytes(result,
							AppConfig.DES_KEY);
					callback.onSuccess(resultStr);
				} catch (Exception e) {
					callback.onFailure(e);
				}
			}
		}).start();
	}

	/**
	 * 
	 * @param requestURL
	 * @param params
	 * @param callback
	 */
	public void sendNoEcrypt(final String requestURL, final byte[] params,
			final MyRequestCallBack callback) {
		new Thread(new Runnable() {
			public void run() {
				try {
					byte[] result = sendPostRequestBytes(requestURL, params,
							"UTF-8", 30, 30);
					String resultStr = new String(result, "UTF-8");
					callback.onSuccess(resultStr);
				} catch (Exception e) {
					callback.onFailure(e);
				}
			}
		}).start();
	}
}
