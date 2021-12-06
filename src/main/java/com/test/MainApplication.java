package com.test;

import com.test.exceptions.NotFoundException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) throws NotFoundException {
        /*ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);
        UserService service = context.getBean(UserService.class);
        System.out.println(service.findByEmail("mels.hakobian@gmail.com"));*/
        SpringApplication.run(MainApplication.class, args);

    }
}
