package com.letsun.frame.redis.cache;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ClassUtils;

/**
 * @Desc redis配置  
 * @author YY  
 * @date 2018年4月12日
 */
@Configuration
@EnableCaching
public class RedisConfig {
	
	public static final int NO_PARAM_KEY = 0;  
    public static final int NULL_PARAM_KEY = 53;  
    
	/**
	 * @Desc 自定义key 此方法将会根据类名+方法名+所有参数的值生成唯一的一个key
	 * 即使@Cacheable中的value属性一样，key也会不一样
	 * @return
	 * @author YY<2017年11月8日>
	 */
	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName()+":" + method.getName());
				if (params.length == 0) {  
		            return sb.append(NO_PARAM_KEY).toString();  
		        }
				sb.append("[");
				int n = 0;
				for (Object param : params) {
					if (param == null) {
						sb.append(NULL_PARAM_KEY);
					} else if (ClassUtils.isPrimitiveArray(param.getClass())) {
						int length = Array.getLength(param);
						for (int i = 0; i < length; i++) {
							sb.append(Array.get(param, i));
						}
					} else if (ClassUtils.isPrimitiveOrWrapper(param.getClass()) || param instanceof String) {
						sb.append(param);
					}else if(param instanceof Map){
						Map<?, ?> map  = (Map<?, ?>)param;
						Map<Object, Object> newMap  = new HashMap<Object, Object>();
						map.forEach((k,v) -> {
							//时间类型不作为redis的key
							if(!(v instanceof Date)){
								newMap.put(k, v);
							}
						});
						sb.append(newMap.toString());
					}else if(param instanceof List){
						List<?> list  = (List<?>)param;
						list = list.stream().filter(obj -> !(obj instanceof Date)).collect(Collectors.toList());
						sb.append(param.toString());
					}else {
						sb.append(param.hashCode());
					}
					if(n++ < params.length -1){
						sb.append(",");
					}
				}
				sb.append("]");
				return sb.toString();
			}
		};
	}

	/**
	 * @Desc 缓存管理器.
	 * @param redisTemplate
	 * @return
	 * @author YY<2017年11月8日>
	 */
	@Bean
	public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		// 默认超时时间半小时,单位秒
		cacheManager.setDefaultExpiration(1800);
		return cacheManager;
	}

	/**
	 * 重写Redis序列化方式，使用Json方式:
	 * 当我们的数据存储到Redis的时候，我们的键（key）和值（value）都是通过Spring提供的Serializer序列化到数据库的。
	 * RedisTemplate默认使用的是JdkSerializationRedisSerializer，
	 * StringRedisTemplate默认使用的是StringRedisSerializer。 Spring Data
	 * JPA为我们提供了下面的Serializer：
	 * GenericToStringSerializer、Jackson2JsonRedisSerializer、
	 * JacksonJsonRedisSerializer、JdkSerializationRedisSerializer、OxmSerializer、
	 * StringRedisSerializer。 在此我们将自己配置RedisTemplate并定义Serializer。
	 * 
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
	    FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<Object>(Object.class);
		//设置值（value）的序列化采用FastJsonRedisSerializer
		redisTemplate.setValueSerializer(fastJsonRedisSerializer);
		// 设置键（key）的序列化采用StringRedisSerializer。
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(redisSerializer);
		redisTemplate.setHashKeySerializer(redisSerializer);
		redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

}