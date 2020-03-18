package com.tufusi.compiler;

import com.google.auto.service.AutoService;
import com.tufusi.annotation.Subscribe;
import com.tufusi.compiler.model.AnnotatedClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Created by 鼠夏目 on 2020/3/13.
 *
 * @See
 * @Description 订阅处理类
 */
@AutoService(Processor.class)
@SupportedSourceVersion(value = SourceVersion.RELEASE_7)
public class SubscribeProcessor extends AbstractProcessor {

    /**
     * 元素相关的辅助类
     */
    private Elements mElementUtils;

    /**
     * 日志相关的辅助类
     */
    private Messager mMessager;

    /**
     *
     */
    private List<Element> mSubscribedMethodElement = new ArrayList<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mSubscribedMethodElement.clear();

        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Subscribe.class)) {
            if (annotatedElement.getKind() == ElementKind.METHOD) {
                mSubscribedMethodElement.add(annotatedElement);
            }
        }

        AnnotatedClass annotatedClass = new AnnotatedClass(mSubscribedMethodElement, mElementUtils);

        try {
            annotatedClass.generateFinder().writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.getStackTrace();
        }

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Subscribe.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void error(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    private void info(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }
}
