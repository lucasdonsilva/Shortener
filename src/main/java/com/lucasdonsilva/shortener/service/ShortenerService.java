package com.lucasdonsilva.shortener.service;

import com.lucasdonsilva.shortener.dto.RequestShortenerDTO;
import com.lucasdonsilva.shortener.dto.ResponseShortenerDTO;
import com.lucasdonsilva.shortener.exception.InvalidUrlException;
import com.lucasdonsilva.shortener.exception.NotFoundException;
import com.lucasdonsilva.shortener.mapper.ShortenerMapper;
import com.lucasdonsilva.shortener.repository.ShortenerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShortenerService {

    private final ShortenerRepository repository;
    private final ShortenerMapper mapper;

    public String create(RequestShortenerDTO dto) throws InvalidUrlException {

        validateUrl(dto.getUrl());
        do {
            dto.generateAlias();
        } while (repository.existsById(dto.getAlias()));

        return repository.save(mapper.mapToDocument(dto)).getAlias();
    }

    public String findByAlias(String alias) throws NotFoundException {

        var url = repository.findByAlias(alias).orElseThrow(() -> new NotFoundException(alias));
        url.incrementAccess();

        return repository.save(url).getUrl();
    }

    public List<ResponseShortenerDTO> findTopTen() {
        return mapper.mapToResponses(repository.findTop10ByOrderByAccessDesc());
    }

    private void validateUrl(String url) throws InvalidUrlException {
        try{
            new URL(url);
        } catch (MalformedURLException ex) {
            throw new InvalidUrlException(url);
        }
    }
}