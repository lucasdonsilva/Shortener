package com.lucasdonsilva.shortener.config;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@EnableCaching
public class CacheEvictConfiguration {

    @Scheduled(fixedRateString = "${cache.evict.time}")
    @CacheEvict(cacheNames = "alias", allEntries = true)
    public void cacheEvict(){}
}
