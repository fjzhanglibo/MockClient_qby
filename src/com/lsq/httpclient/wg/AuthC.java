package com.lsq.httpclient.wg;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.payment.gateway.common.constant.Constant;
import com.payment.gateway.common.util.StringUtil;
import com.payment.gateway.tools.GatewayRequest;
import com.payment.gateway.tools.HttpPostUtil;

public class AuthC {
	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		HttpPostUtil postUtil = new HttpPostUtil();
		String params = "";
		String respStr = null;
		GatewayRequest gatewayRequest = new GatewayRequest(null, null,0);
		
		gatewayRequest.setKey("857e6g8y51b5k365f7v954s50u24h14w");
		gatewayRequest.setGatewayUrl(TestUtil.reqUrl);
//		gatewayRequest.setGatewayUrl("http://localhost:8080/payment-gateway/backStageEntry.do");
		gatewayRequest.setParameter("busi_code","AUTHC");
		gatewayRequest.setParameter("merchant_no","102100000125");
		gatewayRequest.setParameter("terminal_no","20000147");
		
		gatewayRequest.setParameter("order_no",DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
		gatewayRequest.setParameter("ori_order_no","1473510604802");
		//gatewayRequest.setParameter("amount","11");
		gatewayRequest.setParameter("notify_url","http://localhost:8082/entry.do");
		//gatewayRequest.setParameter("sign_type",payTimeStr);
		gatewayRequest.setParameter("sign_type",Constant.GATEWAY_SIGN_TYPE_SHA256);
		
		String requestUrl = gatewayRequest.getRequestURL();
		params = postUtil.getQueryString(requestUrl);
		String callUrl = TestUtil.reqUrl;
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