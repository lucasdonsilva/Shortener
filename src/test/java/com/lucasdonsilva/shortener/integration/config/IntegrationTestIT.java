package com.lucasdonsilva.shortener.integration.config;

import com.lucasdonsilva.shortener.ShortenerApplication;
import com.lucasdonsilva.shortener.document.ShortenerDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.Charset.forName;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.StreamUtils.copyToString;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "integration-test")
@AutoConfigureMockMvc
@SpringBootTest(classes = {ShortenerApplication.class, EmbeddedMongoAutoConfiguration.class})
public abstract class IntegrationTestIT {

    @Autowired
    public MongoTemplate mongoTemplate;

    @Autowired
    protected MockMvc mockMvc;

    @BeforeEach
    public void resetMongo() {
        mongoTemplate.dropCollection(ShortenerDocument.class);
    }

    protected String generateBody(String address) throws IOException {
        return copyToString(new ClassPathResource("/request/".concat(address)).getInputStream(), UTF_8);
    }
}