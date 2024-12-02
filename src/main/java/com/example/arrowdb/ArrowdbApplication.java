package com.example.arrowdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;

@SpringBootApplication
@EnableEnversRepositories
public class ArrowdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArrowdbApplication.class, args);
    }

}