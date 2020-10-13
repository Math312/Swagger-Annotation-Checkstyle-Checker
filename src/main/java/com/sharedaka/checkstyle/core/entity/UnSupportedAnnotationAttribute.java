package com.sharedaka.checkstyle.core.entity;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UnSupportedAnnotationAttribute extends AbstractAnnotationAttribute {

    private DetailAST value;

}
