package com.lsq.httpclient.wg;

import com.payment.gateway.common.util.SHA256Util;
import com.payment.gateway.tools.HttpPostUtil;
import com.payment.gateway.tools.ResponseHelper;

public class testWgSign {

	public testWgSign() {
		// TODO Auto-generated constructor stub
	}
    public static void main(String args[]) throws Exception{
    	String request="busi_code=FRONT_PAY&merchant_no=102100000125&order_no=20160919115711727&pay_no=703749&qr_code=https://qr.alipay.com/bax041754tdqbsd89u0g80d6&resp_code=00&resp_desc=Success&sign_type=SHA256&terminal_no=20000147&key=857e6g8y51b5k365f7v954s50u24h14w";
    	String sign = SHA256Util.SHA256Encode(request, "utf-8").toLowerCase();
    	System.out.println("请求端签名："+sign);
    	
    	String response = "amount=14444&bank_code=WECHAT&base64_memo=88290008000086&busi_code=FRONT_PAY&client_ip=117.136.43.41&currency_type=CNY&merchant_no=102100000027&notify_url=http://180.175.173.228:8080/geturl.asp&order_no=200018&product_desc=chinapay&product_name=newname&sett_currency_type=CNY&sign_type=SHA256&terminal_no=20000147&key=857e6g8y51b5k365f7v954s50u24h14w";
    	String signf = SHA256Util.SHA256Encode(response, "utf-8").toLowerCase();
    	System.out.println("服务器端签名："+signf);
    	
    	String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><sign>067b3ac5e4e78954443bd6fcb018778a8d43f35487c299170806279d8acdbdf0</sign><resp_desc>未找到交易渠道</resp_desc><merchant_no>549440153990200</merchant_no><terminal_no>20000154</terminal_no><resp_code>99</resp_code><sign_type>SHA256</sign_type><busi_code>FRONT_PAY</busi_code><order_no>201609190000001716</order_no></root>";
    	HttpPostUtil postUtil = new HttpPostUtil();
    	ResponseHelper resHelper = new ResponseHelper();
    	resHelper.setContent(xml); //setContent(xml)并解析XML内容，得到map
    	resHelper.setKey("b69e76280c6eb7c65fba45e9e3e7a7f4");
    	resHelper.verifySign();
    }
}
