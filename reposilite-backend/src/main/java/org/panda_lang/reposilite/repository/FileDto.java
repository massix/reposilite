/*
 * Copyright (c) 2020 Dzikoysk
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

package org.panda_lang.reposilite.repository;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.Serializable;

final class FileDto implements Serializable, Comparable<FileDto> {

    static final String FILE = "file";
    static final String DIRECTORY = "directory";

    private final String type;
    private final String name;
    private final long contentLength;

    private FileDto(String type, String name, long contentLength) {
        this.type = type;
        this.name = name;
        this.contentLength = contentLength;
    }

    @Override
    public int compareTo(@NotNull FileDto to) {
        int result = type.compareTo(to.getType());

        if (result == 0) {
            result = name.compareTo(to.getType());
        }

        return result;
    }

    boolean isDirectory() {
        return DIRECTORY.equals(type);
    }

    public long getContentLength() {
        return contentLength;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public static FileDto of(File file) {
        return new FileDto(file.isDirectory() ? DIRECTORY : FILE, file.getName(), file.isDirectory() ? -1 : file.length());
    }

}
