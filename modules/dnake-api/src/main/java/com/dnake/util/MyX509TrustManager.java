package com.dnake.util;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * 实现自己的证书信任管理器类
 *
 * @author Mr.Deng
 * @date 2018/11/7 13:58
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
public class MyX509TrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] arg0, String arg1) {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] arg0, String arg1) {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

}  
