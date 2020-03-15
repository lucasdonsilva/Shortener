package com.lucasdonsilva.shortener.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Random;
import java.util.UUID;

@Data
public class RequestShortenerDTO {

    @NotBlank(message = "url is required.")
    private String url;
    @JsonIgnore
    private String alias;

    public void generateAlias() {
        this.alias = Integer.toString(Math.abs(new Random().nextInt()), 36);
    }
}