package com.mit.community.util;

public class Client {
    public Client() {
    }

    public static Response execute(Request request) throws Exception {
        switch(request.getMethod()) {
            case GET:
                return HttpUtil.httpGet(request.getHost(), request.getPath(), request.getTimeout(), request.getHeaders(), request.getQuerys(), request.getSignHeaderPrefixList(), request.getAppKey(), request.getAppSecret());
            case GET_RESPONSE:
                return HttpUtil.httpImgGet(request.getHost(), request.getPath(), request.getTimeout(), request.getHeaders(), request.getQuerys(), request.getSignHeaderPrefixList(), request.getAppKey(), request.getAppSecret());
            case POST_STRING:
                return HttpUtil.httpPost(request.getHost(), request.getPath(), request.getTimeout(), request.getHeaders(), request.getQuerys(), request.getStringBody(), request.getSignHeaderPrefixList(), request.getAppKey(), request.getAppSecret());
            case POST_STRING_RESPONSE:
                return HttpUtil.httpImgPost(request.getHost(), request.getPath(), request.getTimeout(), request.getHeaders(), request.getQuerys(), request.getStringBody(), request.getSignHeaderPrefixList(), request.getAppKey(), request.getAppSecret());
            case POST_BYTES:
                return HttpUtil.httpPost(request.getHost(), request.getPath(), request.getTimeout(), request.getHeaders(), request.getQuerys(), request.getBytesBody(), request.getSignHeaderPrefixList(), request.getAppKey(), request.getAppSecret());
            case PUT_STRING:
                return HttpUtil.httpPut(request.getHost(), request.getPath(), request.getTimeout(), request.getHeaders(), request.getQuerys(), request.getStringBody(), request.getSignHeaderPrefixList(), request.getAppKey(), request.getAppSecret());
            case PUT_BYTES:
                return HttpUtil.httpPut(request.getHost(), request.getPath(), request.getTimeout(), request.getHeaders(), request.getQuerys(), request.getBytesBody(), request.getSignHeaderPrefixList(), request.getAppKey(), request.getAppSecret());
            case DELETE:
                return HttpUtil.httpDelete(request.getHost(), request.getPath(), request.getTimeout(), request.getHeaders(), request.getQuerys(), request.getSignHeaderPrefixList(), request.getAppKey(), request.getAppSecret());
            default:
                throw new IllegalArgumentException(String.format("unsupported method:%s", request.getMethod()));
        }
    }
}
