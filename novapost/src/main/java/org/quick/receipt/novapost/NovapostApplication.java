package org.quick.receipt.novapost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class NovapostApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovapostApplication.class, args);
    }

}
