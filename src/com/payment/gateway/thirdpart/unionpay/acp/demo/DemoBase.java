package com.payment.gateway.thirdpart.unionpay.acp.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;
import com.unionpay.acp.sdk.SecureUtil;

/**
 * Name:  Basic parameter<br>
 * Function:  to provide basic data<br>
 * Version:  5.0<br>
 * Updated:  July 2014<br>
 * Author:  China UnionPay ACP Team<br>
 * Copyright:  China UnionPay<br>
 * Notes: The following sample code is for testing only. Merchants can write their own code based on their needs following technical documentation. The following code is for reference only.<br>
 */
public class DemoBase {

	public static String encoding = "UTF-8";

	/**
	 * 5.0.0
	 */
	public static String version = "5.0.0";

	/**
	 * For code for back-end services, please refer to FrontRcvResponse.java.
	 * http://localhost:8080/ACPTest/acp_front_url.do
	 */
	public static String frontUrl = "http://localhost:8080/ACPTest/acp_front_url.do";

	public DemoBase() {
		super();
	}

	/**
	 * http://localhost:8080/ACPTest/acp_back_url.do
	 */
	// For code of back-end services, please refer to BackRcvResponse.java.
	public static String backUrl = "http://localhost:8080/ACPTest/acp_back_url.do";// An optional field for acquiring bank and issuing bank [O]--back-end notify address

