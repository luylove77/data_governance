package com.luy.dwm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 主程序类
 */

@SpringBootApplication
@EnableScheduling
public class MainApplication {
    public static void main(String[] args) {
            SpringApplication.run(MainApplication.class, args);
    }
}
