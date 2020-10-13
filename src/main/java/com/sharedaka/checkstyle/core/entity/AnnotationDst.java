package com.sharedaka.checkstyle.core.entity;


import java.util.Map;
import lombok.Data;

@Data
public class AnnotationDst {

    private String name;

    private Map<String, AbstractAnnotationAttribute> attributes;


}
