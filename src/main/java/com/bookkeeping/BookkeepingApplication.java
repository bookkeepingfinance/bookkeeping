package com.bookkeeping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookkeepingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookkeepingApplication.class, args);

        char c = '我';

        System.out.println((long)c);

    }

}
