package com.colotnet.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.colotnet.util.ConfigUtils;
import com.colotnet.util.SSLClient;
import com.colotnet.util.SignUtils;
/**
 * 类ApplePayConsumeTest.java的实现描述：消费
 * @author tulu 2016年4月27日 下午3:15:55
 */
public class ApplePayConsumeTest {

    public static void main(String[] args) throws Exception {
        DefaultHttpClient httpClient = new SSLClient();
        HttpPost postMethod = new HttpPost(ConfigUtils.getProperty("trans_url"));
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("requestNo", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())));
        nvps.add(new BasicNameValuePair("version", "V1.0"));
        nvps.add(new BasicNameValuePair("productId", "0105"));
        nvps.add(new BasicNameValuePair("transId", "01"));
        nvps.add(new BasicNameValuePair("merNo", ConfigUtils.getProperty("merchant_no")));
        nvps.add(new BasicNameValuePair("orderDate", new SimpleDateFormat("yyyyMMdd").format(new Date())));
        nvps.add(new BasicNameValuePair("orderNo", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
        nvps.add(new BasicNameValuePair("returnUrl", ""));
        nvps.add(new BasicNameValuePair("notifyUrl", ""));
        nvps.add(new BasicNameValuePair("transAmt", "1"));
        nvps.add(new BasicNameValuePair("commodityName", "test"));
        nvps.add(new BasicNameValuePair("acctNo", "6259020040003629"));
        nvps.add(new BasicNameValuePair("customerName", "tom"));
        nvps.add(new BasicNameValuePair("pin", "81103e8d9ea8afc5c6147b09e7cd2d8db88b82080021ffffffffffff"));
        nvps.add(new BasicNameValuePair("icCardData", "nyYI/RZhrTzcBF+fNgIAAYECACGDaMOcdhXu5YlSgbnWkLdA1R4D4A7AQ3BAxKYgz7w6ElseNh0uWnMWPtLLtu+/Cg98b/1MSLOhfPfkTtm9QSBb8jqVBMC96llxK7aWP2yY/rt1tCLy9Up6dxjmf2IhbvvTX1LnOCnOEQn+"));
        nvps.add(new BasicNameValuePair("signature", SignUtils.signData(nvps)));
        try {
            postMethod.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            HttpResponse resp = httpClient.execute(postMethod);
            String str = EntityUtils.toString(resp.getEntity(), "UTF-8");
            int statusCode = resp.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                boolean signFlag = SignUtils.verferSignData(str);
                if (!signFlag) {
                    System.out.println("验签失败");
                    return;
                }
                System.out.println("验签成功");
                return;
            }
            System.out.println("返回错误码:" + statusCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
