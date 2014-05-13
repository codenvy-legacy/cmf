package com.codenvy.modeling.generator.maven.plugin;

import com.codenvy.modeling.generator.GeneratorUtility;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;

/**
 * @author Vladyslav Zhukovskii
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
@Mojo(name = "java", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class ModelGeneratorMojo extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        MavenProject project = (MavenProject)getPluginContext().get("project");
        String baseDir = project.getBasedir().getPath();

        GeneratorUtility.generate(baseDir);
    }
}