package com.dbc.vemcv;

import com.dbc.vemcv.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@EnableFeignClients
@EnableScheduling
public class VemCvApplication {

    public static void main(String[] args) {
        SpringApplication.run(VemCvApplication.class, args);
    }

}
