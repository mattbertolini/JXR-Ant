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

import org.apache.tools.ant.Task;

/**
 * @author Matt Bertolini
 */
public class StringElement extends Task {
    private String str;

    public StringElement() {
        // Default constructor
    }

    public StringElement(String str) {
        this.str = str;
    }

    public void addText(String str) {
        this.str = this.getProject().replaceProperties(str);
    }

    public String getText() {
        return this.str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringElement)) return false;

        StringElement that = (StringElement) o;

        if (str != null ? !str.equals(that.str) : that.str != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return str != null ? str.hashCode() : 0;
    }
}
