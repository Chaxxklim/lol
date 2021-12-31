package com.example.lolapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LolAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(LolAppApplication.class, args);
    }

}
