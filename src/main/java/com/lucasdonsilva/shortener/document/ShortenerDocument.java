package com.lucasdonsilva.shortener.document;

import lombok.Data;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("shorteners")
@Data
public class ShortenerDocument {

    @Id
    private ObjectId id;
    private String alias;
    private String url;
    private int access;

    public void incrementAccess() {
        this.access++;
    }
}
