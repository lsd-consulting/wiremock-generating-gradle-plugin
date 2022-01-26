package com.lsdconsulting.stub.plugin.handler;

import com.lsdconsulting.stub.plugin.model.ControllerModel;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;

@AllArgsConstructor
public class RestControllerAnnotationHandler {
    private final Elements elementUtils;

    public void restControllerAnnotationHandler(final ControllerModel controllerModel, final Element element) {
        controllerModel.setRootUrl(element.getAnnotation(RestController.class).value());
        controllerModel.setStubClassName(element.getSimpleName().toString() + "Stub");
        controllerModel.setStubPackageName(elementUtils.getPackageOf(element).getQualifiedName().toString());
        controllerModel.setStubFullyQualifiedName(controllerModel.getStubPackageName() + "." + controllerModel.getStubClassName());
        controllerModel.setStubBaseFullyQualifiedName(controllerModel.getStubPackageName() + ".StubBase");
    }
}