package com.sharedaka.checkstyle.core.entity;

import lombok.Data;

@Data
public class BooleanAnnotationAttribute extends AbstractAnnotationAttribute {

    private boolean value;

    @Override
    public Object getValue() {
        return value;
    }
}