	/**
	 * An example of how to create a HTTP POST transaction form
	 * 
	 * @param action
	 *            The address where the form is sent
	 * @param hiddens
	 *            Form keys stored in MAP
	 * @return Created HTTP POST transaction form
	 */
	public static String createHtml(String action, Map<String, String> hiddens) {
		StringBuffer sf = new StringBuffer();
		sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
		sf.append("<form id = \"pay_form\" action=\"" + action
				+ "\" method=\"post\">");
		if (null != hiddens && 0 != hiddens.size()) {
			Set<Entry<String, String>> set = hiddens.entrySet();
			Iterator<Entry<String, String>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, String> ey = it.next();
				String key = ey.getKey();
				String value = ey.getValue();
				sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\""
						+ key + "\" value=\"" + value + "\"/>");
			}
		}
		sf.append("</form>");
		sf.append("</body>");
		sf.append("<script type=\"text/javascript\">");
		sf.append("document.all.pay_form.submit();");
		sf.append("</script>");
		sf.append("</html>");
		return sf.toString();
	}

	/**
	 * java main method  Submit data 　　 Sign data
	 * 
	 * @param contentData
	 * @return　Signed map object
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> signData(Map<String, String> contentData) {
		Entry<String, String> obj = null;
		Map<String, String> submitFromData = new HashMap<String, String>();
		for (Iterator<?> it = contentData.entrySet().iterator(); it.hasNext();) {
			obj = (Entry<String, String>) it.next();
			String value = obj.getValue();
			if (!SDKUtil.isEmpty(value)) {
				// Get rid of spaces before and following value
				submitFromData.put(obj.getKey(), value.trim());
			}
		}
		/**
		 * Signature
		 */
		SDKUtil.sign(submitFromData, encoding);
		return submitFromData;
	}

	/**
	 * java main method  Submit data  Submit to back end
	 * 
	 * @param contentData
	 * @return  Return message map
	 */
	public static Map<String, String> submitUrl(
			Map<String, String> submitFromData, String requestUrl) {
		String resultString = "";
		/**
		 * Send
		 */
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		try {
			int status = hc.send(submitFromData, encoding);
			//if (200 == status) {
				resultString = hc.getResult();
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> resData = new HashMap<String, String>();
		/**
		 * Verify signature
		 */
		if (null != resultString && !"".equals(resultString)) {
			// Converse returned result into map
			resData = SDKUtil.convertResultStringToMap(resultString);
			if (SDKUtil.validate(resData, encoding)) {
				System.out.println("Signature verification successes.");
			} else {
				System.out.println("Signature verification fails.");
			}
			// Print returned message.
			System.out.println("Print returned message:" + resultString);
		}
		return resData;
	}

	/**
	 * Parse returned message
	 */
	public static void deCodeFileContent(Map<String, String> resData) {
		// Parse returned message
		String fileContent = resData.get(SDKConstants.param_fileContent);
		if (null != fileContent && !"".equals(fileContent)) {
			try {
				byte[] fileArray = SecureUtil.inflater(SecureUtil
						.base64Decode(fileContent.getBytes(encoding)));
				String root = "D:\\";
				String filePath = null;
				if (SDKUtil.isEmpty(resData.get("fileName"))) {
					filePath = root + File.separator + resData.get("merId")
							+ "_" + resData.get("batchNo") + "_"
							+ resData.get("txnTime") + ".zip";
				} else {
					filePath = root + File.separator + resData.get("fileName");
				}
				File file = new File(filePath);
				if (file.exists()) {
					file.delete();
				}
				file.createNewFile();
				FileOutputStream out = new FileOutputStream(file);
				out.write(fileArray, 0, fileArray.length);
				out.flush();
				out.close();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * java main method  Submit data　 Assembly and submit data, including signature
	 * 
	 * @param contentData
	 * @return  Return message map
	 */
	public static Map<String, String> submitDate(Map<String, String> contentData,
			String requestUrl) {
		Map<String, String> submitFromData = (Map<String, String>) signData(contentData);
		return submitUrl(submitFromData, requestUrl);
	}

	/**
	 * Operate on card holder information fields.
	 * 
	 * @param encoding
	 *            Coding method
	 * @return base64 encoded card holder information fields
	 */
	public static String getCustomer(String encoding) {
		StringBuffer sf = new StringBuffer("{");
		// ID type
		String certifTp = "01";
		// ID No.
		String certifId = "1301212386859081945";
		// Name
		String customerNm = "Test";
		// Mobile phone No.
		String phoneNo = "18222920903";
		// SMS authentication code
		String smsCode = "123311";
		// Card holder's password
		String pin = "123213";
		// cvn2
		String cvn2 = "400";
		// Expiration date
		String expired = "1212";
		sf.append("certifTp=" + certifTp + SDKConstants.AMPERSAND);
		sf.append("certifId=" + certifId + SDKConstants.AMPERSAND);
		sf.append("customerNm=" + customerNm + SDKConstants.AMPERSAND);
		sf.append("phoneNo=" + phoneNo + SDKConstants.AMPERSAND);
		sf.append("smsCode=" + smsCode + SDKConstants.AMPERSAND);
		// Password encrypted
		// sf.append("pin=" + SDKUtil.encryptPin("622188123456789", pin, encoding) + SDKConstants.AMPERSAND);
		// Password not encrypted
		sf.append("pin=" + pin + SDKConstants.AMPERSAND);
		// cvn2 encrypted
		// sf.append(SDKUtil.encryptCvn2(cvn2, encoding) + SDKConstants.AMPERSAND);
		// cvn2 not encrypted
		sf.append("cvn2=" + cvn2 + SDKConstants.AMPERSAND);
		// Expiration date encrypted
		// sf.append(SDKUtil.encryptAvailable(expired, encoding));
		// Expiration date not encrypted
		sf.append("expired=" + expired);
		sf.append("}");
		String customerInfo = sf.toString();
		try {
			return new String(SecureUtil.base64Encode(sf.toString().getBytes(
					encoding)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return customerInfo;
	}

	public static String getCurrentTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	public static String getOrderId() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	/**
	 * An example of card transaction data field (cardTransData) structure<br>
	 * All subfield should be included in {} and connected with &. The format should be as follows: {subfield_name1=value&subfield_name2=value&subfield_name3=value}<br>
	 * Notes: This example is for reference only. When writing code, please use the message assembly components in interface documentation.
	 * 
	 * @param contentData
	 * @param encoding
	 * @return
	 */
	public static String getCardTransData(Map<String, String> contentData,
			String encoding) {

		StringBuffer cardTransDataBuffer = new StringBuffer();
		
		// The following testing data is only used to illustrate the basic steps to assembly cardTransData fields. Actual data should be based on actual business.
		String ICCardData = "uduiadniodaiooxnnxnnada";// IC card data
		String ICCardSeqNumber = "123";// IC card's serial number
		String track2Data = "testtrack2Datauidanidnaidiadiada231";// Track 2 data
		String track3Data = "testtrack3Datadadaiiuiduiauiduia312117831";// Track 3 data
		String transSendMode = "b";// Transaction initiation method

		// The encrypted format of Track 2 data is as follows: merId|orderId|txnTime|txnAmt|track2Data
		StringBuffer track2Buffer = new StringBuffer();
		track2Buffer.append(contentData.get("merId"))
				.append(SDKConstants.COLON).append(contentData.get("orderId"))
				.append(SDKConstants.COLON).append(contentData.get("txnTime"))
				.append(SDKConstants.COLON).append(contentData.get("txnAmt"))
				.append(SDKConstants.COLON).append(track2Data);

		String encryptedTrack2 = SDKUtil.encryptTrack(track2Buffer.toString(),
				encoding);

		// The encrypted format of Track 3 data is as follows: merId|orderId|txnTime|txnAmt|track3Data
		StringBuffer track3Buffer = new StringBuffer();
		track3Buffer.append(contentData.get("merId"))
				.append(SDKConstants.COLON).append(contentData.get("orderId"))
				.append(SDKConstants.COLON).append(contentData.get("txnTime"))
				.append(SDKConstants.COLON).append(contentData.get("txnAmt"))
				.append(SDKConstants.COLON).append(track3Data);

		String encryptedTrack3 = SDKUtil.encryptTrack(track3Buffer.toString(),
				encoding);

		// The data to be assembled are packed into MAP and then formatted.
		Map<String, String> cardTransDataMap = new HashMap<String, String>();
		cardTransDataMap.put("ICCardData", ICCardData);
		cardTransDataMap.put("ICCardSeqNumber", ICCardSeqNumber);
		cardTransDataMap.put("track2Data", encryptedTrack2);
		cardTransDataMap.put("track3Data", encryptedTrack3);
		cardTransDataMap.put("transSendMode", transSendMode);

		return cardTransDataBuffer.append(SDKConstants.LEFT_BRACE)
				.append(SDKUtil.coverMap2String(cardTransDataMap))
				.append(SDKConstants.RIGHT_BRACE).toString();
	}

}
