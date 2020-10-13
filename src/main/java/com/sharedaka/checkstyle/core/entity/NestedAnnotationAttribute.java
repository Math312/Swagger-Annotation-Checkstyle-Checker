package com.sharedaka.checkstyle.core.entity;

import lombok.Data;

@Data
public class NestedAnnotationAttribute extends AbstractAnnotationAttribute {

    private AnnotationDst nestedAnnotation;

    @Override
    public Object getValue() {
        return nestedAnnotation;
    }
}
