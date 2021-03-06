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
 * 类TransProxyPayTest.java的实现描述：单笔代付
 * @author tulu 2016年4月27日 下午3:15:55
 */
public class TransProxyPayTest {

    public static void main(String[] args) throws Exception {
        DefaultHttpClient httpClient = new SSLClient();
        HttpPost postMethod = new HttpPost(ConfigUtils.getProperty("trans_url"));
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("requestNo", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())));
        nvps.add(new BasicNameValuePair("version", "V1.0"));
        nvps.add(new BasicNameValuePair("productId", "0201"));// 0201-非垫支,0203-垫支(T0)
        nvps.add(new BasicNameValuePair("transId", "07"));
        nvps.add(new BasicNameValuePair("merNo", ConfigUtils.getProperty("merchant_no")));
        nvps.add(new BasicNameValuePair("orderDate", new SimpleDateFormat("yyyyMMdd").format(new Date())));
        nvps.add(new BasicNameValuePair("orderNo", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
        nvps.add(new BasicNameValuePair("notifyUrl", ""));
        nvps.add(new BasicNameValuePair("transAmt", "1"));
        nvps.add(new BasicNameValuePair("isCompay", "0"));// 0-对私,1-对公
        nvps.add(new BasicNameValuePair("phoneNo", "13312345678"));
        nvps.add(new BasicNameValuePair("customerName", "张三"));
        nvps.add(new BasicNameValuePair("cerdType", "01"));
        nvps.add(new BasicNameValuePair("cerdId", "110000197609260652"));
        nvps.add(new BasicNameValuePair("accBankNo", "105100000025"));
        nvps.add(new BasicNameValuePair("accBankName", "建设银行"));
        nvps.add(new BasicNameValuePair("acctNo", "6217007200007448098"));
        nvps.add(new BasicNameValuePair("note", "test"));
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
