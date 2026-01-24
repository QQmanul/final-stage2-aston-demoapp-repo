package ru.aston.homework4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {
        "ru.aston.homework2.entities",
        "ru.aston.kafka.dto"
})
@ComponentScan(basePackages = {
        "ru.aston.homework4",
        "ru.aston.homework5"
})
public class UserApplication {

    public static void main(String... args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
