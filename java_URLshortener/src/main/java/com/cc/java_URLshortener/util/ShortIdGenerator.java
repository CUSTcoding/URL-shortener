package com.cc.java_URLshortener.util;

import java.util.UUID;

public class ShortIdGenerator {
    public static String generate(){
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
