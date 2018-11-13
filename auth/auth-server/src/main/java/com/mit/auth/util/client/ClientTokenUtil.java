package com.mit.auth.util.client;

import com.mit.auth.common.util.jwt.IJWTInfo;
import com.mit.auth.common.util.jwt.JWTHelper;
import com.mit.auth.configuration.KeyConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * token
 * @author shuyy
 * @date 2018/11/7 10:53
 * @company mitesofor
*/
@Configuration
public class ClientTokenUtil {

    @Value("${client.expire}")
    private int expire;

    private final KeyConfiguration keyConfiguration;

    @Autowired
    public ClientTokenUtil(KeyConfiguration keyConfiguration) {
        this.keyConfiguration = keyConfiguration;
    }

    public String generateToken(IJWTInfo jwtInfo) throws Exception {
        return JWTHelper.generateToken(jwtInfo, keyConfiguration.getServicePriKey(), expire);
    }

    public IJWTInfo getInfoFromToken(String token) throws Exception {
        return JWTHelper.getInfoFromToken(token, keyConfiguration.getServicePubKey());
    }

}
