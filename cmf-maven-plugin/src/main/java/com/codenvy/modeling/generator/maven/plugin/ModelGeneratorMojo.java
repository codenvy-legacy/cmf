package com.codenvy.modeling.generator.maven.plugin;

import com.codenvy.modeling.generator.Generator;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.HashMap;
import java.util.Map;

/**
* @author Vladyslav Zhukovskii
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
        Map<Generator.Param, String> params = new HashMap<>();

        if (sourcePath != null && !sourcePath.isEmpty()) {
            params.put(Generator.Param.SOURCE_PATH, sourcePath);
        }

        if (targetPath != null && !targetPath.isEmpty()) {
            params.put(Generator.Param.TARGET_PATH, targetPath);
        }

        if (mainPackage != null && !mainPackage.isEmpty()) {
            params.put(Generator.Param.MAIN_PACKAGE, mainPackage);
        }

        if (editorName != null && !editorName.isEmpty()) {
            params.put(Generator.Param.EDITOR_NAME, editorName);
        }

        getLog().info(params.toString());
    }
}
