package com.yingshibao;

import com.yingshibao.bean.BaseBean;
import com.yingshibao.util.Constants;
import com.yingshibao.util.UIUtil;

public class YingShiBaoException extends Exception {
	private static final long serialVersionUID = 6241352744004862941L;

	private BaseBean mBaseBean;

	public YingShiBaoException(BaseBean bean) {
		mBaseBean = bean;
	}

	public void showErrorToast() {
		switch (mBaseBean.getErrorCode()) {
		// case Constants.ERROR_YES:
		// UIUtil.showShortToast("失败");
		// break;
		case Constants.ERROR_USERNAME_INVALID:
			UIUtil.showShortToast("无效帐号");
			break;
		case Constants.ERROR_USERNAME_USED:
			UIUtil.showShortToast("帐号被占用");
			break;
		case Constants.ERROR_MAIL_INVALID:
			UIUtil.showShortToast("无效邮箱");
			break;
		case Constants.ERROR_MAIL_USED:
			UIUtil.showShortToast("邮箱被占用");
			break;
		case Constants.ERROR_USERNAME_PASSWORD_SAME:
			UIUtil.showShortToast("帐号和密码不一致");
			break;
		case Constants.ERROR_TOKEN_INVALID:
			UIUtil.showShortToast("无效会话");
			break;
		case Constants.ERROR_DECRYPT:
			UIUtil.showShortToast("解密数据出错");
			break;
		case Constants.ERROR_ENCRYPT:
			UIUtil.showShortToast("加密数据出错");
			break;
		case Constants.ERROR_PASSWORD_INVALID:
			UIUtil.showShortToast("密码无效");
			break;
		case Constants.ERROR_DATA_RELATION_INVALID:
			UIUtil.showShortToast("数据关系无效");
			break;
		case Constants.ERROR_REQUEST_OVERFLOW:
			UIUtil.showShortToast("请求数据过多");
			break;

		default:
			break;
		}

	}
}
