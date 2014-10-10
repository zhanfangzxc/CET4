/**
 * Title：RequestCallBack.java
 * Copyright:云电同方科技有限公司
 * Company：云电同方科技有限公司
 */
package com.yingshibao.cet4.util;

/**
 * @author hwm
 * 2014-1-12
 * 1.0
 */
public interface MyRequestCallBack {
	 public void onFailure(Exception error);
	 public void onSuccess(String result);
}
