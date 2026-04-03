package com.cc.java_URLshortener.repository;

import com.cc.java_URLshortener.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, String> {

}
