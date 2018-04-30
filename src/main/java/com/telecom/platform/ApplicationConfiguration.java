package com.telecom.platform;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public MongoClient mongoClient(@Value("${spring.data.mongodb.host}") String mongoHost,
                                   @Value("${spring.data.mongodb.port}") String mongoPort) {
        return new MongoClient(mongoHost.concat(":").concat(mongoPort));
    }

}
