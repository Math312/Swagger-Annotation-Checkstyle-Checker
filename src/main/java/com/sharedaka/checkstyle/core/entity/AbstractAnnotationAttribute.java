package com.sharedaka.checkstyle.core.entity;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import lombok.Data;

@Data
public abstract class AbstractAnnotationAttribute {

    private String name;

    private DetailAST detailAST;

    public abstract Object getValue();

}
