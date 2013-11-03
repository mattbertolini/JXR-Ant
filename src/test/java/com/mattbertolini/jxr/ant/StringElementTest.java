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

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.apache.tools.ant.Project;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Matt Bertolini
 */
public class StringElementTest {
    @Test
    public void testEqualsAndHashCode() {
        Project mockProject1 = Mockito.mock(Project.class);
        Project anotherMock = Mockito.mock(Project.class);
        EqualsVerifier.forClass(StringElement.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .withPrefabValues(Project.class, mockProject1, anotherMock)
                .verify();
    }
}
