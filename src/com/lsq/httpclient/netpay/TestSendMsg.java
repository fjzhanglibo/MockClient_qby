package com.lsq.httpclient.netpay;


import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


public class TestSendMsg {
	public static void main(String[] args) throws Exception {
		//生产
//			final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("F:/密钥/test/pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
//			final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("F:/密钥/生成证书/rsa_public_key_2048.pem", "pem", "RSA");
		//测试
		PublicKey yhPubKey = TestUtil.getPublicKey();
		PrivateKey hzfPriKey = TestUtil.getPrivateKey();
		try {
			HttpClient4Util httpClient4Util = new HttpClient4Util();
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>1.0.0</version>");
			sBuilder.append("<merchantId>"+TestUtil.merchantId+"</merchantId>");
//            sBuilder.append("<childMerchantId>"+TestUtil.childMerchantId+"</childMerchantId>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<tranCode>IFP009</tranCode>");
			String reqdate=DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
			sBuilder.append("<reqMsgId>"+reqdate+"</reqMsgId>");
			System.out.println("请求交易流水号： "+reqdate);
			sBuilder.append("<reqDate>" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "</reqDate>");
			sBuilder.append("</head>");
		  //短信接口
			sBuilder.append("<body>");
			//18500339003	
			//移动测试号码:18222920903
			//13660583856
			sBuilder.append("<oriReqMsgId>20171117003204</oriReqMsgId>");
//			sBuilder.append("<oriPayMsgId>000000000078</oriPayMsgId>");
			sBuilder.append("<currency>156</currency>");
			sBuilder.append("<amount>10</amount>");
			sBuilder.append("<refundReason>测试</refundReason>");
			
			
			
			sBuilder.append("<mobilePhone>18222920903</mobilePhone>");
			sBuilder.append("<userId>"+TestUtil.userId+"</userId>");
			sBuilder.append("</body>");
			sBuilder.append("</merchant>");
			String plainXML = sBuilder.toString();
			byte[] plainBytes = plainXML.getBytes("UTF-8");
			
			String keyStr = "1122334455667788";
			byte[] keyBytes = keyStr.getBytes("UTF-8");
			byte[] base64EncryptDataBytes = Base64.encodeBase64(CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES",
					"AES/ECB/PKCS5Padding", null));
			String encryptData = new String(base64EncryptDataBytes, "UTF-8");
			byte[] base64SingDataBytes = Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA"));
			String signData = new String(base64SingDataBytes, "UTF-8");
			byte[] base64EncyrptKeyBytes = Base64.encodeBase64(CryptoUtil
					.RSAEncrypt(keyBytes, yhPubKey, 2048, 11, "RSA/ECB/PKCS1Padding"));
			String encrtptKey = new String(base64EncyrptKeyBytes, "UTF-8");
			
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("encryptData", encryptData));
			nvps.add(new BasicNameValuePair("encryptKey", encrtptKey));
			nvps.add(new BasicNameValuePair("merchantId", TestUtil.merchantId));
//			nvps.add(new BasicNameValuePair("merchantId", "I00010"));
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", "IFP009"));
			nvps.add(new BasicNameValuePair("callBack", "http://localhost:808/testShopRetur.do"));
			
