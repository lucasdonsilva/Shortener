package com.lucasdonsilva.shortener.dto;

import lombok.Data;

@Data
public class ResponseShortenerDTO {
    private String url;
    private String alias;
    private Integer access;
}
