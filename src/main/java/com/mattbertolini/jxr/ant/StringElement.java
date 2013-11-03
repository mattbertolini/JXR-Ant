/*
 * Copyright 2012-2013 Matt Bertolini
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

import org.apache.tools.ant.Project;

/**
 * @author Matt Bertolini
 */
public final class StringElement {
    private final Project project;
    private String str;

    public StringElement(Project project) {
        this.project = project;
    }

    public StringElement(Project project, String str) {
        this.project = project;
        this.str = str;
    }

    public void addText(String str) {
        this.str = this.project.replaceProperties(str);
    }

    public String getText() {
        return this.str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringElement)) return false;

        StringElement that = (StringElement) o;

        if (project != null ? !project.equals(that.project) : that.project != null) return false;
        if (str != null ? !str.equals(that.str) : that.str != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = project != null ? project.hashCode() : 0;
        result = 31 * result + (str != null ? str.hashCode() : 0);
        return result;
    }
}
