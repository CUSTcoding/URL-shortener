package com.cc.java_URLshortener.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Url {

    @Id
    private String shortId;

    private String longUrl;

    private int clickCount;

}
