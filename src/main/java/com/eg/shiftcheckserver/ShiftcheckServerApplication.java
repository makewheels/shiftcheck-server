package com.eg.shiftcheckserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShiftcheckServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiftcheckServerApplication.class, args);
    }

}
