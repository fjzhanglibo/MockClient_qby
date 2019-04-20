package com.lsq.httpclient.wg;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.lsq.httpclient.netpay.TestUtil;
import com.payment.gateway.common.constant.Constant;
import com.payment.gateway.common.util.StringUtil;
import com.payment.gateway.tools.GatewayRequest;
import com.payment.gateway.tools.HttpPostUtil;

public class PayC {
	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		HttpPostUtil postUtil = new HttpPostUtil();
		String params = "";
		String respStr = null;
		GatewayRequest gatewayRequest = new GatewayRequest(null, null,0);
		
		gatewayRequest.setGatewayUrl(TestUtil.wg_url+"backStageEntry.do");
        gatewayRequest.setParameter("version","2.0.0");
//		gatewayRequest.setGatewayUrl("http://localhost:8080/payment-gateway/backStageEntry.do");
		gatewayRequest.setParameter("busi_code","PAYC");
//		gatewayRequest.setParameter("busi_code","CLOSE_ORDER");
//		gatewayRequest.setParameter("merchant_no","549034459460001");
//		gatewayRequest.setParameter("terminal_no","20000262");
//		gatewayRequest.setKey("33c17a7d5c91a908025d9c14d6894104");
//		gatewayRequest.setParameter("merchant_no","000158120120");
//        gatewayRequest.setParameter("terminal_no","20000067");
//        gatewayRequest.setParameter("child_merchant_no",TestUtil.childMerchantId);
//        gatewayRequest.setKey("e859eb838c95e1042bae9e44796e4d63");
		
		
		gatewayRequest.setParameter("merchant_no",TestUtil.merchantId);
      gatewayRequest.setParameter("terminal_no",TestUtil.terminalId);
      gatewayRequest.setKey(TestUtil.key);
		
		gatewayRequest.setParameter("order_no",DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
		gatewayRequest.setParameter("ori_order_no","20190103234052190");
		//gatewayRequest.setParameter("amount","11");
		gatewayRequest.setParameter("notify_url","http://localhost:8082/entry.do");
		//gatewayRequest.setParameter("sign_type",payTimeStr);
		gatewayRequest.setParameter("sign_type",Constant.GATEWAY_SIGN_TYPE_SHA256);
		
		String requestUrl = gatewayRequest.getRequestURL();
		params = postUtil.getQueryString(requestUrl);
		String callUrl = TestUtil.wg_url+"backStageEntry.do";
//		String callUrl = "http://localhost:8080/payment-gateway/backStageEntry.do";
		System.out.println("异步通知:" +callUrl+ params);
		if (callUrl.indexOf("http://") != -1) {
			respStr = postUtil.postHttp(callUrl, params, "GET");
		} else if (callUrl.indexOf("https://") != -1) {
			respStr = postUtil.postHttps(callUrl, params, "POST");
		} else {
			System.out.println("异步通知地址无效");
		}
		System.out.println("异步通知返回结果:" + respStr);
	}
}
