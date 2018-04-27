package com.letsun.frame.redis.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Desc redis session共享配置
 * 引入spring-session.xml为了让Spring Session不再执行config命令 ,腾讯云redis不支持config命令
 * @author YY  
 * @date 2018年4月12日
 */
@Configuration
@ImportResource(locations={"classpath:spring-session.xml"})
@EnableRedisHttpSession(redisNamespace = "com.letsun.session", maxInactiveIntervalInSeconds = 1800)
public class RedisSessionConfig {

}
