package com.lsq.httpclient.netpay;

import org.apache.commons.codec.binary.Base64;

public class Test {
	public static void main(String[] args){
		String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><merchant><head></version>1.0.0<ersion><merchantId>102100000125</merchantId><msgType>01</msgType><tranCode>IFP001</tranCode><reqMsgId>1061619431100000</reqMsgId><reqDate>20160825161624</reqDate></head><body><bankCardNo>6230200730111111</bankCardNo><accountName>测试</accountName><bankCardType>01</bankCardType><certificateType>ZR01</certificateType><certificateNo>350681199102046539</certificateNo><mobilePhone>13063032756</mobilePhone></valid><alid><cvn2></cvn2><pin></pin><userId>test</userId></body></merchant>";
		
				//"b/9Sczp9I+9cVoUXimTve2+OSEmia3tBw3dqBI9u/NsK5cXycZOFwcxlvHSehHni0Tq6JUHyoLbLaWqlaf5U1tKRJgoQb/zXMFXP2VqD1lPmYDm4u9uSQ4IOKn7iz14gMV9o3MN7ugF0Y5AEGqQm0i+dfhnp80IAL71jzj14wY1kPIBMqp4w0c85JhjjSZxQCO95yWwANMyrMTyGLNZZZYKmis6M1f5HHEh2Q7+x01Yq6h8oOKV82u0yKxp2gEKhY2zBeiO7ez7rzLpeb+PYl2OMqFPfY/SLDNTSdBiFc+UXvSe3CHMy9ZeGdU4+NU9tMwa01/UCWM7YhUoKGjxsxRi0himlBe6JWOfc7mbWUM5jirYmnb2F3m2CHJl1vrlEvtj/hb2VGgwBBU38mUln9IVOvhoxkgS36O8upTGmnQGpcrIYK0sjj57XgPLS2a83wVY1yaVInPZy/F410ZDTm501B94vwtGrdUGEXns8DSxJgbx+EoLKfqkzt84ObzwrZ/ucAM0j4EgYjC9ojckmbaKm4p2Lvyzg0BZaQEXjDdenujhxRs6QOCZWtrJNi+t0J6HBuSmre67GEbUBl2SR3UABsob0QMYPjWkDxYNJwV6lUHjCE+oahsMOxwaqooaBTW5dPl96F54ndYOte8mf2q/3LJwMyILIe0XrIsKQOeUPwaMS4WKtGV5wG14HJr0kCIntfpiPG/xioJMStZa3AjKE6lBbXp9oC2uOgeXByh+n7O2LKmAYW2i7BiFav0wdhNuZ1bhM5HfxOTCx3p9K1gnVIJGOg1YReG0VFmIDA4X1IyZYOTmd28RuJfyMosfVE74k9qNjM6dDDomDSb06xD1G4FBu5UmisqJnFf5afW3ZtAyG6HRDjOzlMrtLAPCtaZtX0gpKzovvMk5BdsrkGJhHTi7jSpxKGCNZxcCZoNcj99wKHPDAjRFwyXkDTzlXWTzkxvVWH8Wj1z62MAc0fSwvFiqz7fwVEFdNDZduDBkJEyyExzL1dCmNR1tpTxr/mzinsUwRLCm5CHVBcsZkbdcv3jNntZ57T4fuAsf2UKg=";
				    //"bq/68H5x00PIqOcprz0yEHLy+MzXh4mhgm0fqo6kASx+pFjyBgrwg1i+Z2lCpqZGiQ3A4SPJ4sn5sY+3t+RMbL3di8cfMCDDCgTUy+M4jjMIKdGyizZ03YBj/RrGr1hCmn3Eg+VoFjf/oP7E5jwkhqkHFVk5xPIxI5wjDx/iPnVVvOvz9c6at0kUKfpEw3MmmvHIzlLMspArL9xttwy0q+IzLetjOmMadnQOwkKwujUVbNph6JmBHPOJOnLV5e2ek96lgDykI0GXCEKfBc+K+l0FX1vKmVgLGl1P573CBcXGhlzZA6+ZlTLJwcd7KpXLBwMPlMg0k2xEy5yxLBHMo0/4fEpza01J+2nhJkcv6nbv7uEC5YNgqctFpRg7yoBfjA1/R7JdR4A8xLst6tB3zEQAO3XRhXZ02gJMUh1Ga9o9MSxO+K5Mxxhrhp/IvkcdQIeNRJNhXThLU7ggC73qlMbWx0s/ZmlHks+h90yoUpdOUFKieT0idbPGdRxNxF4HCNkQXHE1AVdaOEH9s38WyJcQypZvG1oJ/cY/J7Yrl61R6La26JNYCjIlU72201bLSzVzOPrTkfOWnWMx93pi87kS2k6YfOeppypJ0SCJadOIbCWGfXK3qoRahaaVRt1qbhtAPmiz48oOb6IH8nasc19lRWZwIg2SjIAzB6Q9bwYVxFzF5cMaSKdcG9EOg8pBwrhRriuP3R1TZ7XleLtM0TK7ince1MaJ1unyqZkBtGo=";
		String key = "3kfztf1p12vdmyrs";
		try{
//			byte[] plainBytes = Base64.decodeBase64(data.toString().getBytes("UTF-8"));
			byte[] plainBytes = data.toString().getBytes("UTF-8");
			byte[] keyBytes = key.getBytes("UTF-8");
			
			byte[] base64EncryptDataBytes = Base64.encodeBase64(CryptoUtil
					.AESEncrypt(plainBytes, keyBytes, "AES",
							"AES/ECB/PKCS5Padding", null));
			String encryptData = new String(base64EncryptDataBytes, "UTF-8");
			
//			byte[] xmlBytes = CryptoUtil.AESDecrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			System.out.println(new String(encryptData));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
