package com.lsq.httpclient.wg;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.RandomUtils;

import com.lsq.httpclient.netpay.TestUtil;
import com.payment.gateway.common.constant.Constant;
import com.payment.gateway.common.util.StringUtil;
import com.payment.gateway.tools.GatewayRequest;
import com.payment.gateway.tools.HttpPostUtil;
import com.payment.gateway.tools.ResponseHelper;
import com.sicpay.comm.util.RequestUtil;

public class Front {
	public static void main(String[] args) throws Exception {
	    
	    System.out.println(new Date());
	    
		HttpPostUtil postUtil = new HttpPostUtil();
		String params = "";
		String respStr = null;
		for(int i=0;i<1;i++){
			GatewayRequest gatewayRequest = new GatewayRequest(null, null,0);
			String key = TestUtil.key;
			gatewayRequest.setKey(key);
			gatewayRequest.setGatewayUrl(TestUtil.wg_url+"backStageEntry.do");
			gatewayRequest.setParameter("version","2.0.0");
            gatewayRequest.setParameter("busi_code","FRONT_PAY");//UNITE_PAY|APP_PAY|FRONT_PAY|BACKSTAGE_PAY
			gatewayRequest.setParameter("merchant_no",TestUtil.merchantId);
			gatewayRequest.setParameter("terminal_no",TestUtil.terminalId);
//            gatewayRequest.setParameter("merchant_no","549440148160074");
//            gatewayRequest.setParameter("terminal_no","21235563");
			
//			gatewayRequest.setParameter("merchant_no","549440155510001");
//            gatewayRequest.setParameter("terminal_no","20000277");
//            gatewayRequest.setParameter("child_merchant_no", TestUtil.childMerchantId);
//			gatewayRequest.setParameter("token_id","xbwy9mYsAh2n3IPEVlJzbEFbS1CjV+1q2IbToWfsSHFjG/WtkJHc9XU8iMyKmVg3");
//			gatewayRequest.setParameter("order_no","20180325004518287");
			gatewayRequest.setParameter("order_no",DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+RandomUtils.nextInt(10, 1000));
//            gatewayRequest.setParameter("bank_code","UNIONZS");//ALIPAY|PUBLICALIPAY|PUBLICWECHAT|WECHAT|UNION_APP|APPWECHAT|WAPWECHAT|BACKSTAGEUNION
//          gatewayRequest.setParameter("bank_code","ALIPAY");
//			gatewayRequest.setParameter("bank_code","PUBLICALIPAY");
		gatewayRequest.setParameter("bank_code","PUBLICWECHAT");
//        gatewayRequest.setParameter("bank_code","WECHAT");
			gatewayRequest.setParameter("amount","0.1");
//		gatewayRequest.setParameter("auth_code","280448163006692512");
			gatewayRequest.setParameter("currency_type","CNY");
			gatewayRequest.setParameter("sett_currency_type","CNY");
			gatewayRequest.setParameter("product_name","iPhone18测#试");
			gatewayRequest.setParameter("product_desc","未来产品");
	         gatewayRequest.setParameter("auth_code","134609009782936275");

//            gatewayRequest.setParameter("app_id","wx29942bf7fd404aa6");
//            app_id=wxd811f5114e3e56f3
			

//            gatewayRequest.setParameter("chn_serial_no","407000000001791");
            gatewayRequest.setParameter("user_bank_card_no","oUpF8uOYunOKJ-g7EjhjXayypnIs");
			
			gatewayRequest.setParameter("notify_url",TestUtil.wg_url+"staging/notifyUrl.jsp");
			gatewayRequest.setParameter("sign_type",Constant.GATEWAY_SIGN_TYPE_SHA256);
//			gatewayRequest.setParameter("client_ip", "112.94.189.94");
//            gatewayRequest.setParameter("scene_info", "{\"h5_info\":{\"type\":\"Wap\",\"wap_url\":\"http://www.pengjv.com\",\"wap_name\":\"鹏聚电商\"}}");
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
			
//			System.out.println(RequestUtil.doGet(requestUrl, null));
			
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
		     System.out.println(new Date());

			
		}
		
		
	}
}
