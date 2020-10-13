package com.sharedaka.checkstyle.core.config;

import com.sharedaka.checkstyle.core.parser.AnnotationParser;
import com.sharedaka.checkstyle.core.parser.DefaultAnnotationParser;

import java.util.HashMap;
import java.util.Map;

public class SingletonHolder {
    public static Map<String,Object> singletonMap;
    static {
        singletonMap = new HashMap<>();
        singletonMap.put("defaultAnnotationParser",new DefaultAnnotationParser());
    }

    public static AnnotationParser getAnnotationParser() {
        return (AnnotationParser) singletonMap.get("defaultAnnotationParser");
    }
}
