package com.sharedaka.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.sharedaka.checkstyle.core.entity.AbstractAnnotationAttribute;
import com.sharedaka.checkstyle.core.entity.AnnotationDst;
import com.sharedaka.checkstyle.core.entity.ArrayAnnotationAttribute;
import com.sharedaka.checkstyle.core.entity.NestedAnnotationAttribute;

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
                if (annotations.containsKey("RequestMapping") || annotations.containsKey("GetMapping") || annotations.containsKey("DeleteMapping") || annotations.containsKey("PostMapping") || annotations.containsKey("PutMapping")) {
                    needCheck = true;
                }
                if (needCheck) {
                    if (!annotations.containsKey("ApiOperation")) {
                        log(ast.getLineNo(), "接口应该使用ApiOperation注解提供接口注释");
                    } else {
                        AbstractAnnotationAttribute attribute = annotations.get("ApiOperation").getAttributes().get("value");
                        if (attribute.getValue().equals("")) {
                            log(attribute.getDetailAST().getLineNo(), "ApiOperation注解提供接口注释不能为空字符串");
                        }
                    }
                    if (!annotations.containsKey("ApiImplicitParams")) {
                        log(ast.getLineNo(), "接口应该使用ApiImplicitParams注解提供请求参数注释");
                    } else {
                        AnnotationDst apiImplicitParams = annotations.get("ApiImplicitParams");
                        if (!apiImplicitParams.getAttributes().containsKey("value")) {
                            log(apiImplicitParams.getDetailAST().getLineNo(), "接口应该使用ApiImplicitParam注解提供请求参数注释");
                        } else {
                            ArrayAnnotationAttribute arrayAnnotationAttribute = (ArrayAnnotationAttribute) apiImplicitParams.getAttributes().get("value");
                            for (AbstractAnnotationAttribute annotationAttribute : arrayAnnotationAttribute.getAttributes()) {
                                if (annotationAttribute instanceof NestedAnnotationAttribute) {
                                    AnnotationDst apiImplicitParam = (AnnotationDst) annotationAttribute.getValue();
                                    if (!apiImplicitParam.getAttributes().containsKey("name")) {
                                        log(apiImplicitParam.getDetailAST().getLineNo(), "接口应该使用ApiImplicitParam注解应提供name属性标示参数名称");
                                    }
                                    if (!apiImplicitParam.getAttributes().containsKey("required")) {
                                        log(apiImplicitParam.getDetailAST().getLineNo(), "接口应该使用ApiImplicitParam注解应提供required属性标示参数是否是必须的");
                                    }
                                    if (!apiImplicitParam.getAttributes().containsKey("dataType")) {
                                        log(apiImplicitParam.getDetailAST().getLineNo(), "接口应该使用ApiImplicitParam注解应提供dataType属性标示参数的数据类型");
                                    }
                                    if (!apiImplicitParam.getAttributes().containsKey("dataTypeClass")) {
                                        log(apiImplicitParam.getDetailAST().getLineNo(), "接口应该使用ApiImplicitParam注解应提供dataTypeClass属性标示参数对应的Class对象");
                                    }
                                    if (!apiImplicitParam.getAttributes().containsKey("paramType")) {
                                        log(apiImplicitParam.getDetailAST().getLineNo(), "接口应该使用ApiImplicitParam注解应提供paramType属性标示参数类型（query or body）");
                                    }
                                    if (!apiImplicitParam.getAttributes().containsKey("value")) {
                                        log(apiImplicitParam.getDetailAST().getLineNo(), "接口应该使用ApiImplicitParam注解应提供value属性提供参数备注");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}
