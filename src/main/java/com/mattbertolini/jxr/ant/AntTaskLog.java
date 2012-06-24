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

import org.apache.maven.jxr.log.Log;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.LogLevel;

/**
 * Implementation of {@link Log} that logs messages to the given Ant task logger.
 *
 * @author Matt Bertolini
 */
public final class AntTaskLog implements Log {
    private final Task task;

    /**
     * Create a new AntTaskLogger.
     *
     * @param task Reference to the Ant task that log messages should be logged to.
     */
    public AntTaskLog(Task task) {
        this.task = task;
    }

    @Override
    public void info(String message) {
        this.task.log(message, LogLevel.INFO.getLevel());
    }

    @Override
    public void debug(String message) {
        this.task.log(message, LogLevel.DEBUG.getLevel());
    }

    @Override
    public void warn(String message) {
        this.task.log(message, LogLevel.WARN.getLevel());
    }

    @Override
    public void error(String message) {
        this.task.log(message, LogLevel.ERR.getLevel());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AntTaskLog)) return false;

        AntTaskLog that = (AntTaskLog) o;

        if (!task.equals(that.task)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return task.hashCode();
    }
}
