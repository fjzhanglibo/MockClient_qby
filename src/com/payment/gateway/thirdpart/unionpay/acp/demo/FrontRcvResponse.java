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
 * Name: Merchant's Front-end Notices
 * Function: 
 * Class attribute:
 * Method call version: 5.0 
 * Updated: July 2014 
 * Author: China UnionPay ACP Team 
 * Copyright: China UnionPay
 * Notes: The following sample code is for testing only. Merchants can write their own code based on their needs following technical documentation. The following code is for reference only.
 * </pre>
 * */
public class FrontRcvResponse extends HttpServlet {

	private static final long serialVersionUID = -4826029673018921502L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		LogUtil.writeLog("FrontRcvResponse Front end begins to receive message returns.");

		req.setCharacterEncoding("ISO-8859-1");
		String encoding = req.getParameter(SDKConstants.param_encoding);
		LogUtil.writeLog("Returned message encoding=[" + encoding + "]");
		String pageResult = "";
		if ("UTF-8".equalsIgnoreCase(encoding)) {
			pageResult = "/utf8_result.jsp";
		} else {
			pageResult = "/gbk_result.jsp";
		}
		Map<String, String> respParam = getAllRequestParam(req);

		// Print request message
		LogUtil.printRequestLog(respParam);

		Map<String, String> valideData = null;
		StringBuffer page = new StringBuffer();
		if (null != respParam && !respParam.isEmpty()) {
			Iterator<Entry<String, String>> it = respParam.entrySet()
					.iterator();
			valideData = new HashMap<String, String>(respParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = new String(value.getBytes("ISO-8859-1"), encoding);
				page.append("<tr><td width=\"30%\" align=\"right\">" + key
						+ "(" + key + ")</td><td>" + value + "</td></tr>");
				valideData.put(key, value);
			}
		}
		if (!SDKUtil.validate(valideData, encoding)) {
			page.append("<tr><td width=\"30%\" align=\"right\">Result of signature verification</td><td>fails.</td></tr>");
			LogUtil.writeLog("Result of signature verification [fails].");
		} else {
			page.append("<tr><td width=\"30%\" align=\"right\">Result of signature validation</td><td>succeeds.</td></tr>");
			LogUtil.writeLog("Result of signature verification [succeeds].");
		}
		req.setAttribute("result", page.toString());
		req.getRequestDispatcher(pageResult).forward(req, resp);

		LogUtil.writeLog("FrontRcvResponse Front end's returned message receiving ends.");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	/**
	 * Get all requested parameter values
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(
			final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				// No message will be submitted if field value is null. <In the following treatment, when getting all parameter data, any field with its value judged as null will be deleted.>
				if (res.get(en) == null || "".equals(res.get(en))) {
					// System.out.println("======Null field===="+en);
					res.remove(en);
				}
			}
		}
		return res;
	}

}
