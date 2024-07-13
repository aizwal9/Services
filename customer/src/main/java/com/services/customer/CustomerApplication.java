package com.services.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(
        scanBasePackages = {
                "com.services.customer",
                "com.services.amqp"
        }
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.services.client"
)
@PropertySources({
        @PropertySource("classpath:client-${spring.profiles.active}.properties")
})
public class CustomerApplication {
    public static void main(String[] args){
        SpringApplication.run(CustomerApplication.class,args);
    }
}
