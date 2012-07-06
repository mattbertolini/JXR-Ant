/*
 * Copyright 2012 Matt Bertolini
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
* limitations under the License.
 */

package com.mattbertolini.jxr.ant;

import org.apache.maven.jxr.JXR;
import org.apache.maven.jxr.JxrException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.LogLevel;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.PatternSet;
import org.apache.tools.ant.types.Resource;
import org.apache.tools.ant.types.resources.FileResource;
import org.apache.tools.ant.types.resources.URLResource;
import org.apache.tools.ant.util.ResourceUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Ant task for the Maven JXR plugin.
 *
 * @author Matt Bertolini
 */
public class JxrTask extends Task {
    private static final String DEFAULT_ENCODING = System.getProperty("file.encoding");
    private static final String DEFAULT_TEMPLATE_PATH = "templates";
    private static final String DEFAULT_BOTTOM = "";
    private static final String DEFAULT_DOC_TITLE = "%s Xref Documentation";
    private static final String DEFAULT_WINDOW_TITLE = "%s Xref Documentation";

    private JXR jxr;
    private StringElement bottom;
    private FileResource destDir;
    private StringElement docTitle;
    private PatternSet includesExcludes;
    private String inputEncoding;
    private FileResource javadocDir;
    private String outputEncoding;
    private Path sourcePaths;
    private Resource stylesheet;
    private Resource templateDir;
    private StringElement windowTitle;

    public JxrTask() {
        super();
        // Default constructor required by Ant.
        this.jxr = new JXR();
    }

    @Override
    public void init() throws BuildException {
        this.sourcePaths = new Path(this.getProject());
        this.jxr.setLog(new AntTaskLog(this));
    }

    @Override
    public void execute() throws BuildException {
        this.log("Generating Xref documentation", LogLevel.INFO.getLevel());
        if(this.destDir == null) {
            throw new BuildException("Destination directory not specified.");
        }

        if(this.inputEncoding == null) {
            this.inputEncoding = DEFAULT_ENCODING;
        }
        if(this.outputEncoding == null) {
            this.outputEncoding = DEFAULT_ENCODING;
        }
        if(this.windowTitle == null) {
            this.windowTitle = new StringElement(String.format(DEFAULT_WINDOW_TITLE, this.getProject().getName()));
        }
        if(this.docTitle == null) {
            this.docTitle = new StringElement(String.format(DEFAULT_DOC_TITLE, this.getProject().getName()));
        }
        if(this.bottom == null) {
            this.bottom = new StringElement(DEFAULT_BOTTOM);
        }

        List<String> sourcePathStrings = Arrays.asList(this.sourcePaths.list());
        if(sourcePathStrings.isEmpty()) {
            throw new BuildException("No source paths defined.");
        }

        if(this.includesExcludes != null) {
            String[] includePatterns = this.includesExcludes.getIncludePatterns(this.getProject());
            String[] excludePatterns = this.includesExcludes.getExcludePatterns(this.getProject());
            this.jxr.setIncludes(includePatterns);
            this.jxr.setExcludes(excludePatterns);
        }

        String templatePathStr = DEFAULT_TEMPLATE_PATH;
        if(this.templateDir != null && this.templateDir.isDirectory()) {
            templatePathStr = this.templateDir.toString();
        }
        if(this.javadocDir != null && this.javadocDir.isDirectory()) {
            this.jxr.setJavadocLinkDir(this.javadocDir.toString());
        }

        this.jxr.setOutputEncoding(this.outputEncoding);
        this.jxr.setInputEncoding(this.inputEncoding);
        this.jxr.setDest(this.destDir.toString());

        try {
            this.jxr.xref(sourcePathStrings, templatePathStr, this.windowTitle.getText(), this.docTitle.getText(), this.bottom.getText());

            if(this.stylesheet == null) {
                URL defaultStylesheetUrl = this.getClass().getResource("/com/mattbertolini/jxr/ant/stylesheet.css");
                this.stylesheet = new URLResource(defaultStylesheetUrl);
            }
            FileResource destStylesheet = new FileResource(this.destDir.getFile(), "stylesheet.css");
            ResourceUtils.copyResource(this.stylesheet, destStylesheet);

        } catch (IOException e) {
            throw new BuildException("Exception while running XJR task.", e);
        } catch (JxrException e) {
            throw new BuildException("Exception running XJR task.", e);
        }
    }

