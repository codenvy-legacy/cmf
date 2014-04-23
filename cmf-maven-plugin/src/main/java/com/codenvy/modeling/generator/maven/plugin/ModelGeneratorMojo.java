package com.codenvy.modeling.generator.maven.plugin;

import com.codenvy.modeling.configuration.DescriptorConfig;
import com.codenvy.modeling.configuration.InvalidDescriptorException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/**
* @author Vladyslav Zhukovskii
*/
@Mojo(name = "java", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class ModelGeneratorMojo extends AbstractMojo {

    @Parameter(property = "confDescriptor", required = false)
    private File configurationDescriptor;

    @Parameter(property = "genDir", required = false)
    private File genDir;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
//        if (!(configurationDescriptor.exists() || configurationDescriptor.isFile())) {
//            getLog().error("You should provide a valid path to configuration descriptor file.");
//        }
//        try {
//            DescriptorConfig descriptor = new DescriptorConfig(configurationDescriptor);
//            getLog().info(descriptor.getMetaModelConfigDir());
//            getLog().info(descriptor.getSerializationConfigDir());
//            getLog().info(descriptor.getEditorConfigDir());
//            getLog().info(descriptor.getStyleConfigDir());
//        } catch (InvalidDescriptorException e) {
//            getLog().error(e.getMessage(), e);
//        }
    }
}
