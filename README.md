[![semantic-release](https://img.shields.io/badge/semantic-release-e10079.svg?logo=semantic-release)](https://github.com/semantic-release/semantic-release)

# wiremock-generating-gradle-plugin
![GitHub](https://img.shields.io/github/license/lsd-consulting/wiremock-generating-gradle-plugin)
![Codecov](https://img.shields.io/codecov/c/github/lsd-consulting/wiremock-generating-gradle-plugin)

[![CI](https://github.com/lsd-consulting/wiremock-generating-gradle-plugin/actions/workflows/ci.yml/badge.svg)](https://github.com/lsd-consulting/wiremock-generating-gradle-plugin/actions/workflows/ci.yml)
[![Nightly Build](https://github.com/lsd-consulting/wiremock-generating-gradle-plugin/actions/workflows/nightly.yml/badge.svg)](https://github.com/lsd-consulting/wiremock-generating-gradle-plugin/actions/workflows/nightly.yml)
[![GitHub release](https://img.shields.io/github/release/lsd-consulting/wiremock-generating-gradle-plugin)](https://github.com/lsd-consulting/wiremock-generating-gradle-plugin/releases)
![Maven Central](https://img.shields.io/maven-central/v/io.github.lsd-consulting/wiremock-generating-gradle-plugin)

## About
This is a Gradle plugin for generating Wiremock stubs from Spring @Controller & @RestController annotated classes 

TODO:
- fix Jacoco/Codecov

This Gradle plugin implements the following functionality:

```groovy
task compileStubs(type: JavaCompile) {
    JavaCompile compileJava = project.getTasksByName("compileJava", true).toArray()[0]
    classpath = compileJava.classpath
    source = project.getLayout().getBuildDirectory().dir("generated-stub-sources")
    def stubsClassesDir = file("${project.getBuildDir()}/generated-stub-classes")
    destinationDir(stubsClassesDir)
    compileJava.finalizedBy(compileStubs)
}

task stubsJar(type: Jar) {
    JavaCompile compileJavaStubs = project.getTasksByName("compileStubs", true).toArray()[0]
    setDescription('Java Wiremock stubs JAR')
    setGroup("Verification")
    archiveBaseName.convention(project.provider(project::getName))
    archiveClassifier.convention("wiremock-stubs")
    from(compileJavaStubs.getDestinationDirectory())
    dependsOn(compileJavaStubs)
    compileJavaStubs.finalizedBy(stubsJar)
    project.artifacts(artifactHandler -> artifactHandler.add("archives", stubsJar))
}
```