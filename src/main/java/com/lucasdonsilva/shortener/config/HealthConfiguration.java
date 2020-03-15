package com.lucasdonsilva.shortener.config;

import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class HealthConfiguration implements HealthIndicator {

    private final MongoTemplate mongoTemplate;
    private final String mongoUri;

    public HealthConfiguration(MongoTemplate mongoTemplate, @Value("${spring.data.mongodb.uri}") String mongoUri) {
        this.mongoTemplate = mongoTemplate;
        this.mongoUri = mongoUri.replaceFirst("/\\w.+@", "/user:password@");
    }

    @Override
    public Health health() {
        try {
            mongoTemplate.getDb().runCommand(new BasicDBObject("ping", "1"));
            return Health.up().withDetail("reference", this.mongoUri).build();
        } catch (Exception ignored) {
            return Health.down().withDetail("reference", this.mongoUri).build();
        }
    }
}
