package ru.felix.votingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class VotingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VotingServiceApplication.class, args);
    }
}
