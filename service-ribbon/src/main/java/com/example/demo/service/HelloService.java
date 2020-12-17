package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class HelloService {
    @Autowired
    RestTemplate restTemplate;
    
    @HystrixCommand(fallbackMethod="helloError")
    public String helloService() {
        System.out.println("helloService。");

        return restTemplate.postForObject("http://querycenter2/test/hello", null, String.class);
    }
    
    public String helloError() {
    	return "error happens in Ribbon!";
    }
}

