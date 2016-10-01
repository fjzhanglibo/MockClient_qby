package com.payment.gateway.thirdpart.unionpay.acp.demo;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;


/**
 * <pre>
 * Name: Merchant's Back-end Notices
 * Function: 
 * Class attribute:
 * Method call version: 5.0 
 * Updated: July 2014 
 * Author: China UnionPay ACP Team 
 * Copyright: China UnionPay
 * Notes: The following sample code is for testing only. Merchants can write their own code based on their needs following technical documentation. The following code is for reference only.
 * </pre>
 * */
public class BackRcvResponse extends HttpServlet{

	private static final long serialVersionUID = 3414800502432002480L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		LogUtil.writeLog("BackRcvResponse begins to receive back-end notice.");

		req.setCharacterEncoding("ISO-8859-1");
		String encoding = req.getParameter(SDKConstants.param_encoding);
		// Get all requested parameter values
		Map<String, String> reqParam = getAllRequestParam(req);
		// Print request message
		LogUtil.printRequestLog(reqParam);

		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = new String(value.getBytes("ISO-8859-1"), encoding);
				valideData.put(key, value);
			}
		}

		// Verify signature
		if (!SDKUtil.validate(valideData, encoding)) {
			LogUtil.writeLog("Result of signature verification [fails].");
		} else {
			LogUtil.writeLog("Result of signature verification [succeeds].");
		}

		LogUtil.writeLog("BackRcvResponse back-end notice receiving ends.");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		this.doPost(req, resp);
	}

	/**
	 * Get all requested parameter values
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				//No message will be submitted if field value is null. <In the following treatment, when getting all parameter data, any field with its value judged as null will be deleted.>
				//System.out.println("Class ServletUtil line 247 temp key=="+en+"     value==="+value);
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}

}
