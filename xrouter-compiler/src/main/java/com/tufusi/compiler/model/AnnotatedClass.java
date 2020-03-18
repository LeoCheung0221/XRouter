package com.tufusi.compiler.model;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.tufusi.annotation.Subscribe;
import com.tufusi.compiler.TypeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;

/**
 * Created by 鼠夏目 on 2020/3/13.
 *
 * @See
 * @Description 注解类
 */
public class AnnotatedClass {

    public Elements mElementUtils;
    private Map<String, String> mThreadsByMethodName = new HashMap<>();

    public AnnotatedClass(List<Element> subscribedMethodElements, Elements elementUtils) {
        this.mElementUtils = elementUtils;
        for (Element methodElement : subscribedMethodElements) {
            Subscribe subscribe = methodElement.getAnnotation(Subscribe.class);
            mThreadsByMethodName.put(mElementUtils.getPackageOf(methodElement).getQualifiedName()
                    + "." + methodElement.getEnclosingElement().getSimpleName()
                    + "." + methodElement.getSimpleName(), subscribe.runThread().name());
        }
    }

    /**
     * 生成 产生器
     *
     * @return java文件
     */
    public JavaFile generateFinder() {
        TypeName stringMap = ParameterizedTypeName.get(TypeUtil.MAPCLS, TypeUtil.STRINGCLS, TypeUtil.STRINGCLS);

        CodeBlock.Builder staticBlock = CodeBlock.builder()
                .addStatement("methodThreadMode = new $T<>()", TypeUtil.HASHMAPCLS);

        for (Map.Entry entry : mThreadsByMethodName.entrySet()) {
            staticBlock.addStatement("methodThreadMode.put($S,$S)", entry.getKey(), entry.getValue());
        }

        MethodSpec.Builder getMethodThread = MethodSpec.methodBuilder("getMethodThread")
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeUtil.STRINGCLS)
                .addStatement("return methodThreadMode.get(methodName)")
                .addParameter(String.class, "methodName");

        // 生成整个类
        TypeSpec finderClass = TypeSpec.classBuilder("MethodThreadFinder")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(TypeUtil.METHODTHREADCLS)
                .addField(stringMap, "methodThreadMode", Modifier.STATIC, Modifier.PRIVATE)
                .addStaticBlock(staticBlock.build())
                .addMethod(getMethodThread.build())
                .build();

        String packageName = "com.tufusi.xrouter";

        return JavaFile.builder(packageName, finderClass).build();
    }
}
