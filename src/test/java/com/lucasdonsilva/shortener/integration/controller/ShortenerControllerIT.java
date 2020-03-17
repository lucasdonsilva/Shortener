package com.lucasdonsilva.shortener.integration.controller;

import com.lucasdonsilva.shortener.document.ShortenerDocument;
import com.lucasdonsilva.shortener.integration.config.IntegrationTestIT;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ShortenerControllerIT extends IntegrationTestIT {

    @Test
    @DisplayName("Create an alias successfully, set status code 201 and send in header location")
    public void createSuccess() throws Exception {

        mockMvc.perform(post("/shorteners").content(generateBody("body.json")).contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"));
    }

    @Test
    @DisplayName("Return error when try create an alias with null value, set status code 400 and send message in body")
    public void createErrorNullValue() throws Exception {

        mockMvc.perform(post("/shorteners").content(generateBody("body-url-null.json")).contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.url").value("url is required."));
    }

    @Test
    @DisplayName("Return error when try create an alias with url broken, set status code 422 and send message in body")
    public void createErrorUrlBroken() throws Exception {

        mockMvc.perform(post("/shorteners").content(generateBody("body-url-broken.json")).contentType(APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value("url abcdef is invalid."));
    }

    @Test
    @DisplayName("Find alias successfully, set status code 302 and send in header location")
    public void findByAliasSuccess() throws Exception {

        var document = new ShortenerDocument();
        document.setId(new ObjectId());
        document.setUrl("http://www.facebook.com");
        document.setAlias("faceb");
        document.setAccess(0);

        mongoTemplate.save(document);

        mockMvc.perform(get("/shorteners/faceb"))
                .andExpect(status().isFound())
                .andExpect(header().string("location", document.getUrl()));
    }

    @Test
    @DisplayName("Return error when try find alias, set status code 404 and send message in body")
    public void findByAliasNotFound() throws Exception {

        mockMvc.perform(get("/shorteners/aaaaa"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("alias aaaaa not found."));
    }

    @Test
    @DisplayName("Find top ten successfully, set status code 200 and return body")
    public void findTopTenByAccessSuccess() throws Exception {

        var document = new ShortenerDocument();
        document.setId(new ObjectId());
        document.setUrl("http://www.facebook.com");
        document.setAlias("faceb");
        document.setAccess(100);

        var document1 = new ShortenerDocument();
        document1.setId(new ObjectId());
        document1.setUrl("http://www.google.com");
        document1.setAlias("googl");
        document1.setAccess(50);

        var document2 = new ShortenerDocument();
        document2.setId(new ObjectId());
        document2.setUrl("http://www.g1.com");
        document2.setAlias("g1");
        document2.setAccess(10);

        mongoTemplate.save(document);
        mongoTemplate.save(document1);
        mongoTemplate.save(document2);

        mockMvc.perform(get("/shorteners/top10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].url").value(document.getUrl()))
                .andExpect(jsonPath("$[0].alias").value(document.getAlias()))
                .andExpect(jsonPath("$[0].access").value(document.getAccess()))
                .andExpect(jsonPath("$[1].url").value(document1.getUrl()))
                .andExpect(jsonPath("$[1].alias").value(document1.getAlias()))
                .andExpect(jsonPath("$[1].access").value(document1.getAccess()))
                .andExpect(jsonPath("$[2].url").value(document2.getUrl()))
                .andExpect(jsonPath("$[2].alias").value(document2.getAlias()))
                .andExpect(jsonPath("$[2].access").value(document2.getAccess()));
    }
}