    /**
     * Optional.
     * @param bottom The footer text.
     */
    public void addBottom(StringElement bottom) {
        this.log("Setting bottom text", LogLevel.DEBUG.getLevel());
        this.bottom = bottom;
    }

    /**
     * Optional.
     * @param bottom The footer text.
     */
    public void setBottom(String bottom) {
        this.log("Setting bottom text", LogLevel.DEBUG.getLevel());
        this.bottom = new StringElement(bottom);
    }

    /**
     * Required. Sets the destination directory of the generated files.
     *
     * @param destDir The destination directory.
     */
    public void setDestDir(FileResource destDir) {
        if(!destDir.isDirectory()) {
            throw new BuildException("Destination path " + destDir + " is not a directory.");
        }
        this.destDir = destDir;
    }

    /**
     * Optional. Sets the title of the main page of Xref HTML pages. Defaults to "${project.name} Xref Documentation".
     *
     * @param docTitle The doc title
     */
    public void addDocTitle(StringElement docTitle) {
        this.log("Setting doc title", LogLevel.DEBUG.getLevel());
        this.docTitle = docTitle;
    }

    /**
     * Optional. Sets the title of the main page of Xref HTML pages. Defaults to "${project.name} Xref Documentation".
     *
     * @param docTitle The doc title.
     */
    public void setDocTitle(String docTitle) {
        this.log("Setting doc title to: " + docTitle, LogLevel.DEBUG.getLevel());
        this.docTitle = new StringElement(docTitle);
    }

    /**
     * Optional. Sets the file encoding to be used when reading the input files. Defaults to the system file encoding.
     *
     * @param inputEncoding The file encoding.
     */
    public void setInputEncoding(String inputEncoding) {
        this.log("Setting input encoding to " + inputEncoding, LogLevel.DEBUG.getLevel());
        this.inputEncoding = inputEncoding;
    }

    /**
     * Optional. Sets the directory where the generated Javadoc files are located.
     * @param javadocDir The javadoc directory.
     */
    public void setJavadocDir(FileResource javadocDir) {
        this.javadocDir = javadocDir;
    }

    /**
     * Optional. Set the file encoding of the generated files. Defaults to the system file encoding.
     *
     * @param outputEncoding The encoding to set.
     */
    public void setOutputEncoding(String outputEncoding) {
        this.log("Setting output encoding to " + outputEncoding, LogLevel.DEBUG.getLevel());
        this.outputEncoding = outputEncoding;
    }

    public void addSourcePath(Path sourcePathElement) {
        this.sourcePaths.add(sourcePathElement);
    }

    /**
     * Optional.
     *
     * @param sourcePath The source path.
     */
    public void setSourcePath(FileResource sourcePath) {
        this.sourcePaths.add(sourcePath);
    }

    /**
     * Optional. Set a custom stylesheet to be used
     *
     * @param stylesheet The custom stylesheet
     */
    public void setStylesheet(Resource stylesheet) {
        this.stylesheet = stylesheet;
    }

    /**
     * Optional. The directory where the JXR velocity templates are located.
     *
     * @param templateDir The desired template directory.
     */
    public void setTemplateDir(Resource templateDir) {
        this.templateDir = templateDir;
    }

    /**
     * Optional. Sets the window title of the Xref HTML files. Defaults to "${project.name} Xref Documentation".
     *
     * @param windowTitle The window title to set.
     */
    public void addWindowTitle(StringElement windowTitle) {
        this.log("Setting window title", LogLevel.DEBUG.getLevel());
        this.windowTitle = windowTitle;
    }

    /**
     * Optional. Sets the window title of the Xref HTML files. Defaults to "${project.name} Xref Documentation".
     *
     * @param windowTitle The window title to set.
     */
    public void setWindowTitle(String windowTitle) {
        this.log("Setting window title to: " + windowTitle, LogLevel.DEBUG.getLevel());
        this.windowTitle = new StringElement(windowTitle);
    }

    /**
     * Optional. Adds a pattern set that defines the files to include or exclude from the source path.
     *
     * @param includesExcludes A PatternSet with includes and/or excludes
     */
    public void add(PatternSet includesExcludes) {
        this.includesExcludes = includesExcludes;
    }
}
