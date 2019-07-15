package com.atguigu.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebRestfulcrudApplication implements HealthIndicator {

    public static void main(String[] args) {
        SpringApplication.run(WebRestfulcrudApplication.class, args);
    }

    /**
     * 在/health接口调用的时候，返回多一个属性："WebRestfulcrudApplication":{"status":"UP","hello":"world"}
     */
    @Override
    public Health health() {
        return Health.up().withDetail("hello", "world").build();
    }
}
