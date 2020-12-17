package com.goldwater.querycenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class QuerycenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuerycenterApplication.class, args);
    }

}
