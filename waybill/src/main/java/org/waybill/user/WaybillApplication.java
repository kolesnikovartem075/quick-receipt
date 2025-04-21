package org.waybill.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableConfigurationProperties
@EnableFeignClients
public class WaybillApplication {


    public static void main(String[] args) {
        SpringApplication.run(WaybillApplication.class, args);
        System.out.println("UserApplication started");
    }
}
