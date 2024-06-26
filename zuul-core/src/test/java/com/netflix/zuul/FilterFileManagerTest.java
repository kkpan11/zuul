/*
 * Copyright 2019 Netflix, Inc.
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */
package com.netflix.zuul;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests for {@link FilterFileManager}.
 */
@ExtendWith(MockitoExtension.class)
class FilterFileManagerTest {
    @Mock
    private File nonGroovyFile;

    @Mock
    private File groovyFile;

    @Mock
    private File directory;

    @Mock
    private FilterLoader filterLoader;

    @BeforeEach
    void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFileManagerInit() throws Exception {
        FilterFileManager.FilterFileManagerConfig config = new FilterFileManager.FilterFileManagerConfig(
                new String[] {"test", "test1"}, new String[] {"com.netflix.blah.SomeFilter"}, 1, (dir, name) -> false);
        FilterFileManager manager = new FilterFileManager(config, filterLoader);

        manager = spy(manager);
        doNothing().when(manager).manageFiles();

        manager.init();
        verify(manager, atLeast(1)).manageFiles();
        verify(manager, times(1)).startPoller();
        assertNotNull(manager.poller);
    }
}
