package com.cc.java_URLshortener.controller;

import com.cc.java_URLshortener.domain.Url;
import com.cc.java_URLshortener.service.UrlService;
import com.cc.java_URLshortener.util.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @Value("${BASE_URL}")
    private String baseUrl;

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl (
            @RequestBody Map<String, String> body
            ){

        String longUrl = body.get("url");

        if (longUrl == null || longUrl.isBlank()){
            return ResponseEntity.badRequest().body(Map.of("error", "URL can't be empty"));
        }

        if (!UrlValidator.isValid(longUrl)) {
            return ResponseEntity.badRequest().body(Map.of("error", "URL inválida"));
        }

        String shortId = urlService.generateShortId();
        Url url = urlService.saveUrl(longUrl, shortId);

        return ResponseEntity.status(201).body(Map.of("shortUrl", baseUrl + "/" + url.getShortId()));

    }

    @GetMapping("/{shortId}")
    public ResponseEntity<?> redirect(@PathVariable String shortId){

        Url url = urlService.getUrl(shortId);

        if(url == null){
            return ResponseEntity.status(404).body(Map.of("error", "short Url not found"));
        }

        urlService.incrementClicks(url);

        return ResponseEntity.status(302).location(URI.create(url.getLongUrl())).build();

    }

    @GetMapping("/{shortId}/status")
    public ResponseEntity<?> getStats(@PathVariable String shortId){

        Url url = urlService.getUrl(shortId);

        if(url == null){
            return ResponseEntity.status(404).body(Map.of("error", "Url not found"));
        }

        return ResponseEntity.ok(Map.of(
                "longUrl", url.getLongUrl(),
                "clickCount", url.getClickCount()
        ));
    }

}
