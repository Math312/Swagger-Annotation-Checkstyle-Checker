package com.sharedaka.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.sharedaka.checkstyle.core.entity.AnnotationDst;

import java.util.Map;


public class SwaggerClassAnnotationCheck extends AbstractAnnotationParentCheck {

    @Override
    public int[] getDefaultTokens() {
        return new int[]{
                TokenTypes.CLASS_DEF
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[]{
                TokenTypes.CLASS_DEF
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[]{
                TokenTypes.CLASS_DEF
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.CLASS_DEF) {
            Map<String, AnnotationDst> annotations = getAnnotationFromDefNode(ast);
            if (annotations != null) {
                if (annotations.containsKey("GetController") || annotations.containsKey("DeleteController") || annotations.containsKey("PostController") || annotations.containsKey("PutController")) {
                    log(ast.getLineNo(), "不允许使用GetController、PostController、DeleteController、PutController注解修饰");
                }
                if (annotations.containsKey("RestController") || annotations.containsKey("Controller")) {
                    if (!annotations.containsKey("Api")) {
                        log(ast.getLineNo(), "Controller必须使用@Api注解添加注释");
                    } else {
                        AnnotationDst detailAST = annotations.get("Api");
                        if (!detailAST.getAttributes().containsKey("value")) {
                            log(ast.getLineNo(), "Controller必须使用@Api注解添加注释，并且value属性为必填");
                        } else {
                            if (detailAST.getAttributes().get("value").getValue().equals("")) {
                                log(ast.getLineNo(), "Controller必须使用@Api注解添加注释，并且value属性不能为空字符串");
                            }
                        }
                    }
                }
            }
        }
    }
}
