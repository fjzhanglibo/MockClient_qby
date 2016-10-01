package com.lsq.httpclient.netpay;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;


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
	
	public static void main(String[] args) {
		String response="encryptData=5z+wYEWiGr/cFtU9WNP51l/aw4ldOfrcyS8YB9SlZpEpVVsQDYe1rTCfIVu5ZIEhfEK/sDmMB/oQsljI5Q3XyiHIPk3Bkz67TYP6/NGCj7fTZf9HyMXK6AyfV4tiO4sTUZwtBaRUdzvd0bUiD6K94icl+8k9V7KZyY7HepSswbfbRMBkq+HIWAD4G8mW12NiFuR1wWDqgu9jYNusxBTneEOXkCxt012kbB6AcW92UdE+vJTDeCS1nGkpl5mwGDiK0JzaZb3vZY2JwNa0Io4ms7GoOlAEIWpJckVi0bj+EC00aHqXwMwr3KOafKKvARdVD187qmehJEPFVExRSuaI/wikyLe+ZEIYOlpGHlgSHi6RaY6/hi+5NeeGJiUk+vTNE7UeMxgqSuMZw+RAaUQY1GoHQzAU/Dz+0OvRI99N9Jzn5CKN6ALuqI8InQ8DFLJbxXW3XBbIjTCHcM4EXMbz5bBSCmYYyGc0ImS87yj6DHhmZd8+Rm1LFfwdYKEXto2b8/fU0sEFFnunngJkRothgViUvde5Ayvg7hjtrcDtlthVDjToJdsJz72LgwrC8fGj0b9japR3TiKQyiNgwirJFnZWHK0QvUzz9Aeb1IK6OudGYK91O9VPdLDir5neHFkzlDJsfyciB2l2J+3bxAoqgYIzEQ4YG2NmJSxxQ6HMKW1Ws0qSd+MAo4hEoLZD4kqZBF8/7HZAA3ByM2CtpyjWvg==&encryptKey=kXvxdbs7NSCYeCzMs/RzcVCKQLLNgk1zMfL56GXGj4xBxIvrPcMwCMnI5d+50F2RuRWLp4Rb3dSFtyULzI5mhBrdQeCGB2yeyAhxjds675Jlz8QRQkAo9nKovCyUEDMJKfvfkXWyCOH9KU9jZXJtMi1516jJEaA/bM7BZlmFvQ5e1wpT0DJEM6A7hMDPP5jQM6vR9djkKPcf+9uP7A1/1zakDu9505IZ3+RydvcF4HNoadhhPC9U9JwgnHTnm9RUtbQJ8eVk4eQ4Y4+p3wtNNF1W3uwtaNQn56FspE2rgxfTnEQSLk1Mqv8bqM4e7hvZrIB5m+m2xKCLFLegr7RuiQ==&signData=CYnIfZonIhK8AM/kDRGR8hK98qCzZTU4Pf829ylHKY8c1aOXhQ+7qfHX1nfZpr1QAhZVEyx9evLoXm6sOp6nFS6ova8saGdPiizHaEo+Vsoc9HPAC4VtttJihsN89DfcbRPDMGVuqw2tCjg2StQ1nFz6xmj+L9mfJKz+twVmm4dQVGAKR+NctVOnn0+d/XXDfTf9AOmJxZZ+Ars/vuwOH4ISB8an71sSjssKRzl4u71cJ1DhShyF974cS4jMpIFqb6TycqYWbwTEPMCdkzSEfWVBODRKOWp8F//Dy9G0bZcRfjQo++HSbv8akXGmKiE63cI1bl1P4akUNo8J2RsGdA==&tranCode=IFP001";
		TestEncype tt=new TestEncype();
		System.out.println(tt.parseMap(response).toString());
		System.out.println("解密结果: "+tt.respDecryption(response));
	}
}
