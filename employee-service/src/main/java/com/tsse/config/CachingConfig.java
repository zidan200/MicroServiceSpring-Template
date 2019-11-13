package com.tsse.config;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {
 
    @Bean
    public CacheManager timeOutCacheManager() {
    	GuavaCacheManager  cacheManager = new GuavaCacheManager ();
    	cacheManager.setCacheBuilder(CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES));
        /*cacheManager.setCaches(Arrays.asList(
          new ConcurrentMapCache("employees"), 
          new ConcurrentMapCache("departments")));*/
        
        return cacheManager;
    }
}