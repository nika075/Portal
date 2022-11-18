package com.portal.ludzie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class LudzieApplication {

    public static void main(String[] args) {
        SpringApplication.run(LudzieApplication.class, args);
    }

}
