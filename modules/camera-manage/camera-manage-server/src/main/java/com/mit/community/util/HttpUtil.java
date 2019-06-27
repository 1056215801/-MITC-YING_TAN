package com.mit.community.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtil {
    public HttpUtil() {
    }

    public static Response httpGet(String host, String path, int connectTimeout, Map<String, String> headers, Map<String, String> querys, List<String> signHeaderPrefixList, String appKey, String appSecret) throws Exception {
        headers = initialBasicHeader("GET", path, headers, querys, (Map)null, signHeaderPrefixList, appKey, appSecret);
        HttpClient httpClient = wrapClient(host);
        Response r = null;

        try {
            httpClient.getParams().setParameter("http.connection.timeout", getTimeout(connectTimeout));
            HttpGet get = new HttpGet(initUrl(host, path, querys));
            Iterator var11 = headers.entrySet().iterator();

            while(var11.hasNext()) {
                Entry<String, String> e = (Entry)var11.next();
                get.addHeader((String)e.getKey(), MessageDigestUtil.utf8ToIso88591((String)e.getValue()));
            }

            HttpResponse rp = httpClient.execute(get);
            r = convert(rp);
        } catch (ClientProtocolException var18) {
            var18.printStackTrace();
        } catch (UnsupportedEncodingException var19) {
            var19.printStackTrace();
        } catch (IOException var20) {
            var20.printStackTrace();
        } finally {
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
            }

        }

        return r;
    }

    public static Response httpImgGet(String host, String path, int connectTimeout, Map<String, String> headers, Map<String, String> querys, List<String> signHeaderPrefixList, String appKey, String appSecret) throws Exception {
        headers = initialBasicHeader("GET", path, headers, querys, (Map)null, signHeaderPrefixList, appKey, appSecret);
        HttpClient httpClient = wrapClient(host);
        Response r = null;

        try {
            try {
                httpClient.getParams().setParameter("http.connection.timeout", getTimeout(connectTimeout));
                HttpGet get = new HttpGet(initUrl(host, path, querys));
                Iterator var11 = headers.entrySet().iterator();

                while(var11.hasNext()) {
                    Entry<String, String> e = (Entry)var11.next();
                    get.addHeader((String)e.getKey(), MessageDigestUtil.utf8ToIso88591((String)e.getValue()));
                }

                HttpResponse rp = httpClient.execute(get);
                r = convertImg(rp);
            } catch (ClientProtocolException var18) {
                var18.printStackTrace();
            } catch (UnsupportedEncodingException var19) {
                var19.printStackTrace();
            } catch (IOException var20) {
                var20.printStackTrace();
            }

            return r;
        } finally {
            ;
        }
    }




    public static Response httpPost(String host, String path, int connectTimeout, Map<String, String> headers, Map<String, String> querys, String body, List<String> signHeaderPrefixList, String appKey, String appSecret) throws Exception {
        String contentType = (String)headers.get("Content-Type");
        if ("application/x-www-form-urlencoded;charset=UTF-8".equals(contentType)) {
            Map<String, String> paramMap = strToMap(body);
            String modelDatas = (String)paramMap.get("modelDatas");
            if (StringUtils.isNotBlank(modelDatas)) {
                paramMap.put("modelDatas", URLDecoder.decode(modelDatas));
            }

            headers = initialBasicHeader("POST", path, headers, querys, paramMap, signHeaderPrefixList, appKey, appSecret);
        } else {
            headers = initialBasicHeader("POST", path, headers, querys, (Map)null, signHeaderPrefixList, appKey, appSecret);
        }

        HttpClient httpClient = wrapClient(host);
        httpClient.getParams().setParameter("http.connection.timeout", getTimeout(connectTimeout));
        HttpPost post = new HttpPost(initUrl(host, path, querys));
        Iterator var12 = headers.entrySet().iterator();

        while(var12.hasNext()) {
            Entry<String, String> e = (Entry)var12.next();
            post.addHeader((String)e.getKey(), MessageDigestUtil.utf8ToIso88591((String)e.getValue()));
        }

        if (StringUtils.isNotBlank(body)) {
            post.setEntity(new StringEntity(body, "UTF-8"));
        }

        return convert(httpClient.execute(post));
    }

    public static Response httpImgPost(String host, String path, int connectTimeout, Map<String, String> headers, Map<String, String> querys, String body, List<String> signHeaderPrefixList, String appKey, String appSecret) throws Exception {
        String contentType = (String)headers.get("Content-Type");
        if ("application/x-www-form-urlencoded;charset=UTF-8".equals(contentType)) {
            Map<String, String> paramMap = strToMap(body);
            String modelDatas = (String)paramMap.get("modelDatas");
            if (StringUtils.isNotBlank(modelDatas)) {
                paramMap.put("modelDatas", URLDecoder.decode(modelDatas));
            }

            headers = initialBasicHeader("POST", path, headers, querys, paramMap, signHeaderPrefixList, appKey, appSecret);
        } else {
            headers = initialBasicHeader("POST", path, headers, querys, (Map)null, signHeaderPrefixList, appKey, appSecret);
        }

        HttpClient httpClient = wrapClient(host);
        httpClient.getParams().setParameter("http.connection.timeout", getTimeout(connectTimeout));
        HttpPost post = new HttpPost(initUrl(host, path, querys));
        Iterator var12 = headers.entrySet().iterator();

        while(var12.hasNext()) {
            Entry<String, String> e = (Entry)var12.next();
            post.addHeader((String)e.getKey(), MessageDigestUtil.utf8ToIso88591((String)e.getValue()));
        }

        if (StringUtils.isNotBlank(body)) {
            post.setEntity(new StringEntity(body, "UTF-8"));
        }

        return convertImg(httpClient.execute(post));
    }

    public static Response httpPost(String host, String path, int connectTimeout, Map<String, String> headers, Map<String, String> querys, byte[] bodys, List<String> signHeaderPrefixList, String appKey, String appSecret) throws Exception {
        headers = initialBasicHeader("POST", path, headers, querys, (Map)null, signHeaderPrefixList, appKey, appSecret);
        HttpClient httpClient = wrapClient(host);
        httpClient.getParams().setParameter("http.connection.timeout", getTimeout(connectTimeout));
        HttpPost post = new HttpPost(initUrl(host, path, querys));
        Iterator var11 = headers.entrySet().iterator();

        while(var11.hasNext()) {
            Entry<String, String> e = (Entry)var11.next();
            post.addHeader((String)e.getKey(), MessageDigestUtil.utf8ToIso88591((String)e.getValue()));
        }

        if (bodys != null) {
            post.setEntity(new ByteArrayEntity(bodys));
        }

        return convert(httpClient.execute(post));
    }

    public static Response httpPut(String host, String path, int connectTimeout, Map<String, String> headers, Map<String, String> querys, String body, List<String> signHeaderPrefixList, String appKey, String appSecret) throws Exception {
        headers = initialBasicHeader("PUT", path, headers, querys, (Map)null, signHeaderPrefixList, appKey, appSecret);
        HttpClient httpClient = wrapClient(host);
        httpClient.getParams().setParameter("http.connection.timeout", getTimeout(connectTimeout));
        HttpPut put = new HttpPut(initUrl(host, path, querys));
        Iterator var11 = headers.entrySet().iterator();

        while(var11.hasNext()) {
            Entry<String, String> e = (Entry)var11.next();
            put.addHeader((String)e.getKey(), MessageDigestUtil.utf8ToIso88591((String)e.getValue()));
        }

        if (StringUtils.isNotBlank(body)) {
            put.setEntity(new StringEntity(body, "UTF-8"));
        }

        return convert(httpClient.execute(put));
    }

    public static Response httpPut(String host, String path, int connectTimeout, Map<String, String> headers, Map<String, String> querys, byte[] bodys, List<String> signHeaderPrefixList, String appKey, String appSecret) throws Exception {
        headers = initialBasicHeader("PUT", path, headers, querys, (Map)null, signHeaderPrefixList, appKey, appSecret);
        HttpClient httpClient = wrapClient(host);
        httpClient.getParams().setParameter("http.connection.timeout", getTimeout(connectTimeout));
        HttpPut put = new HttpPut(initUrl(host, path, querys));
        Iterator var11 = headers.entrySet().iterator();

        while(var11.hasNext()) {
            Entry<String, String> e = (Entry)var11.next();
            put.addHeader((String)e.getKey(), MessageDigestUtil.utf8ToIso88591((String)e.getValue()));
        }

        if (bodys != null) {
            put.setEntity(new ByteArrayEntity(bodys));
        }

        return convert(httpClient.execute(put));
    }

    public static Response httpDelete(String host, String path, int connectTimeout, Map<String, String> headers, Map<String, String> querys, List<String> signHeaderPrefixList, String appKey, String appSecret) throws Exception {
        headers = initialBasicHeader("DELETE", path, headers, querys, (Map)null, signHeaderPrefixList, appKey, appSecret);
        HttpClient httpClient = wrapClient(host);
        httpClient.getParams().setParameter("http.connection.timeout", getTimeout(connectTimeout));
        HttpDelete delete = new HttpDelete(initUrl(host, path, querys));
        Iterator var10 = headers.entrySet().iterator();

        while(var10.hasNext()) {
            Entry<String, String> e = (Entry)var10.next();
            delete.addHeader((String)e.getKey(), MessageDigestUtil.utf8ToIso88591((String)e.getValue()));
        }

        return convert(httpClient.execute(delete));
    }

    private static UrlEncodedFormEntity buildFormEntity(Map<String, String> formParam) throws UnsupportedEncodingException {
        if (formParam == null) {
            return null;
        } else {
            List<NameValuePair> nameValuePairList = new ArrayList();
            Iterator var2 = formParam.keySet().iterator();

            while(var2.hasNext()) {
                String key = (String)var2.next();
                nameValuePairList.add(new BasicNameValuePair(key, (String)formParam.get(key)));
            }

            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            formEntity.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
            return formEntity;
        }
    }

    public static String initUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (!StringUtils.isBlank(path)) {
            sbUrl.append(path);
        }

        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            Iterator var5 = querys.entrySet().iterator();

            while(var5.hasNext()) {
                Entry<String, String> query = (Entry)var5.next();
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }

                if (StringUtils.isBlank((CharSequence)query.getKey()) && !StringUtils.isBlank((CharSequence)query.getValue())) {
                    sbQuery.append((String)query.getValue());
                }

                if (!StringUtils.isBlank((CharSequence)query.getKey())) {
                    sbQuery.append((String)query.getKey());
                    if (!StringUtils.isBlank((CharSequence)query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode((String)query.getValue(), "UTF-8"));
                    }
                }
            }

            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }

        return sbUrl.toString();
    }

    private static Map<String, String> initialBasicHeader(String method, String path, Map<String, String> headers, Map<String, String> querys, Map<String, String> bodys, List<String> signHeaderPrefixList, String appKey, String appSecret) throws MalformedURLException {
        if (headers == null) {
            headers = new HashMap();
        }

        ((Map)headers).put("x-ca-timestamp", String.valueOf((new Date()).getTime()));
        ((Map)headers).put("x-ca-nonce", UUID.randomUUID().toString());
        ((Map)headers).put("x-ca-key", appKey);
        ((Map)headers).put("x-ca-signature", SignUtil.sign(appSecret, method, path, (Map)headers, querys, bodys, signHeaderPrefixList));
        return (Map)headers;
    }

    private static int getTimeout(int timeout) {
        return timeout == 0 ? 100 : timeout;
    }

    private static Response convert(HttpResponse response) throws IOException {
        Response res = new Response();
        if (null != response) {
            res.setStatusCode(response.getStatusLine().getStatusCode());
            Header[] var2 = response.getAllHeaders();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Header header = var2[var4];
                res.setHeader(header.getName(), MessageDigestUtil.iso88591ToUtf8(header.getValue()));
            }

            res.setContentType(res.getHeader("Content-Type"));
            res.setRequestId(res.getHeader("X-Ca-Request-Id"));
            res.setErrorMessage(res.getHeader("X-Ca-Error-Message"));
            if (response.getEntity() == null) {
                res.setBody((String)null);
            } else {
                res.setBody(readStreamAsStr(response.getEntity().getContent()));
            }
        } else {
            res.setStatusCode(500);
            res.setErrorMessage("No Response");
        }

        return res;
    }

    private static Response convertImg(HttpResponse response) throws IOException {
        Response res = new Response();
        if (null != response) {
            if (302 == response.getStatusLine().getStatusCode()) {
                Header header = response.getFirstHeader("location");
                String newUrl = header.getValue();
                HttpGet httpget = new HttpGet(newUrl);
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse response1 = httpClient.execute(httpget);
                response = response1;
                int var7 = response1.getStatusLine().getStatusCode();
            }

            res.setStatusCode(response.getStatusLine().getStatusCode());
            Header[] var8 = response.getAllHeaders();
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
                Header header = var8[var10];
                res.setHeader(header.getName(), MessageDigestUtil.iso88591ToUtf8(header.getValue()));
            }

            res.setContentType(res.getHeader("Content-Type"));
            res.setRequestId(res.getHeader("X-Ca-Request-Id"));
            res.setErrorMessage(res.getHeader("X-Ca-Error-Message"));
            res.setResponse(response);
        } else {
            res.setStatusCode(500);
            res.setErrorMessage("No Response");
        }

        return res;
    }

    public static String readStreamAsStr(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        WritableByteChannel dest = Channels.newChannel(bos);
        ReadableByteChannel src = Channels.newChannel(is);
        ByteBuffer bb = ByteBuffer.allocate(4096);

        while(src.read(bb) != -1) {
            bb.flip();
            dest.write(bb);
            bb.clear();
        }

        src.close();
        dest.close();
        return new String(bos.toByteArray(), "UTF-8");
    }

    public static String readImageAsStr(byte[] src) throws IOException {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src != null && src.length > 0) {
            for(int i = 0; i < src.length; ++i) {
                int v = src[i] & 255;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }

                stringBuilder.append(hv);
            }

            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public static String inStream2String(InputStream src) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        boolean var3 = true;

        int len;
        while((len = src.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }

        return new String(baos.toByteArray());
    }

    private static HttpClient wrapClient(String host) {
        HttpClient httpClient = new DefaultHttpClient();
        if (host.startsWith("https://")) {
            sslClient(httpClient);
        }

        return httpClient;
    }

    private static void sslClient(HttpClient httpClient) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] xcs, String str) {
                }

                public void checkServerTrusted(X509Certificate[] xcs, String str) {
                }
            };
            ctx.init((KeyManager[])null, new TrustManager[]{tm}, (SecureRandom)null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (KeyManagementException var6) {
            throw new RuntimeException(var6);
        } catch (NoSuchAlgorithmException var7) {
            throw new RuntimeException(var7);
        }
    }

    private static Map<String, String> strToMap(String str) {
        HashMap map = new HashMap();

        try {
            String[] params = str.split("&");
            String[] var3 = params;
            int var4 = params.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String param = var3[var5];
                String[] a = param.split("=");
                map.put(a[0], a[1]);
            }

            return map;
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        }
    }
}
