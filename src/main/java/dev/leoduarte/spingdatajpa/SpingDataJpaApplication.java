package dev.leoduarte.spingdatajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpingDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpingDataJpaApplication.class, args);
    }

}
