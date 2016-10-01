package com.payment.gateway.thirdpart.unionpay.acp.demo;

import java.util.HashMap;
import java.util.Map;

import com.unionpay.acp.sdk.SDKConfig;

/**
 * 
 * Name:  Payment over Internet - Cross-border<br>
 * Function: 5.2 Transaction Status Inquiry<br>
 * Back-end Transaction<br>
 * Version:  5.0<br>
 * Updated:  July 2014<br>
 * Author:  China UnionPay ACP Team<br>
 * Copyright:  China UnionPay<br>
 * Notes: The following sample code is for testing only. Merchants can write their own code based on their needs following technical documentation. The following code is for reference only.<br>
 */
public class Form00_5_2 extends DemoBase {

	public static Map<String, String> setFormDate() {

		Map<String, String> contentData = new HashMap<String, String>();

		// Constant value
		contentData.put("version", version);// M
		// Default value: UTF-8
		contentData.put("encoding", encoding);// M
		// Value: 01 (RSA)
		contentData.put("signMethod", "01");// M
		// Transaction type 00
		contentData.put("txnType", "00");// M
		// Default 00
		contentData.put("txnSubType", "00");// M
		// Default: 000000
		contentData.put("bizType", "000000");// M
		// 1: Acquirer Access
		contentData.put("accessType", "1");// M
		// Acquirer Code
		contentData.put("acqInsCode", "00010000");
		// 　
		contentData.put("merId", "802290049000180");// M
		// Date and time when the transaction being checked occurred
		contentData.put("txnTime", "");// M
		// Order No.
		contentData.put("orderId", getOrderId());// M
		// Transaction inquiry serial No.
		contentData.put("queryId", "");// Serial No. of transaction being checked

		return contentData;
	}

	public static void main(String[] args) {

		/**
		 * <pre>
		 * Parameter initialization 
		 * 1. When it runs as a java main method, configuration file will be loaded every time. 
		 * 2. When it runs as a java web application, code for configuration file initialization can be included in listener.
		 * </pre>
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();

		/**
		 * Transaction request url, read from configuration file
		 */
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();
		Map<String, String> resmap = submitDate(setFormDate(), requestBackUrl);

		System.out.println(resmap.toString());
	}

}
