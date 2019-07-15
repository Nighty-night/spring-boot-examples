package com.neo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
    @RequestMapping("/hello")
    public String index() {
        Logger logger = LoggerFactory.getLogger(HelloWorldController.class);
        logger.info("我要给乐西买CL萝卜丁");
        return "Hello World";
    }
}