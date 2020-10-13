package com.sharedaka.checkstyle.core.entity;

import lombok.Data;

import java.util.List;

@Data
public class ArrayAnnotationAttribute extends AbstractAnnotationAttribute {

    public List<AbstractAnnotationAttribute> attributes;

    @Override
    public Object getValue() {
        return attributes;
    }
}
