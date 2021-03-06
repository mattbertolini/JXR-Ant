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

import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.LogLevel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Matt Bertolini
 */
public class AntTaskLogTest {
    private Task mockTask = Mockito.mock(Task.class);

    @Before
    public void setUp() {
        Mockito.reset(this.mockTask);
    }

    @Test
    public void testInfo() {
        String expectedLogMsg = "Info log message.";
        AntTaskLog antTaskLog = new AntTaskLog(this.mockTask);
        antTaskLog.info(expectedLogMsg);
        Mockito.verify(this.mockTask).log(expectedLogMsg, LogLevel.INFO.getLevel());
    }

    @Test
    public void testDebug() {
        String expectedLogMsg = "Debug log message.";
        AntTaskLog antTaskLog = new AntTaskLog(this.mockTask);
        antTaskLog.debug(expectedLogMsg);
        Mockito.verify(this.mockTask).log(expectedLogMsg, LogLevel.DEBUG.getLevel());
    }

    @Test
    public void testWarn() {
        String expectedLogMsg = "Warning log message.";
        AntTaskLog antTaskLog = new AntTaskLog(this.mockTask);
        antTaskLog.warn(expectedLogMsg);
        Mockito.verify(this.mockTask).log(expectedLogMsg, LogLevel.WARN.getLevel());
    }

    @Test
    public void testError() {
        String expectedLogMsg = "Error log message.";
        AntTaskLog antTaskLog = new AntTaskLog(this.mockTask);
        antTaskLog.error(expectedLogMsg);
        Mockito.verify(this.mockTask).log(expectedLogMsg, LogLevel.ERR.getLevel());
    }

    @Test
    public void testEqualsAndHashCode() {
        Task anotherMock = Mockito.mock(Task.class);
        EqualsVerifier.forClass(AntTaskLog.class).withPrefabValues(Task.class, this.mockTask, anotherMock).verify();
    }
}
