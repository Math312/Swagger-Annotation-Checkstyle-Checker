package com.sharedaka.checkstyle.core.parser;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.sharedaka.checkstyle.core.entity.AnnotationDst;

public interface AnnotationParser {

    boolean support(DetailAST detailAST);

    AnnotationDst parse(DetailAST detailAST);
}
