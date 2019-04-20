package com.lsq.httpclient.wg;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.lsq.httpclient.netpay.TestUtil;
import com.payment.gateway.common.constant.Constant;
import com.payment.gateway.tools.GatewayRequest;
import com.payment.gateway.tools.HttpPostUtil;
import com.payment.gateway.tools.ResponseHelper;

public class Scan {
	public static void main(String[] args) throws Exception {
	    
	    for (int i = 0; i < 1; i++) {
            
	        HttpPostUtil postUtil = new HttpPostUtil();
	        String params = "";
	        String respStr = null;
//		String key = "e859eb838c95e1042bae9e44796e4d63";
//	        String key = "857e6g8y51b5k365f7v954s50u24h14w";
	        
//      String key = "33c17a7d5c91a908025d9c14d6894104";//境外冠付
	        
//		String key = "ca862078adcc6da769bd5bd8f57cbde2";//境外Technology Merchant
	        
	        
	        String key = TestUtil.key;
	        
	        GatewayRequest gatewayRequest = new GatewayRequest(null, null,0);
	        gatewayRequest.setParameter("version", "2.0.0");
	        
	        gatewayRequest.setKey(key);
	        gatewayRequest.setGatewayUrl(TestUtil.wg_url+"backStageEntry.do");
//		gatewayRequest.setGatewayUrl("http://localhost:8080/payment-gateway/backStageEntry.do");
	        gatewayRequest.setParameter("busi_code","BACKSTAGE_PAY");
	        gatewayRequest.setParameter("merchant_no",TestUtil.merchantId);
	        gatewayRequest.setParameter("terminal_no",TestUtil.terminalId);
//        gatewayRequest.setParameter("child_merchant_no",TestUtil.childMerchantId);
	        
//		gatewayRequest.setParameter("merchant_no","000158120120");
//        gatewayRequest.setParameter("terminal_no","20000067");
//        gatewayRequest.setKey("e859eb838c95e1042bae9e44796e4d63");
	        
	        
//        gatewayRequest.setParameter("merchant_no","549034459460001");
//        gatewayRequest.setParameter("terminal_no","20000262");
	        
//		gatewayRequest.setParameter("merchant_no","549034455310001");
//        gatewayRequest.setParameter("terminal_no","20000598");
	        
	        
	        
	        
	        
//		gatewayRequest.setParameter("bank_code","BACKSTAGEALIPAY");
//	        gatewayRequest.setParameter("bank_code","BACKSTAGEWECHAT");
//	        gatewayRequest.setParameter("bank_code","EXPRESS_PAY");
	        gatewayRequest.setParameter("bank_code","BACKSTAGEUNION");
	        gatewayRequest.setParameter("amount","0.01");
	        gatewayRequest.setParameter("auth_code","hQVDUFYwMWFRTwigAAADMwEBAlcMYlgJFlZ0JWHVASIBYzOCAgAAnxARBwEBA6AAAAEIMDAyNTAwMDGfJghW5e3f7vb8WJ8nAYCfNgIUe583BAqH4VVfNAEB");
	        gatewayRequest.setParameter("currency_type","HKD");
	        gatewayRequest.setParameter("sett_currency_type","HKD");
	        gatewayRequest.setParameter("product_name","iPhone18");
	        gatewayRequest.setParameter("product_desc","未来产品");
	        
	        gatewayRequest.setParameter("notify_url",TestUtil.wg_url+"staging/notifyUrl.jsp");
	        gatewayRequest.setParameter("sign_type",Constant.GATEWAY_SIGN_TYPE_SHA256);
	        
	        
	        gatewayRequest.setParameter("order_no",DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+i);
	        
//	        gatewayRequest.setParameter("user_bank_card_no","6258091656742561");
//            gatewayRequest.setParameter("cvn2","741");
//            gatewayRequest.setParameter("valid","0421");
	        
//            gatewayRequest.setParameter("split","<split><split_type>2</split_type><merchant_list><merchant_no>549034555420003</merchant_no><value>15</value></merchant_list><merchant_list><merchant_no>00000298</merchant_no><value>15</value></merchant_list><merchant_list><merchant_no>00000296</merchant_no><value>70</value></merchant_list></split>");
	        
	        String requestUrl = gatewayRequest.getRequestURL();
	        params = postUtil.getQueryString(requestUrl);
	        String callUrl = TestUtil1.reqUrl;
	        
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
