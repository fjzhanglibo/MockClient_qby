package com.payment.gateway.tools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import org.apache.log4j.Logger;

public class HttpPostUtil {

	Logger logger = Logger.getLogger(HttpPostUtil.class.getName());


	/**
	 * 发送post请求
	 * 
	 * @param strUrl
	 * @return
	 */
	public boolean postRequest(String strUrl) {
		// 请求url
		String url = this.getURL(strUrl);
		// 请求参数
		String queryString = this.getQueryString(strUrl);
		try {
			if (url.indexOf("http://") != -1) {
				this.resContent = this.postHttp(url, queryString);
				return true;
			} else if (url.indexOf("https://") != -1) {
				this.resContent = this.postHttps(url, queryString);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String postHttp(String urlStr, String params) {
		System.out.println("http方式提交的URL: " + urlStr + "?" + params);
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();
		try {
			URL realUrl = new URL(urlStr);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			System.out.println("Con Accept-Charset:" + conn.getRequestProperties().get("Accept-Charset"));

			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			while ((line = in.readLine()) != null) {
				result.append(new String(line.getBytes("UTF-8")));
			}
			// System.out.println("remote host respone : "+result);
			System.out.println("远程主机返回文本结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
					out = null;
				}
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return result.toString();
	}

	public String postHttp(String urlStr, String params, String requestMethod) {
		// logger.info("http方式提交的URL: "+urlStr + "?" + params);
		PrintWriter out = null;
		BufferedInputStream in = null;
		String result = null;
		try {
			URL realUrl = new URL(urlStr);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod(requestMethod);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(params);
			// flush输出流的缓冲
			out.flush();
			// 定义Bufferedinputstream输入流来读取URL的响应
			in = new BufferedInputStream(conn.getInputStream());
			byte abyte0[] = new byte[4096];
			StringBuffer stringbuffer = new StringBuffer();
			for (int i = 0; i != -1;) {
				i = in.read(abyte0, 0, 4096);
				if (i != -1) {
					String s2 = new String(abyte0, 0, i, "UTF-8");
					stringbuffer.append(s2);
				}
			}
			result = stringbuffer.toString();
			// System.out.println("remote host respone : "+result);
			// logger.info("远程主机返回文本结果："+result);
		} catch (Exception e) {
			// logger.error(e.getMessage(),e);
		} finally {
			try {
				if (out != null) {
					out.close();
					out = null;
				}
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (Exception ex) {
				// logger.error(this.getClass().getName(), ex);
			}
		}

		return result;
	}

	/**
	 * 以SSL方式提交请求
	 * 
	 * @param urlString
	 *            提交的url地址
	 * @param data
	 *            提交的请求参数
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 */
	@SuppressWarnings({ "deprecation", "static-access" })
	public String postHttps(String urlString, String data)
			throws NoSuchAlgorithmException, KeyManagementException, IOException {
		System.out.println("https方式提交的URL:" + urlString);
		System.out.println("提交的参数：" + data);
		OutputStreamWriter os = null;

		URL url = new URL(urlString);
		URLConnection con = url.openConnection();
		System.out.println("连接类型：" + con.getClass());
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();

		if (con instanceof javax.net.ssl.HttpsURLConnection) {
			System.out.println("*** openConnection returns an instanceof javax.net.ssl.HttpsURLConnection");

			// 信任所有证书 开始
			javax.net.ssl.SSLContext sc = null;
			javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[] {
					new javax.net.ssl.X509TrustManager() {
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return null;
						}

						public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
						}

						public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
						}
					} };

			// Install the all-trusting trust manager
			sc = javax.net.ssl.SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			// 信任所有证书 结束

			javax.net.ssl.HostnameVerifier hv = new javax.net.ssl.HostnameVerifier() {
				public boolean verify(String urlHostName, javax.net.ssl.SSLSession session) {
					return urlHostName.equals(session.getPeerHost());
				}
			};
			javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(hv);
			javax.net.ssl.HttpsURLConnection conn = null;
			conn = (javax.net.ssl.HttpsURLConnection) con;
			// conn.setRequestProperty("Content-Type", "text/xml");
			conn.setDoOutput(true);
			conn.setFollowRedirects(true);
			// conn.setReadTimeout(30000);
			os = new OutputStreamWriter(conn.getOutputStream());
			os.write(data);
			os.flush();
			if (conn.getResponseCode() == 302 || conn.getResponseCode() == 200) {
				System.out.println("https请求发送成功。");
				System.out.println("返回码：" + conn.getResponseCode());
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} else if (con instanceof com.sun.net.ssl.HttpsURLConnection) {
			System.out.println("***openConnection returns an instanceof com.sun.net.ssl.HttpsURLConnection");
			// 信任所有证书 开始
			com.sun.net.ssl.SSLContext sc = null;
			com.sun.net.ssl.TrustManager[] trustAllCerts = new com.sun.net.ssl.TrustManager[] {
					new com.sun.net.ssl.X509TrustManager() {
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return null;
						}

						@Override
						public boolean isClientTrusted(X509Certificate[] arg0) {
							// TODO Auto-generated method stub
							return true;
						}

						@Override
						public boolean isServerTrusted(X509Certificate[] arg0) {
							// TODO Auto-generated method stub
							return true;
						}
					} };
			// Install the all-trusting trust manager
			sc = com.sun.net.ssl.SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			com.sun.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			// 信任所有证书 结束

			com.sun.net.ssl.HttpsURLConnection conn = null;
			conn = (com.sun.net.ssl.HttpsURLConnection) con;
			com.sun.net.ssl.HostnameVerifier hv = new com.sun.net.ssl.HostnameVerifier() {
				@Override
				public boolean verify(String arg0, String arg1) {
					return true;
				}
			};
			com.sun.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(hv);
			conn.setAllowUserInteraction(true);
			conn.setDoOutput(true);

			os = new OutputStreamWriter(conn.getOutputStream());
			os.write(data);
			os.flush();

			if (conn.getResponseCode() == 302 || conn.getResponseCode() == 200) {
				System.out.println("https请求发送成功。");
				System.out.println("返回码：" + conn.getResponseCode());
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		}
		System.out.println("远程主机返回的文本结果：" + result);

		return result.toString();
	}

	/**
	 * 获取不带查询串的url
	 * 
	 * @param strUrl
	 * @return String
	 */
	private String getURL(String strUrl) {

		if (null != strUrl) {
			int indexOf = strUrl.indexOf("?");
			if (-1 != indexOf) {
				return strUrl.substring(0, indexOf);
			}

			return strUrl;
		}

		return strUrl;

	}

	/**
	 * 获取查询串
	 * 
	 * @param strUrl
	 * @return String
	 */
	public String getQueryString(String strUrl) {

		if (null != strUrl) {
			int indexOf = strUrl.indexOf("?");
			if (-1 != indexOf) {
				return strUrl.substring(indexOf + 1, strUrl.length());
			}

			return "";
		}

		return strUrl;
	}

	public String getResContent() {
		return resContent;
	}

	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}

	// 以SSL方式提交请求
	/**
	 * @param urlString
	 *            提交的url地址
	 * @param data
	 *            提交的请求参数
	 * @param requestMethod
	 *            提交的requestMethod
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 */
	public String postHttps(String urlString, String data, String requestMethod)
			throws NoSuchAlgorithmException, KeyManagementException, IOException {
		return postHttpsMain(urlString, data, requestMethod, null);
	}

	// 以SSL方式提交请求
	/**
	 * @param urlString
	 *            提交的url地址
	 * @param data
	 *            提交的请求参数
	 * @param requestMethod
	 *            提交的requestMethod
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public String postHttpsMain(String urlString, String data, String requestMethod, Object obj)
			throws NoSuchAlgorithmException, KeyManagementException, IOException {
		logger.info("https方式提交的URL:" + urlString);
		logger.info("提交的参数：" + data);
		if (data == null)
			data = "";
		if (requestMethod == null)
			requestMethod = "";
		OutputStreamWriter os = null;

		URL url = new URL(urlString);
		URLConnection con = url.openConnection();
		logger.info("连接类型：" + con.getClass());
		BufferedInputStream in = null;
		String result = null;

		if (con instanceof javax.net.ssl.HttpsURLConnection) {
			logger.info("*** openConnection returns an instanceof javax.net.ssl.HttpsURLConnection");

			// 信任所有证书 开始
			javax.net.ssl.SSLContext sc = null;
			javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[] {
					new javax.net.ssl.X509TrustManager() {
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return null;
						}

						public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
						}

						public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
						}
					} };

			// Install the all-trusting trust manager
			sc = javax.net.ssl.SSLContext.getInstance("SSL");
			if (obj == null)
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
			else
				sc.init(((javax.net.ssl.KeyManagerFactory) obj).getKeyManagers(), trustAllCerts,
						new java.security.SecureRandom());
			javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			// 信任所有证书 结束

			javax.net.ssl.HostnameVerifier hv = new javax.net.ssl.HostnameVerifier() {
				public boolean verify(String urlHostName, javax.net.ssl.SSLSession session) {
					return urlHostName.equals(session.getPeerHost());
				}
			};
			javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(hv);
			javax.net.ssl.HttpsURLConnection conn = null;
			conn = (javax.net.ssl.HttpsURLConnection) url.openConnection();
			// conn.setRequestProperty("Content-Length",String.valueOf(data.getBytes().length));
			// conn.setDoInput(true);
			conn.setDoOutput(true);
			if (requestMethod != null)
				requestMethod = requestMethod.toUpperCase();
			conn.setRequestMethod(requestMethod);
			// conn.setFollowRedirects(true);

			os = new OutputStreamWriter(conn.getOutputStream());
			os.write(data);
			os.flush();
			if (conn.getResponseCode() == 302 || conn.getResponseCode() == 200) {
				logger.info("https请求发送成功。");
				logger.info("返回码：" + conn.getResponseCode());
			}
			// 定义Bufferedinputstream输入流来读取URL的响应
			in = new BufferedInputStream(conn.getInputStream());
			byte abyte0[] = new byte[4096];
			StringBuffer stringbuffer = new StringBuffer();
			for (int i = 0; i != -1;) {
				i = in.read(abyte0, 0, 4096);
				if (i != -1) {
					String s2 = new String(abyte0, 0, i, "UTF-8");
					stringbuffer.append(s2);
				}
			}
			result = stringbuffer.toString();
		} else if (con instanceof com.sun.net.ssl.HttpsURLConnection) {
			logger.info("***openConnection returns an instanceof com.sun.net.ssl.HttpsURLConnection");
			// 信任所有证书 开始
			com.sun.net.ssl.SSLContext sc = null;
			com.sun.net.ssl.TrustManager[] trustAllCerts = new com.sun.net.ssl.TrustManager[] {
					new com.sun.net.ssl.X509TrustManager() {
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return null;
						}

						@Override
						public boolean isClientTrusted(X509Certificate[] arg0) {
							// TODO Auto-generated method stub
							return true;
						}

						@Override
						public boolean isServerTrusted(X509Certificate[] arg0) {
							// TODO Auto-generated method stub
							return true;
						}
					} };
			// Install the all-trusting trust manager
			sc = com.sun.net.ssl.SSLContext.getInstance("SSL");

			if (obj == null)
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
			else
				sc.init(((com.sun.net.ssl.KeyManagerFactory) obj).getKeyManagers(), trustAllCerts,
						new java.security.SecureRandom());

			com.sun.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			// 信任所有证书 结束

			com.sun.net.ssl.HttpsURLConnection conn = (com.sun.net.ssl.HttpsURLConnection) url.openConnection();
			com.sun.net.ssl.HostnameVerifier hv = new com.sun.net.ssl.HostnameVerifier() {
				@Override
				public boolean verify(String arg0, String arg1) {
					return true;
				}
			};
			com.sun.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(hv);
			conn.setAllowUserInteraction(true);
			// conn.setDoInput(true);
			conn.setDoOutput(true);
			if (requestMethod != null)
				requestMethod = requestMethod.toUpperCase();
			conn.setRequestMethod(requestMethod);

			os = new OutputStreamWriter(conn.getOutputStream());
			os.write(data);
			os.flush();

			if (conn.getResponseCode() == 302 || conn.getResponseCode() == 200) {
				logger.info("https请求发送成功。");
				logger.info("返回码：" + conn.getResponseCode());
			}
			// 定义Bufferedinputstream输入流来读取URL的响应
			in = new BufferedInputStream(conn.getInputStream());
			byte abyte0[] = new byte[4096];
			StringBuffer stringbuffer = new StringBuffer();
			for (int i = 0; i != -1;) {
				i = in.read(abyte0, 0, 4096);
				if (i != -1) {
					String s2 = new String(abyte0, 0, i, "UTF-8");
					stringbuffer.append(s2);
				}
			}
			result = stringbuffer.toString();

		}
		// logger.info("远程主机返回的文本结果："+result);
		return result;
	}
	
	/** 应答内容 */
	private String resContent;


}
