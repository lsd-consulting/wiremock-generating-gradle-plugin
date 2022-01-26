package com.lsdconsulting.stub.plugin.model;

import lombok.Data;

@Data
public class ControllerModel {
    String stubFullyQualifiedName;
    String stubBaseFullyQualifiedName;
    String stubPackageName;
    String stubClassName;
    String methodName;
    String responseType;
    String rootUrl;
    String url;
}
