package com.lucasdonsilva.shortener.unit.controller;

import com.lucasdonsilva.shortener.controller.ShortenerController;
import com.lucasdonsilva.shortener.dto.RequestShortenerDTO;
import com.lucasdonsilva.shortener.dto.ResponseShortenerDTO;
import com.lucasdonsilva.shortener.exception.InvalidUrlException;
import com.lucasdonsilva.shortener.exception.NotFoundException;
import com.lucasdonsilva.shortener.service.ShortenerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponentsBuilder;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
public class ShortenerControllerTest {

    @Mock
    private ShortenerService service;

    @InjectMocks
    private ShortenerController controller;

    @Test
    @DisplayName("Create an alias successfully, set status code 201 and send in header location")
    public void create() throws InvalidUrlException {

        var body = new RequestShortenerDTO();
        body.setUrl("www.google.com");

        when(service.create(body)).thenReturn("abc");

        var response = controller.create(body, UriComponentsBuilder.newInstance());

        assertThat("/shorteners/abc").isEqualTo(response.getHeaders().getLocation().getPath());
        assertThat(CREATED).isEqualTo(response.getStatusCode());
        verify(service).create(body);
    }

    @Test
    @DisplayName("Find an alias successfully, set status code 302 and send in header location")
    public void findByAlias() throws NotFoundException {

        when(service.findByAlias("abc")).thenReturn("www.google.com");

        var response = controller.findByAlias("abc");

        assertThat("www.google.com").isEqualTo(response.getHeaders().getLocation().toString());
        assertThat(FOUND).isEqualTo(response.getStatusCode());
        verify(service).findByAlias("abc");
    }

    @Test
    @DisplayName("Find Top Ten By Access successfully, set status code 200 and send in header location")
    public void findTopTen() {

        var res1 = new ResponseShortenerDTO();
        res1.setUrl("www.google.com");
        res1.setAlias("abc");
        res1.setAccess(10);

        var res2 = new ResponseShortenerDTO();
        res2.setUrl("www.facebook.com");
        res2.setAlias("efg");
        res2.setAccess(20);

        var res3 = new ResponseShortenerDTO();
        res3.setUrl("www.g1.globo.com");
        res3.setAlias("hij");
        res3.setAccess(50);

        var shorteners = asList(res3, res2, res1);
        when(service.findTopTen()).thenReturn(shorteners);

        var response = controller.findTopTenByAccess();

        assertThat(shorteners.size()).isEqualTo(3);
        assertThat(shorteners.get(0).getUrl()).isEqualTo("www.g1.globo.com");
        assertThat(shorteners.get(0).getAlias()).isEqualTo("hij");
        assertThat(shorteners.get(0).getAccess()).isEqualTo(50);
        assertThat(shorteners.get(1).getUrl()).isEqualTo("www.facebook.com");
        assertThat(shorteners.get(1).getAlias()).isEqualTo("efg");
        assertThat(shorteners.get(1).getAccess()).isEqualTo(20);
        assertThat(shorteners.get(2).getUrl()).isEqualTo("www.google.com");
        assertThat(shorteners.get(2).getAlias()).isEqualTo("abc");
        assertThat(shorteners.get(2).getAccess()).isEqualTo(10);
        assertThat(OK).isEqualTo(response.getStatusCode());
        verify(service).findTopTen();
    }
}
