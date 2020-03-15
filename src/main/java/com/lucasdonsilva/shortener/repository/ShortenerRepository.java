package com.lucasdonsilva.shortener.repository;

import com.lucasdonsilva.shortener.document.ShortenerDocument;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShortenerRepository extends MongoRepository<ShortenerDocument, String> {

    List<ShortenerDocument> findTop10ByOrderByAccessDesc();
    @Cacheable("alias")
    Optional<ShortenerDocument> findByAlias(String alias);
    @CachePut("alias")
    ShortenerDocument save(ShortenerDocument document);
}