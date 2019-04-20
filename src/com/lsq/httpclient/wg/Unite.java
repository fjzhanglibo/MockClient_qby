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

public class Unite {
	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		HttpPostUtil postUtil = new HttpPostUtil();
		String params = "";
		String respStr = null;
		GatewayRequest gatewayRequest = new GatewayRequest(null, null,0);
		
//		gatewayRequest.setKey("857e6g8y51b5k365f7v954s50u24h14w");
//        gatewayRequest.setKey("f6a4d6bf31c9c26b04973fe1a562838e");
		
		gatewayRequest.setKey("ca862078adcc6da769bd5bd8f57cbde2");
		
		gatewayRequest.setGatewayUrl(TestUtil1.reqUrl);
//		gatewayRequest.setGatewayUrl("http://localhost:8080/payment-gateway/backStageEntry.do");
		gatewayRequest.setParameter("busi_code","UNITE_PAY");
//		gatewayRequest.setParameter("merchant_no","102100000125");
//		gatewayRequest.setParameter("terminal_no","20000147");
//        gatewayRequest.setParameter("merchant_no","549440148160074");
//        gatewayRequest.setParameter("terminal_no","21235563");
		
		
		gatewayRequest.setParameter("merchant_no","549034455310001");
        gatewayRequest.setParameter("terminal_no","20000598");
		
		gatewayRequest.setParameter("order_no",DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
//		gatewayRequest.setParameter("bank_code","APPWECHAT");
//		gatewayRequest.setParameter("bank_code","PUBLICWECHAT");
//		gatewayRequest.setParameter("bank_code","ALIPAY");
		gatewayRequest.setParameter("bank_code","UNION_APP");
		gatewayRequest.setParameter("amount","1");
//		gatewayRequest.setParameter("auth_code","280448163006692512");
		gatewayRequest.setParameter("currency_type","CNY");
		gatewayRequest.setParameter("sett_currency_type","CNY");
		gatewayRequest.setParameter("product_name","iPhone18");
		gatewayRequest.setParameter("product_desc","未来产品");
		
		gatewayRequest.setParameter("notify_url",TestUtil1.reqUrl);
		gatewayRequest.setParameter("sign_type",Constant.GATEWAY_SIGN_TYPE_SHA256);
		
		String requestUrl = gatewayRequest.getRequestURL();
		params = postUtil.getQueryString(requestUrl);
		String callUrl = TestUtil1.reqUrl;
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
