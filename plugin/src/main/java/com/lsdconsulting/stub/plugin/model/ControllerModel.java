package com.lsdconsulting.stub.plugin.model;

import lombok.Data;

@Data
public class ControllerModel {
    String packageName;

    // Stub base
    String stubBaseFullyQualifiedName;
    String stubBaseClassName;

    // Stub
    String stubFullyQualifiedName;
    String stubClassName;

    String methodName;
    String responseType;
    String rootResource;
    String subResource;
}
