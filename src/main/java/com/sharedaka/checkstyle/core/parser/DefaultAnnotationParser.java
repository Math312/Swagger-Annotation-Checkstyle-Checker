package com.sharedaka.checkstyle.core.parser;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.sharedaka.checkstyle.core.entity.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DefaultAnnotationParser implements AnnotationParser {

    @Override
    public boolean support(DetailAST detailAST) {
        return detailAST.getType() == TokenTypes.ANNOTATION;
    }

    @Override
    public AnnotationDst parse(DetailAST detailAST) {
        AnnotationDst annotationDst = new AnnotationDst();
        annotationDst.setDetailAST(detailAST);
        Map<String, AbstractAnnotationAttribute> attributes = new HashMap<>();
        DetailAST childNode = detailAST.getFirstChild();

        while (childNode != null) {
            AbstractAnnotationAttribute attribute = null;
            String DEFAULT_NAME = "value";
            switch (childNode.getType()) {
                case TokenTypes.IDENT:
                    annotationDst.setName(childNode.getText());
                    break;
                case TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR:
                    attribute = parseAnnotationMemberValuePair(childNode);
                    break;
                case TokenTypes.EXPR:
                    attribute = parseExpr(childNode);
                    attribute.setName(DEFAULT_NAME);
                    break;
                case TokenTypes.ANNOTATION_ARRAY_INIT:
                    attribute = parseAnnotationArrayInit(childNode);
                    attribute.setName(DEFAULT_NAME);
                    break;
            }
            childNode = childNode.getNextSibling();
            if (attribute != null) {
                attributes.put(attribute.getName(), attribute);
            }
        }
        annotationDst.setAttributes(attributes);
        return annotationDst;
    }

    private AbstractAnnotationAttribute parseAnnotationMemberValuePair(DetailAST detailAST) {
        DetailAST childNode = detailAST.getFirstChild();
        AbstractAnnotationAttribute annotationAttribute = null;
        String name = null;
        while (childNode != null) {
            switch (childNode.getType()) {
                case TokenTypes.IDENT:
                    name = childNode.getText();
                    break;
                case TokenTypes.EXPR:
                    annotationAttribute = parseExpr(childNode);
                    break;

                case TokenTypes.ANNOTATION_ARRAY_INIT:
                    annotationAttribute = parseAnnotationArrayInit(childNode);

            }
            childNode = childNode.getNextSibling();
        }
        if (annotationAttribute != null) {
            annotationAttribute.setName(name);
            annotationAttribute.setDetailAST(detailAST);
        }
        return annotationAttribute;
    }

    private AbstractAnnotationAttribute parseExpr(DetailAST detailAST) {
        AbstractAnnotationAttribute result;
        if (detailAST.getChildCount() > 1) {
            UnSupportedAnnotationAttribute resultAttribute = new UnSupportedAnnotationAttribute();
            resultAttribute.setDetailAST(detailAST);
            resultAttribute.setValue(detailAST);
            result = resultAttribute;
        } else {
            DetailAST childNode = detailAST.getFirstChild();
            switch (childNode.getType()) {
                case TokenTypes.STRING_LITERAL:
                    StringAnnotationAttribute stringAttribute = new StringAnnotationAttribute();
                    stringAttribute.setValue(childNode.getText());
                    result = stringAttribute;
                    break;
                case TokenTypes.NUM_INT:
                    IntegerAnnotationAttribute integerAttribute = new IntegerAnnotationAttribute();
                    integerAttribute.setValue(Integer.parseInt(childNode.getText()));
                    result = integerAttribute;
                    break;
                case TokenTypes.NUM_FLOAT:
                    FloatAnnotationAttribute floatAttribute = new FloatAnnotationAttribute();
                    floatAttribute.setValue(Double.parseDouble(childNode.getText()));
                    result = floatAttribute;
                    break;
                case TokenTypes.LITERAL_BOOLEAN:
                    BooleanAnnotationAttribute booleanAttribute = new BooleanAnnotationAttribute();
                    booleanAttribute.setValue(Boolean.parseBoolean(childNode.getText()));
                    result = booleanAttribute;
                    break;
                default:
                    UnSupportedAnnotationAttribute unSupportedAttribute = new UnSupportedAnnotationAttribute();
                    unSupportedAttribute.setValue(childNode);
                    result = unSupportedAttribute;
            }
            result.setDetailAST(childNode);
        }
        return result;
    }

    private ArrayAnnotationAttribute parseAnnotationArrayInit(DetailAST detailAST) {
        ArrayAnnotationAttribute result = new ArrayAnnotationAttribute();
        LinkedList<AbstractAnnotationAttribute> attributes = new LinkedList<>();
        DetailAST childNode = detailAST.getFirstChild();
        while (childNode != null) {
            if (childNode.getType() == TokenTypes.ANNOTATION) {
                NestedAnnotationAttribute attribute = new NestedAnnotationAttribute();
                attribute.setNestedAnnotation(parse(childNode));
                attribute.setDetailAST(childNode);
                attributes.add(attribute);
            } else if (childNode.getType() == TokenTypes.EXPR) {
                AbstractAnnotationAttribute attribute = parseExpr(childNode);
                attributes.add(attribute);
            }
            childNode = childNode.getNextSibling();
        }
        result.setAttributes(attributes);
        result.setDetailAST(detailAST);
        return result;
    }

}
