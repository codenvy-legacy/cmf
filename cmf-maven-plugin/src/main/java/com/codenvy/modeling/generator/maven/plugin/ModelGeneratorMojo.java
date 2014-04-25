package com.codenvy.modeling.generator.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.HashMap;
import java.util.Map;

import static com.codenvy.modeling.generator.GenerationController.Param;
import static com.codenvy.modeling.generator.GenerationController.Param.EDITOR_NAME;
import static com.codenvy.modeling.generator.GenerationController.Param.MAIN_PACKAGE;
import static com.codenvy.modeling.generator.GenerationController.Param.SOURCE_PATH;
import static com.codenvy.modeling.generator.GenerationController.Param.TARGET_PATH;

/**
 * @author Vladyslav Zhukovskii
 * @author Andrey Plotnikov
 */
@Mojo(name = "java", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class ModelGeneratorMojo extends AbstractMojo {

    @Parameter(property = "descriptor", required = false)
    private String descriptor;
    @Parameter(property = "sourcePath", required = false)
    private String sourcePath;
    @Parameter(property = "targetPath", required = false)
    private String targetPath;
    @Parameter(property = "mainPackage", required = false)
    private String mainPackage;
    @Parameter(property = "editorName", required = false)
    private String editorName;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Map<Param, String> params = new HashMap<>();

        if (sourcePath != null && !sourcePath.isEmpty()) {
            params.put(SOURCE_PATH, sourcePath);
        }

        if (targetPath != null && !targetPath.isEmpty()) {
            params.put(TARGET_PATH, targetPath);
        }

        if (mainPackage != null && !mainPackage.isEmpty()) {
            params.put(MAIN_PACKAGE, mainPackage);
        }

        if (editorName != null && !editorName.isEmpty()) {
            params.put(EDITOR_NAME, editorName);
        }

        getLog().info(params.toString());
    }
}
