package com.lsdconsulting.stub.plugin.handler;

import org.junit.jupiter.api.Test;

class RestControllerAnnotationHandlerShould {
//
//    private final Elements elementUtils = mock(Elements.class);
//    private final Element element = mock(Element.class);
//    private final Name className = mock(Name.class);
//    private final Name packageName = mock(Name.class);
//    private final PackageElement packageElement = mock(PackageElement.class);
//
//    private final RestControllerAnnotationHandler underTest = new RestControllerAnnotationHandler(elementUtils);
//
//    private final String rootPath = RandomStringUtils.randomAlphabetic(10);
//    private final String annotatedClassName = RandomStringUtils.randomAlphabetic(10);
//    private final String packageNameString = RandomStringUtils.randomAlphabetic(10);
//    private final String stubPackageName = RandomStringUtils.randomAlphabetic(10);
//    private final String stubClassName = RandomStringUtils.randomAlphabetic(10);
//
//    private final Model model = new Model();

    @Test
    void restControllerAnnotationHandler() {
//        model.setStubPackageName(stubPackageName);
//        model.setStubClassName(stubClassName);
//
//        given(element.getAnnotation(RestController.class)).willReturn(new RestController() {
//            @Override
//            public Class<? extends Annotation> annotationType() {
//                return null;
//            }
//
//            @Override
//            public String value() {
//                return rootPath;
//            }
//        });
//        given(element.getSimpleName()).willReturn(className);
//        given(className.toString()).willReturn(annotatedClassName);
//        given(elementUtils.getPackageOf(element)).willReturn(packageElement);
//        given(packageElement.getQualifiedName()).willReturn(packageName);
//        given(packageName.toString()).willReturn(packageNameString);
//
//
//        underTest.restControllerAnnotationHandler(model, element);
//
//        assertThat(model.getRootUrl(), is(rootPath));
//        assertThat(model.getStubClassName(), is(annotatedClassName + "Stub"));
//        assertThat(model.getStubPackageName(), is(stubPackageName));
//        assertThat(model.getStubFullyQualifiedName(), is(stubPackageName+"."+stubClassName));
//        assertThat(model.getStubBaseFullyQualifiedName(), is(stubPackageName+".StubBase"));
    }
}
//        model.setRootUrl(element.getAnnotation(RestController.class).value());
//        model.setStubClassName(element.getSimpleName().toString()+"Stub");
//        model.setStubPackageName(elementUtils.getPackageOf(element).getQualifiedName().toString());
//        model.setStubFullyQualifiedName(model.getStubPackageName()+"."+model.getStubClassName());
//        model.setStubBaseFullyQualifiedName(model.getStubPackageName()+".StubBase");
