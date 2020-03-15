package com.lucasdonsilva.shortener.mapper;

import com.lucasdonsilva.shortener.document.ShortenerDocument;
import com.lucasdonsilva.shortener.dto.RequestShortenerDTO;
import com.lucasdonsilva.shortener.dto.ResponseShortenerDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShortenerMapper {

    ShortenerDocument mapToDocument(RequestShortenerDTO dto);
    ResponseShortenerDTO mapToResponse(ShortenerDocument document);
    List<ResponseShortenerDTO> mapToResponses(List<ShortenerDocument> documents);
}
