package com.sharedaka.checkstyle.core.entity;

import lombok.Data;

@Data
public abstract class AbstractAnnotationAttribute {

    private String name;

    public abstract Object getValue();

}
