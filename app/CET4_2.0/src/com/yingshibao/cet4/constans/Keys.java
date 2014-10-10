/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.yingshibao.cet4.constans;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

	//合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088411049478088";

	//收款支付宝账号
	public static final String DEFAULT_SELLER = "liubin@yingshibao.com";

	//商户私钥，自助生成
	public static final String PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOIpkBQB4Y43MkcOzPKIJepFYtp6sPXXIalGzBfP88luilu0iMTX2aTdYFR9k7+JAlzmzqL3bYSVPvS36cwQ3v8UtmiRruVJxltKQY4Mgcy/l7jBUk2ljwF61Y+ESjKG5/NVQbr6Sc14d+zlYy5XWJZDC3pHz/tkoWNLTEmsSU2DAgMBAAECgYBV+BachFqJBNJH0Ih3aOmAs/9vrVO3H5/cCN1r79BrsWlGksmqiaJh2QTVGScgl6QeTF4f9I3c6B6WR5LHz2H0PahYUwUwSNkURXjUetARGnXF2mGoF2MtyFVZdwMNZ8m+F/WCa6dxJBCbUyRs6Nr2tE/stM+1SQxsOw4A/mUIAQJBAPP656PIjWjeT0n6Eb1cknIF+TNIgK2FWPjM1/0B31cDlMHSIBEw0GtFyG3eljeenjMU5uvkylwlXz/pZR+sp0ECQQDtTfBz7TcD1Nboofphg3tN/e69zpWhmiESsLwBUSh9wMtnVa9JWHyvyb4o7ahD0VIr2KQUwyJPVNRzW7LuiyfDAkASnh3ZAK/Rxc0VHxEFey/tpYkLoce39goBClidBOyPDkXmuGscBLaSyQNvZuHdoqv9x+WLMK1EdNGc5emCjkFBAkAbmULr2JzbXzeXWq9KBAalLXFEtfAFGW3bRJ648H1rEq8VV4vJVfc6YpYxIGK/cGrEGrAKkh89ZrpGqFWJFLoNAkEAgFWR4+0qLas4rayQnmfSxKwXn0b5xofMObLSZP/ww1ZT62K+StpFDLqiWIZU9W/ogLDWBHx+Q0Wh44nTGjIwBA==";

	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

}