			//byte[] retBytes = httpClient4Util.doPost("http://localhost:8080/quickInter/channel/commonInter.do", null, nvps, null);
//			byte[] retBytes = httpClient4Util.doPost("http://192.168.80.113:8080/quickInter1/channel/commonSyncInter.do", null, nvps, null);
			//byte[] retBytes = httpClient4Util.doPost("http://192.168.80.113:8080/quickInter/channel/cmbcReturn.do", null, nvps, null);
//			byte[] retBytes = httpClient4Util.doPost("http://epay.gaohuitong.com:8082/quickInter/channel/commonSyncInter.do", null, nvps, null);
			byte[] retBytes = httpClient4Util.doPost(TestUtil.url,null, nvps, null);
			System.out.println("回调结果: "+new String(retBytes,"UTF-8"));
			String response=new String(retBytes,"UTF-8");
			TestEncype test=new TestEncype();
			System.out.println("明文结果: "+test.respDecryption(response));
		
			
		/*	//解密返回的密文、
			String encryptKey = "bHuVrAt17bAKIHtGBa4tffDyhkmiGudR6plYjZWNrR+v3ORQIFQcFBjZdrG6XSj+z4D5M0qjl4or+IlC0HNhCzvjcm6skM7onc351wHlNm8f6muax+RwaaJZ5TUGGGPSFV7h5I0LwL5mB+++n+oKiXf6FaA1rRwAxVUeh513ctkmeys+aerbyQOwt4dz5smEgj1HX8BNMjoiHofXr0Badk/OBVAT7ybNWf4nSt/ATfrKORLIbbzklHmFzbzzSqoFGGZaVAIxUWSYXOHIVt3t9LXT0YiZ/KugeKRZ2mjUKAtfvzD1VV0gbIXqdtBoXHX+KQmnQP2fTO0WB4Cv1QSbslwntBR2NwhkudinW4S9zm9wEAdN2PnzaqkoH21WXr46RA/XRxBITVaN90q4eSSCxIZGHcV9PzW8fmixFXlBPMafZFBtSzgpYSToXNvGu2cUqJbP90WcJcOS0qHsmzVr9HVp3pv7bKTuaxi24mhn/GCLyjvhsCClqeFzTU89PYCxntTKLnUOxUXqmPynNp8P6rOsOyD9v9INfe/APnCCrsWNOpm/+c6Tb4lBqAIyawPVN1/oAdwg1rs6bAuilzWAGdArKkgILH98gTPIpPtDPyG/5+oWtMzfvTkHjya1ZWWwbO+GwXibVXa8789qEQ5MBCe2Wxs8gzqdPKwnMo+/0evcqDDREN7c1J9Jsk7Lo4n7lo6BRm2gWWuiNIzCb/ZW3UpYditNVwgCvHW5KbMF21c=&encryptKey=XM64p4Fr/j0cAU1eJN0uXWaMvtVhrd1j0blJgDki6o1UfM5aIwDtD7EoOdY8ZcyY2q/mwG08iaBwWSrcayRSsjytuh7r7YlZX3JABIBAhiAu/RNhO5sQVQPCHB/ueBLr2TQ/yFh0wWzy211IhcghOyFIOKXTaAMac8eybPBlchV0P1NOGeenCDcpm2A+N/3eyxWmxolTHHYNKOZDa7FhoIJqx9+/iDhpI9DC7ZV0jxSQmbgx4l4ZP73E/GZvr8OZT4YQqm5uJdRFmKU+XRcCyDZGpdpEpZbBOwDa8SwR1K+j0LQM3+kh8LHClhUvblj8xyetBSG4+oB5ZkC59o1Lfg==&signData=KKndcNhmevUiaTmXZe3HIOzqJqV1ungRz90EdUu4pMxBylJOBGhOwxSn6t8TpzMzPF67RvtSJ1yf6TBdVd/nsZ98gZQx23eGhyKOZ8UWgmfwoWT+a+MpSsAGRXr4GffVRc9AuvRLoy1rqdwrxrLdPrLh7TFK0TvQjTdBK+gEIorwngmfEktUIP7F9+3y+26pUjBH9LTeG43qScfDnFfLNzQI1XHFXTiJfMPVloj5AlblavyVOLZ2jFOb8PKuGGzFeCh7l5Pasd3rbf//zzEG0NsnZRIjIWWFh4spuDkFsgCjcWHeshYbzRDEyicOel9/8DoOcnFT/DL9coLiBPsu1Q==";
			String charset = "utf-8";

			byte[] merchantXmlDataBytes = null;
			boolean verifySign = false;
			// 使用base64解码商户对称密钥
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(encryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(encryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			byte[] signBytes = Base64.decodeBase64(signData.getBytes(charset));
			// 使用商户公钥验证签名
			verifySign = CryptoUtil.verifyDigitalSign(merchantXmlDataBytes, signBytes, yhPubKey, "SHA1WithRSA");
			System.out.println("merchantXmlDataBytes:"+new String(merchantXmlDataBytes,charset));
			if (verifySign) {
				System.out.println("SUCCESS");
			}else{
				System.out.println("FAILED");
			}
*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
