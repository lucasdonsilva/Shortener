package com.lucasdonsilva.shortener.controller;

import com.lucasdonsilva.shortener.dto.RequestShortenerDTO;
import com.lucasdonsilva.shortener.dto.ResponseShortenerDTO;
import com.lucasdonsilva.shortener.exception.InvalidUrlException;
import com.lucasdonsilva.shortener.exception.NotFoundException;
import com.lucasdonsilva.shortener.service.ShortenerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/shorteners")
@RequiredArgsConstructor
@Slf4j
public class ShortenerController {

    private final ShortenerService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid RequestShortenerDTO body,
                                                       UriComponentsBuilder builder) throws InvalidUrlException {

        log.info("Accessing endpoint create with url: {}", body.getUrl());

        var response = service.create(body);
        var uri = builder.path("/shorteners/{alias}").buildAndExpand(response);

        return created(uri.toUri()).build();
    }

    @GetMapping("/{alias}")
    public ResponseEntity<?> findByAlias(@PathVariable String alias) throws NotFoundException {

        log.info("Accessing endpoint findByAlias with alias: {}", alias);

        var response = service.findByAlias(alias);

        return status(FOUND).header(LOCATION, response).build();
    }

    @GetMapping("/top10")
    public ResponseEntity<List<ResponseShortenerDTO>> findTopTenByAccess() {

        log.info("Accessing endpoint findTopTen.");

        var response = service.findTopTen();

        return ok().body(response);
    }
}

