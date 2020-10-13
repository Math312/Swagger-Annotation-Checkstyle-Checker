package com.sharedaka.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.sharedaka.checkstyle.core.config.SingletonHolder;
import com.sharedaka.checkstyle.core.entity.AnnotationDst;
import com.sharedaka.checkstyle.core.parser.AnnotationParser;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAnnotationParentCheck extends AbstractCheck {

    protected final AnnotationParser annotationParser;

    public AbstractAnnotationParentCheck() {
        annotationParser = SingletonHolder.getAnnotationParser();
    }

    protected Map<String, AnnotationDst> getAnnotationFromDefNode(DetailAST defNode) {
        DetailAST modifiers = defNode.getFirstChild();
        while (modifiers != null) {
            if (modifiers.getType() == TokenTypes.MODIFIERS) {
                break;
            } else {
                modifiers = modifiers.getNextSibling();
            }
        }
        if (modifiers != null) {
            Map<String, AnnotationDst> annotations = new HashMap<>();
            DetailAST annotation = modifiers.getFirstChild();
            while (annotation != null) {
                if (this.annotationParser.support(annotation)) {
                    AnnotationDst annotationDst = this.annotationParser.parse(annotation);
                    if (annotationDst != null) {
                        annotations.put(annotationDst.getName(), annotationDst);
                    }
                }
                annotation = annotation.getNextSibling();
            }
            return annotations;
        }
        return null;
    }
}
