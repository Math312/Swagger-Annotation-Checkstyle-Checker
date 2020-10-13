package com.sharedaka.checkstyle.core.entity;

import lombok.Data;

@Data
public class FloatAnnotationAttribute extends AbstractAnnotationAttribute {

    private double value;

    public Double getValue() {
        return value;
    }
}
