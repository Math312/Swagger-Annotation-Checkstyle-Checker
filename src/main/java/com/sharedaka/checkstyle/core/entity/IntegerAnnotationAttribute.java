package com.sharedaka.checkstyle.core.entity;

import lombok.Data;

@Data
public class IntegerAnnotationAttribute extends AbstractAnnotationAttribute {

    private int value;

    public Integer getValue() {
        return value;
    }
}
