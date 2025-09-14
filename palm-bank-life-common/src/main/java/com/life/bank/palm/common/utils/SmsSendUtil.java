package com.life.bank.palm.common.utils;

import lombok.Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


@Data
public class SmsSendUtil {




    public static String sendMobile(String phone, String code){
        Client client = new Client();
        client.setAppId("hw_10157");     //开发者ID，在【设置】-【开发设置】中获取
        client.setSecretKey("保密");    //开发者密钥，在【设置】-【开发设置】中获取
        client.setVersion("1.0");

        // json格式可在 be json.com 进行校验
        Request request = new Request();
        String s =
                "{\"mobile\":[\"%phone%\"],\"type\":0,\"template_id\":\"ST_2020101100000008\",\"sign\":\"内推鸭\",\"send_time\":\"\",\"params\":{\"code\":%code%}}";
        String sendS = s.replace("%phone%", phone).replace("%code%", code);
        request.setBizContent(sendS
        );  // 这里是json字符串，send_time 为空时可以为null, params 为空时可以为null,短信签名填写审核后的签名本身，不需要填写签名id
        request.setMethod("sms.message.send");
        return execute(request, client);
    }

    private static String md5(String s) {
        char[] str = new char[32];
        char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] b = md.digest(s.getBytes());
            int k = 0;
            for (byte value : b) {
                str[k++] = hex[value >>> 4 & 0xf];
                str[k++] = hex[value & 0xf];
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new String(str);
    }

    private static String createSignature(HashMap<String, Object> data, String secretKey) {
        Object[] array = data.keySet().toArray();
        Arrays.sort(array);
        ArrayList<String> list = new ArrayList<>();
        for (Object key : array) {
            list.add(key + "=" + data.get(key));
        }
        list.add("key=" + secretKey);

        StringBuilder sb = new StringBuilder();
        for (String v : list) {
            sb.append(v);
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return md5(sb.toString()).toUpperCase();
    }

    private static String execute(Request request, Client client) {

        StringBuilder sb = new StringBuilder();

        HashMap<String, Object> post = new HashMap<>();
        post.put("app_id", client.appId);
        post.put("timestamp", client.timestamp);
        post.put("sign_type", client.signType);
        post.put("version", client.version);
        post.put("method", request.getMethod());
        post.put("biz_content", request.getBizContent());
        post.put("sign", createSignature(post, client.secretKey));

        ArrayList<String> list = new ArrayList<>();
        for (String key : post.keySet()) {
            list.add(key + "=" + post.get(key));
        }

        StringBuilder data = new StringBuilder();
        for (String v : list) {
            data.append(v);
            data.append("&");
        }
        data.deleteCharAt(data.length() - 1);

        try {
            URL url = new URL("http://api.shansuma.com/gateway.do");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setUseCaches(false);
            http.setConnectTimeout(15 * 1000);
            http.setReadTimeout(60 * 1000);
            http.setRequestMethod("POST");
            http.setRequestProperty("User-Agent", "Mozilla 5.0 Java-SMS-SDK v1.0.0 (Haowei Tech)");
            http.setRequestProperty("Connection", "close");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setRequestProperty("Content-Length", String.valueOf(data.length()));
            http.setDoOutput(true);
            http.setDoInput(true);

            OutputStream ops = http.getOutputStream();
            ops.write(data.toString().getBytes());
            ops.flush();
            ops.close();

            String next;
            InputStreamReader reader = new InputStreamReader(http.getInputStream());
            BufferedReader buffered = new BufferedReader(reader);
            while ((next = buffered.readLine()) != null) {
                sb.append(next);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }

    @Data
    public static class Request {
        private String bizContent;
        private String method;
    }

    @Data
    public static class Client {
        private String appId;
        private long timestamp;
        private String secretKey;
        private String version;
        private String signType;

        public Client() {
            this.timestamp = System.currentTimeMillis();
            this.version = "1.0";
            this.signType = "md5";
        }
    }

}
