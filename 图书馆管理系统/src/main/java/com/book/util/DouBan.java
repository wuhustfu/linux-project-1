package com.book.util;

/**
 * Created by ambition on 17/11/18.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DouBan {
    public static String getJson (String isbn) {
        StringBuffer buffer = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = null;
        HttpURLConnection httpUrlConn = null;

        try {
            URL url = new URL("http://api.douban.com/book/subject/isbn/" + isbn);
            httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");

            inputStream = httpUrlConn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);

            buffer = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if(bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStreamReader != null){
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(httpUrlConn != null){
                httpUrlConn.disconnect();
            }
        }
        return (buffer == null) ? "null" : buffer.toString();
    }

    public static BigDecimal getMoney(String json) {
        String money = doFilter(json, "price");
        String regex =  "\\d*[.]\\d*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(money);
        BigDecimal resault = BigDecimal.ZERO;
        while (m.find()) {
            if (!"".equals(m.group())) {
                System.out.println(m.group());
                resault = BigDecimal.valueOf(Double.parseDouble(m.group()));
            }
        }
        return resault;
    }

    public static String getSummary(String json) {
        String regex = "(.*)<summary>(.*?)</summary>(.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);
        String result = "unknown";
        if (matcher.matches()) {
            result = matcher.group(2);
        }
        return result;
    }

    public static String doFilter(String json, String type) {
        String regex = "(.*)<db:attribute name=\"" + type +  "\">(.*?)</db:attribute>(.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);
        String result = "unknown";
        if (matcher.matches()) {
            result = matcher.group(2);
        }
        return result;
    }
}
