package com.lsq.httpclient.wg;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.RandomUtils;

import com.payment.gateway.common.constant.Constant;
import com.payment.gateway.common.util.StringUtil;
import com.payment.gateway.tools.GatewayRequest;
import com.payment.gateway.tools.HttpPostUtil;
import com.payment.gateway.tools.ResponseHelper;

public class Front {
	public static void main(String[] args) throws Exception {
		HttpPostUtil postUtil = new HttpPostUtil();
		String params = "";
		String respStr = null;
		for(int i=0;i<1;i++){
			GatewayRequest gatewayRequest = new GatewayRequest(null, null,0);
			String key = "857e6g8y51b5k365f7v954s50u24h14w";
			gatewayRequest.setKey(key);
			gatewayRequest.setGatewayUrl(TestUtil.reqUrl);
//		gatewayRequest.setGatewayUrl("http://localhost:8080/payment-gateway/backStageEntry.do");
			gatewayRequest.setParameter("busi_code","FRONT_PAY");
			gatewayRequest.setParameter("merchant_no","102100000125");
			gatewayRequest.setParameter("terminal_no","20000147");
			
			gatewayRequest.setParameter("order_no",DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+RandomUtils.nextInt(10, 1000));
//			gatewayRequest.setParameter("bank_code","ALIPAY");
		gatewayRequest.setParameter("bank_code","WECHAT");
			gatewayRequest.setParameter("amount","0.01");
//		gatewayRequest.setParameter("auth_code","280448163006692512");
			gatewayRequest.setParameter("currency_type","CNY");
			gatewayRequest.setParameter("sett_currency_type","CNY");
			gatewayRequest.setParameter("product_name","iPhone18测#试");
			gatewayRequest.setParameter("product_desc","未来产品");
			
			gatewayRequest.setParameter("notify_url","https://epay.gaohuitong.com:8443/staging/notifyUrl.jsp");
			gatewayRequest.setParameter("sign_type",Constant.GATEWAY_SIGN_TYPE_SHA256);
			
			String requestUrl = gatewayRequest.getRequestURL();
			/*params = postUtil.getQueryString(requestUrl);
		String callUrl = TestUtil.reqUrl;
//		String callUrl = "http://localhost:8080/payment-gateway/backStageEntry.do";
		System.out.println("异步通知:" +callUrl+ params);
		if (callUrl.indexOf("http://") != -1) {
			respStr = postUtil.postHttp(callUrl, params);
		} else if (callUrl.indexOf("https://") != -1) {
			respStr = postUtil.postHttps(callUrl, params);
		} else {
			System.out.println("异步通知地址无效");
		}
		System.out.println("异步通知返回结果:" + respStr);*/
			
			//应答对象
			ResponseHelper resHelper = new ResponseHelper();
			
			
			//后台调用
			if(postUtil.postRequest(requestUrl)) {//发送post请求并将返回的结果赋值给 postUtil的ResContent
				String xml = postUtil.getResContent();
				if(xml!=null&&!xml.startsWith("<?xml version=")){
					System.out.println(xml);
				}else{
					resHelper.setContent(xml); //setContent(xml)并解析XML内容，得到map
					resHelper.setKey(key);
					
					//获取返回码
					String resp_code = resHelper.getParameter("resp_code");
					
					//判断签名及结果
					if(resHelper.verifySign()&& "00".equals(resp_code)) {
						System.out.println("订单查询成功</br>");
						System.out.println("查询接口返回xml报文：</br>"+xml.replaceAll("<","&lt;").replaceAll(">","&gt;")+"</br></br>");
						System.out.println("xml解析后的支付结果：</br>");
						
						/*if (StringUtils.isBlank(resHelper.getParameter("qr_code"))) {
							System.out.println("================================================");
							break;
						}*/
						
						
						//  }
					} else {
						//错误时，返回结果未签名，记录resp_code、resp_desc看失败详情。
						System.out.println("验证签名失败或业务错误<br/>");
						System.out.println("resp_code:" + resHelper.getParameter("resp_code")+" resp_desc:" + resHelper.getParameter("resp_desc"));
						System.out.println("验证签名失败或业务错误");
						System.out.println("resp_code:" + resHelper.getParameter("resp_code")+" resp_desc:" + resHelper.getParameter("resp_desc"));
					}	
				}
			} else {
				System.out.println("后台调用通信失败");
				System.out.println("后台调用通信失败");   	
				System.out.println(postUtil.getResContent());
				//有可能因为网络原因，请求已经处理，但未收到应答。
			}
			
		}
		
		
	}
}
