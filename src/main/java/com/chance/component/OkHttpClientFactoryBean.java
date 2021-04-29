package com.chance.component;

import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * @Description: OkHttpClientFactoryBean
 * @Author: chance
 * @Date: 4/24/21 1:11 AM
 * @Version 1.0
 */
@Component
public class OkHttpClientFactoryBean implements FactoryBean, DisposableBean {

    private int connectTimeout;
    private int readTimeout;
    private int writeTimeout;

    /**
     * http proxy config
     **/
    private String host;
    private int port;
    private String username;
    private String password;

    /**
     * OkHttpClient instance
     **/
    private OkHttpClient client;


    @Override
    public void destroy() throws Exception {
        if (client != null) {
            client.connectionPool().evictAll();
            client.dispatcher().executorService().shutdown();
            client.cache().close();
            client = null;
        }
    }

    @Override
    public Object getObject() throws Exception {
        ConnectionPool pool = new ConnectionPool(5, 10, TimeUnit.SECONDS);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(connectTimeout, TimeUnit.MICROSECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                .connectionPool(pool);

        if (StringUtils.isNotBlank(host) && port > 0) {
            Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(host, port));
            builder.proxy(proxy);
        }

        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            Authenticator proxyAuthenticator = new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    String credential = Credentials.basic(username, password);
                    return response.request().newBuilder()
                            .header("Proxy-Authorization", credential)
                            .build();
                }
            };
            builder.proxyAuthenticator(proxyAuthenticator);
        }

        client = builder.build();
        return client;
    }

    @Override
    public Class<?> getObjectType() {
        return OkHttpClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }
}
