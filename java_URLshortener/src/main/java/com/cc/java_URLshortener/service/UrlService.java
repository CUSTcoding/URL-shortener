package com.cc.java_URLshortener.service;

import com.cc.java_URLshortener.domain.Url;
import com.cc.java_URLshortener.repository.UrlRepository;
import com.cc.java_URLshortener.util.ShortIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    @Autowired
    private UrlRepository repository;

    public Url saveUrl(String longUrl, String shortId){

        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setShortId(shortId);
        url.setClickCount(0);

        return repository.save(url);

    }

    public Url getUrl(String shortId){

        return repository.findById(shortId).orElse(null);

    }

    public synchronized void incrementClicks(Url url){
        url.setClickCount(url.getClickCount() + 1);
        repository.save(url);
    }

    public String generateShortId() {
        String shortId;

        do {
            shortId = ShortIdGenerator.generate();
        } while (repository.existsById(shortId));

        return shortId;
    }

}
