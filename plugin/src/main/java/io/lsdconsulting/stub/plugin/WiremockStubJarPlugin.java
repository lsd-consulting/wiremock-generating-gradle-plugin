package io.lsdconsulting.stub.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.compile.JavaCompile;

import java.io.File;

public class WiremockStubJarPlugin implements Plugin<Project> {

    private static final String GENERATED_STUB_CLASSES_DIRECTORY = "generated-stub-classes";
    private static final String GENERATED_STUB_SOURCES_DIRECTORY = "generated-stub-sources";

    public void apply(Project project) {
        project.getPluginManager().apply(JavaLibraryPlugin.class);

        createCompileStubsTask(project);
        createStubsJarTask(project);
    }

    private void createCompileStubsTask(final Project project) {
        JavaCompile compileJava = (JavaCompile) project.getTasksByName("compileJava", false).iterator().next();
        TaskProvider<JavaCompile> compileJavaStubs = project.getTasks().register("compileStubs", JavaCompile.class,
                compileStubs -> {
                    File stubsClassesDir = new File(project.getBuildDir() + "/" + GENERATED_STUB_CLASSES_DIRECTORY);
                    project.getLogger().lifecycle("classes dir:" + (project.getBuildDir() + "/" + GENERATED_STUB_CLASSES_DIRECTORY));
                    stubsClassesDir.mkdirs();
                    compileStubs.setClasspath(compileJava.getClasspath());
                    compileStubs.source(project.getLayout().getBuildDirectory().dir(GENERATED_STUB_SOURCES_DIRECTORY));
                    compileStubs.getDestinationDirectory().set(stubsClassesDir);
                });
        compileJava.finalizedBy(compileJavaStubs);
    }

    private void createStubsJarTask(final Project project) {
        JavaCompile compileJavaStubs = (JavaCompile) project.getTasksByName("compileStubs", false).iterator().next();
        TaskProvider<Jar> stubsJar = project.getTasks().register("stubsJar", Jar.class,
                jar -> {
                    jar.setDescription("Java Wiremock stubs JAR");
                    jar.setGroup("Verification");
                    jar.getArchiveBaseName().convention(project.provider(project::getName));
                    jar.getArchiveClassifier().convention("wiremock-stubs");
                    jar.from(compileJavaStubs.getDestinationDirectory());
                    jar.dependsOn(compileJavaStubs);
                });
        compileJavaStubs.finalizedBy(stubsJar);

        project.artifacts(artifactHandler -> artifactHandler.add("archives", stubsJar));
    }
}
