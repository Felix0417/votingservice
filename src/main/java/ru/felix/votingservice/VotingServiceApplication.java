package ru.felix.votingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class VotingServiceApplication {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("restaurants", "users");
    }

    public static void main(String[] args) {
        SpringApplication.run(VotingServiceApplication.class, args);
    }
}
