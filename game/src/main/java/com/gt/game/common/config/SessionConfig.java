package com.gt.game.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * 这个类用配置redis服务器的连接
 * maxInactiveIntervalInSeconds为SpringSession的过期时间（单位：秒）
 * Created by psr on 2017/10/28 0028.
 */
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)
public class SessionConfig {

    @Value("${redisSession.cookieName}")
    private String cookieName;
    @Value("${redisSession.cookiePath}")
    private String cookiePath;
    @Value("${redisSession.domainName}")
    private String domainName;

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.database}")
    private int database;
    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public DefaultCookieSerializer defaultCookieSerializer() {
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setDomainName(domainName);
        defaultCookieSerializer.setCookieName(cookieName);
        defaultCookieSerializer.setCookiePath(cookiePath);
        return defaultCookieSerializer;
    }

    @Bean
    public JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setDatabase(database);
        factory.setTimeout(timeout); //设置连接超时时间
        factory.setPassword(password);
        return factory;
    }

}
