package com.telecom.platform.repository;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration(exclude = {
            EmbeddedMongoAutoConfiguration.class,
            MongoAutoConfiguration.class,
            MongoDataAutoConfiguration.class
        })
@ComponentScan(
        basePackages = { "com.telecom.platform" },
        excludeFilters = { @ComponentScan.Filter(classes = { SpringBootApplication.class }) }
        )
public class ConfigServerWithFongoConfiguration extends AbstractFongoBaseConfiguration{
}
