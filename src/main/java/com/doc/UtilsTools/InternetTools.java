package com.doc.UtilsTools;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * com.doc.UtilsTools 于2019/4/8 由Administrator 创建 .
 */
@Component("InternetTools")
public class InternetTools {


    public String ApiGet(String GetPath) {
        String GetUrl = GetPath;
        String GetUser = "";
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Content-type", "application/json");
            GetUser = GetContent(GetUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GetUser;
    }


    public String ApiPost(String postPath, String postRequest) {
        String postUrl = postPath;
        String PostUser = "";
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Content-type", "application/json");
            PostUser = getPageContent(postUrl, postRequest, map,"POST");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PostUser;
    }


    public String getPageContent(String postUrl, String postRequest, Map<String, String> map,String method) {
        StringBuffer buffer = new StringBuffer();
        try {
            URL newUrl = new URL(postUrl);

            HttpURLConnection hConnection = (HttpURLConnection) newUrl.openConnection();
            if (postRequest.length() > 0) {
                if("POST".equals(method)){
                    hConnection.setDoOutput(true);
                    hConnection.setDoInput(true);
                    hConnection.setRequestMethod(method);
                }

//                hConnection.setRequestMethod("GET");
                for (String key : map.keySet()) {
                    hConnection.setRequestProperty(key, map.get(key));
                }
                if("POST".equals(method)){
                    OutputStreamWriter out = new OutputStreamWriter(hConnection.getOutputStream());
                    System.out.println(postRequest);
                    out.write(postRequest);
                    out.flush();
                    out.close();
                }

            }
            System.out.println("开始请求：" + postUrl);
            System.out.println("请求参数：" + postRequest);
            InputStream input = hConnection.getInputStream();
            System.out.println("请求结束：" + postUrl);
            InputStreamReader in = new InputStreamReader(input, "utf-8");
            BufferedReader rd = new BufferedReader(in);
            int ch;
            for (int length = 0; (ch = rd.read()) > -1; length++)
                buffer.append((char) ch);
            String s = buffer.toString();
            s.replaceAll("//&[a-zA-Z]{1,10};", "").replace("<[^>]>", "");
            System.out.println("请求获得：" + s);
            rd.close();
            hConnection.disconnect();
            System.out.println("返回的数据：" + buffer.toString().trim());
            System.out.println("--------------请求结束----------------");
            return buffer.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("请求错误返回：" + e.getMessage());
            return e.getMessage();
        }
    }

    private String GetContent(String strurl) throws IOException {
        URL url = new URL(strurl);
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bf = new BufferedReader(isr);

        String line;
        StringBuilder builder = new StringBuilder();
        while((line = bf.readLine()) != null)
        {
            builder.append(line);
        }

        bf.close();
        isr.close();
        is.close();

        System.out.println(builder.toString());
        return builder.toString();
    }

}
