package com.sharedaka.checkstyle.core.entity;


import java.util.Map;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import lombok.Data;

@Data
public class AnnotationDst {

    private String name;

    private DetailAST detailAST;

    private Map<String, AbstractAnnotationAttribute> attributes;


}
