package com.lsdconsulting.stub.plugin;

import com.lsdconsulting.stub.plugin.handler.RestControllerAnnotationHandler;
import com.lsdconsulting.stub.plugin.model.ControllerModel;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import lombok.SneakyThrows;
import org.apache.commons.text.CaseUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

public class ControllerProcessor extends AbstractProcessor {
    private RestControllerAnnotationHandler restControllerAnnotationHandler;

    private Messager messager;

    private final PebbleEngine engine = new PebbleEngine.Builder().build();
    private final PebbleTemplate stubTemplate = engine.getTemplate("templates/Stub.java");
    private final PebbleTemplate stubBaseTemplate = engine.getTemplate("templates/StubBase.java");

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        Elements elementUtils = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
        restControllerAnnotationHandler = new RestControllerAnnotationHandler(elementUtils);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Controller.class.getCanonicalName(),
                RestController.class.getCanonicalName(),
                GetMapping.class.getCanonicalName(),
                PostMapping.class.getCanonicalName(),
                ResponseBody.class.getCanonicalName(),
                RequestBody.class.getCanonicalName(),
                RequestParam.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_11;
    }

    @Override
    @SneakyThrows
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {


        final ControllerModel controllerModel = new ControllerModel();

        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            annotatedElements.forEach(element -> {
                if (element.getAnnotation(RestController.class) != null) {
                    messager.printMessage(Diagnostic.Kind.NOTE, "RestController annotation");
                    restControllerAnnotationHandler.restControllerAnnotationHandler(controllerModel, element);
                } else if (element.getAnnotation(GetMapping.class) != null) {
                    messager.printMessage(Diagnostic.Kind.NOTE, "GetMapping annotation");
                    String[] path = element.getAnnotation(GetMapping.class).path();
                    String[] value = element.getAnnotation(GetMapping.class).value();
                    if (path.length > 0) {
                        controllerModel.setUrl(path[0]);
                    } else if (value.length > 0) {
                        controllerModel.setUrl(value[0]);
                    }

                    controllerModel.setMethodName(CaseUtils.toCamelCase(element.getSimpleName().toString(), true));
                    controllerModel.setResponseType(element.asType().toString().replace("()", ""));

                } else {
                    messager.printMessage(Diagnostic.Kind.NOTE, "Unknown annotation");
                }
            });
        }

        if (!annotations.isEmpty()) {
            messager.printMessage(Diagnostic.Kind.NOTE, "Writing files for model:" + controllerModel);
            writeStubFile(controllerModel);
            writeStubBaseFile(controllerModel);
        }

        return true;
    }

    @SneakyThrows
    private void writeStubBaseFile(final ControllerModel controllerModel) {
        try {
            JavaFileObject builderFile2 = processingEnv.getFiler().createSourceFile(controllerModel.getStubBaseFullyQualifiedName());
            try (PrintWriter writer = new PrintWriter(builderFile2.openWriter())) {
                stubBaseTemplate.evaluate(writer, Map.of(
                        "stubPackageName", controllerModel.getStubPackageName()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @SneakyThrows
    private void writeStubFile(final ControllerModel controllerModel) {
        try {
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(controllerModel.getStubFullyQualifiedName());
            try (PrintWriter writer = new PrintWriter(builderFile.openWriter())) {
                stubTemplate.evaluate(writer, Map.of(
                        "stubPackageName", controllerModel.getStubPackageName(),
                        "stubClassName", controllerModel.getStubClassName(),
                        "methodName", controllerModel.getMethodName(),
                        "responseType", controllerModel.getResponseType(),
                        "rootUrl", controllerModel.getRootUrl(),
                        "url", controllerModel.getUrl()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
