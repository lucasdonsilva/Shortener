package com.lucasdonsilva.shortener.integration.service;

import com.lucasdonsilva.shortener.document.ShortenerDocument;
import com.lucasdonsilva.shortener.dto.RequestShortenerDTO;
import com.lucasdonsilva.shortener.exception.InvalidUrlException;
import com.lucasdonsilva.shortener.exception.NotFoundException;
import com.lucasdonsilva.shortener.integration.config.IntegrationTestIT;
import com.lucasdonsilva.shortener.service.ShortenerService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ShortenerServiceIT extends IntegrationTestIT {

    @Autowired
    private ShortenerService service;

    @Test
    @DisplayName("Create an alias successfully")
    public void createSuccess() throws InvalidUrlException {

        var dto = new RequestShortenerDTO();
        dto.setUrl("http://www.facebook.com");

        var s = service.create(dto);

        assertThat(s).isNotBlank();
    }

    @Test
    @DisplayName("Return error when try create an alias with url broken")
    public void createErrorUrlBroken() {

        var dto = new RequestShortenerDTO();
        dto.setUrl("safsafdfdas");

        InvalidUrlException exception = org.junit.jupiter.api.Assertions.assertThrows(InvalidUrlException.class, () -> service.create(dto));

        assertThat("url safsafdfdas is invalid.").isEqualTo(exception.getMessage());
    }

    @Test
    @DisplayName("Find alias successfully")
    public void findByAliasSuccess() throws Exception {

        var document = new ShortenerDocument();
        document.setId(new ObjectId());
        document.setUrl("http://www.facebook.com");
        document.setAlias("faceb");
        document.setAccess(0);

        mongoTemplate.save(document);

        var url = service.findByAlias(document.getAlias());

        assertThat(url).isEqualTo(document.getUrl());
    }

    @Test
    @DisplayName("Return error when try find alias that not exists")
    public void findByAliasNotFound() {

        NotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> service.findByAlias("safsafdfdas"));

        assertThat("alias safsafdfdas not found.").isEqualTo(exception.getMessage());
    }

    @Test
    @DisplayName("Find top ten successfully, set status code 200 and return body")
    public void findTopTenByAccessSuccess() {

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

        var list = service.findTopTen();

        assertThat(list.size()).isEqualTo(3);
        assertThat(list.get(0).getUrl()).isEqualTo("http://www.facebook.com");
        assertThat(list.get(0).getAlias()).isEqualTo("faceb");
        assertThat(list.get(0).getAccess()).isEqualTo(100);
        assertThat(list.get(1).getUrl()).isEqualTo("http://www.google.com");
        assertThat(list.get(1).getAlias()).isEqualTo("googl");
        assertThat(list.get(1).getAccess()).isEqualTo(50);
        assertThat(list.get(2).getUrl()).isEqualTo("http://www.g1.com");
        assertThat(list.get(2).getAlias()).isEqualTo("g1");
        assertThat(list.get(2).getAccess()).isEqualTo(10);
    }
}
