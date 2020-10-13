package com.sharedaka.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.sharedaka.checkstyle.core.entity.AbstractAnnotationAttribute;
import com.sharedaka.checkstyle.core.entity.AnnotationDst;

import java.util.Map;

public class SwaggerMethodAnnotationCheck extends AbstractAnnotationParentCheck {

    @Override
    public int[] getDefaultTokens() {
        return new int[]{
                TokenTypes.METHOD_DEF
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[]{
                TokenTypes.METHOD_DEF
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[]{
                TokenTypes.METHOD_DEF
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.METHOD_DEF) {
            Map<String, AnnotationDst> annotations = getAnnotationFromDefNode(ast);
            if (annotations != null) {
                boolean needCheck = false;
                if (annotations.containsKey("RequestMapping") || annotations.containsKey("GetController") || annotations.containsKey("DeleteController") || annotations.containsKey("PostController") || annotations.containsKey("PutController")) {
                    needCheck = true;
                }
                if (needCheck) {
                    if (!annotations.containsKey("ApiOperation")) {
                        log(ast.getLineNo(), "接口应该使用ApiOperation注解提供接口注释");
                    } else {
                        AbstractAnnotationAttribute attribute = annotations.get("ApiOperation").getAttributes().get("value");
                        if (attribute.getValue().equals("")) {
                            log(ast.getLineNo(), "ApiOperation注解提供接口注释不能为空字符串");
                        }
                    }
                    if (!annotations.containsKey("ApiImplicitParams")) {
                        log(ast.getLineNo(), "接口应该使用ApiImplicitParams注解提供请求参数注释");
                    }
                }
            }
        }
    }


}
