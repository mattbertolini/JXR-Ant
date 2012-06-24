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
