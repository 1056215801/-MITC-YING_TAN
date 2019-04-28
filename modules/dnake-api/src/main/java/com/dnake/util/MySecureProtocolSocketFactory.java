package com.dnake.util;

import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ControllerThreadSocketFactory;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 安全协议套接字工厂
 *
 * @author Mr.Deng
 * @date 2018/11/7 17:16
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
public class MySecureProtocolSocketFactory implements
        SecureProtocolSocketFactory {

    private SSLContext sslContext = null;

    /**
     * Constructor for MySecureProtocolSocketFactory.
     */
    public MySecureProtocolSocketFactory() {
    }

    /**
     * @return SSLContext
     */
    private static SSLContext createEasySSLContext() {
        try {
            SSLContext context = SSLContext.getInstance("SSL");
            context.init(null, new TrustManager[]{new MyX509TrustManager()},
                    null);
            return context;
        } catch (Exception e) {
            throw new HttpClientError(e.toString());
        }
    }

    /**
     * @return SSLContext
     */
    private SSLContext getSSLContext() {
        if (this.sslContext == null) {
            this.sslContext = createEasySSLContext();
        }
        return this.sslContext;
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress clientHost,
                               int clientPort) throws IOException {

        return getSSLContext().getSocketFactory().createSocket(host, port,
                clientHost, clientPort);
    }

    @Override
    public Socket createSocket(final String host, final int port,
                               final InetAddress localAddress, final int localPort,
                               final HttpConnectionParams params) throws IOException {
        if (params == null) {
            throw new IllegalArgumentException("Parameters may not be null");
        }
        int timeout = params.getConnectionTimeout();
        if (timeout == 0) {
            return createSocket(host, port, localAddress, localPort);
        } else {
            return ControllerThreadSocketFactory.createSocket(this, host, port,
                    localAddress, localPort, timeout);
        }
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return getSSLContext().getSocketFactory().createSocket(host, port);
    }

    @Override
    public Socket createSocket(Socket socket, String host, int port,
                               boolean autoClose) throws IOException {
        return getSSLContext().getSocketFactory().createSocket(socket, host,
                port, autoClose);
    }
}  
