package com.bookkeeping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.filter.GenericFilterBean;

import java.util.stream.Stream;

@SpringBootApplication
public class BookkeepingApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BookkeepingApplication.class, args);
//        Stream.of(context.getBeanNamesForType(GenericFilterBean.class))
//        .forEach(name-> System.err.println(name));
    }
}
