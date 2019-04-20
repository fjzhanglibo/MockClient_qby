package com.lsq.httpclient.netpay;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class TestEncype {
	
	public Map parseMap(String response){
		Map<String,String> map=new HashMap<String,String>();
		
		String [] resp=response.split("&");
		for(int i=0; i<resp.length; i++){
			String key=resp[i].split("=")[0];
			String value=resp[i].split("=")[1];
			map.put(key, value);
		}
		return map;
	}
	public String respDecryption(String response){
		Map respMap=this.parseMap(response);
		String xmlData = "";
		try {
//			final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("F:/密钥/test/pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
//			final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("F:/密钥/生成证书/rsa_public_key_2048.pem", "pem", "RSA");
//			final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("F:/key/test/test_rsa_public_key_2048.pem", "pem", "RSA");
//			final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("F:/key/test/test_pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
			
			PublicKey yhPubKey = TestUtil.getPublicKey();
			PrivateKey hzfPriKey = TestUtil.getPrivateKey();
			
			byte[] encryptedBytes = Base64.decodeBase64(respMap.get("encryptKey").toString().getBytes("UTF-8"));
//			String encrtptKey = new String(encryptedBytes, "UTF-8");
			byte[] keyBytes = CryptoUtil.RSADecrypt(encryptedBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			byte[] plainBytes = Base64.decodeBase64(respMap.get("encryptData").toString().getBytes("UTF-8"));
			byte[] xmlBytes = CryptoUtil.AESDecrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			xmlData = new String(xmlBytes,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlData;
	}
	
	
	public String respDecryptionJson(String response){
        JSONObject jsonObject = JSON.parseObject(response);
        String xmlData = "";
        try {
//          final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("F:/密钥/test/pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
//          final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("F:/密钥/生成证书/rsa_public_key_2048.pem", "pem", "RSA");
//          final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("F:/key/test/test_rsa_public_key_2048.pem", "pem", "RSA");
//          final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("F:/key/test/test_pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
            
            PublicKey yhPubKey = TestUtil.getPublicKey();
            PrivateKey hzfPriKey = TestUtil.getPrivateKey();
            
            byte[] encryptedBytes = Base64.decodeBase64(jsonObject.getString("encryptKey").toString().getBytes("UTF-8"));
//          String encrtptKey = new String(encryptedBytes, "UTF-8");
            byte[] keyBytes = CryptoUtil.RSADecrypt(encryptedBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
            byte[] plainBytes = Base64.decodeBase64(jsonObject.getString("encryptData").toString().getBytes("UTF-8"));
            byte[] xmlBytes = CryptoUtil.AESDecrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null);
            

            xmlData = new String(xmlBytes,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlData;
    }
	
	
	public static void main(String[] args) throws Exception {
		String response="{\"signData\":\"cO8f6pqSx8qW/T+aRg3uZdZclb2VPnGOcrL/UMwGoXXyC4dBZOEfGpuGUssT9nFC4ORVjoqHkO+BpZpWxwaTTO7DpyZljlaDbns22Er4se5uCeHufx9RRnZGqnCcJ4JuQkXGxFx/oMnwwGam0QsUiC6Ky28mj04UBdp2QgkuOqba9h+hnwnyeDcsHhPGgGifmuCL6A4NNp07IWNB5neEjap8lo2o+nQG+t2R0lOKEU8Kl8N4HjOpQ3u5wFjITkoCiQuOQXwgNmTywxqJVP1POtOE8iR5H8yD5N5sEGhATBqWp1DSq9eNGy7TKD1NHqLy4MGsxKvfjR4gZOmdFfwGiQ==\",\"agencyId\":\"549034655110001\",\"encryptKey\":\"GZA66ZN1z0dF+Dyz5R9GrHzcL7JvdlZnTaxKVMpbfKP+Jg7uoUjPU0uvAxxhzUypa2NUzhqeLDMSE0J5/2/serg7ERGtfzF4zF13QJ7+ZWrID5eiV1JRe7B7wSdfuWohOOincl0T4mCoLnj3NTB7+MYb/gT6nBD/ySJ/7KCt/yMgol2ogb9LjVxtFt0TJ0IFra9aGhyKhFgpSVlvuzX6MzNg0P5PNpGUE7tz8MHw2pVAkcLBOfV2EdH46Seb5wA+nb4GQIS2Rpnjs6GmMfj1ycC+4Rdtf8vjRcv5FM51cVhHG7Un1pLj0jF2mNh/UqurELZO9mTytNKZ4mbws22/3g==\",\"encryptData\":\"wj4vEysPN7KgsbHzhtC+0m+zqj1V/z6Qd/4dvfy6wg2Biw88yg5YYnuGIdAI0n0rOaJqeAIqvurWip1VyiF7GIm0Do9hF80yB5GSbHnLhVJDZb24gFl7s9F5jUjLAx1httVLeTyZWDxg0fD02tEfXgmDknjKHwX/cdBYbgAbguBF2sIiwFjc1OSCbobaFVWDQ/oSv6QC/iSSWMzYUDccTm5Gybu59f/T8pxp05bMC655nGZDKonzMU/WqeuF1u4p0hux6rBwVDltVqAe5euYtXOhZ1cUf/wEs/g+KejFFZT9AlYXof6mKaTEYk0QLHLbH+pEUhkL3bC3jTW7cbpj/kjgYQNapoV3cjCYqdzfn6qx5K72mohXSUtKkDs437OewKlCiSK8nkFjNwZiAmmRUzrvhyvxqwAmCnRxPD5hFaitzCm/fZokaEb8aU781rdR44lfDz97Xa3kovwg60OmibH5OLeWPn5aZtVqm82uj3CLBv4npUh5rmiT0Kma9RyL028yBdpDmk7xkX19YO1QPamBLT9Oy7M7iVyuEEuYLCoqVQjyLnYoq383PXlQzQYNm1Xg6UHexe8nFwcI5ri1szvUF+xl/0Sm4XLXziThkk+rIOaf3YBCPs9uL0VolCZnSxcYflVhKXt1iMRp+ZJFKupzydig17UVvq9/ElkuoBOB3DkTAwMAsyA2oF6SfbackFYlGe7VzlhlnaxBbcQaJLf2fq1ypqXGJ3kRxLkE9nAluzmiZQn5687+nysY0iZR9Q3GZwzsASwyCIGBcCGzog==\"}";
//		
		
		TestEncype tt=new TestEncype();
		
		String ss = tt.respDecryptionJson(response);
		System.out.println(ss);
	}
}
