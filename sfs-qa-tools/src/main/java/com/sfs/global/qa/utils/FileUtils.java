/*
 * Copyright (c) 2013-2022 CodeMinter, the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sfs.global.qa.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

/**
 * Class for all utility methods related to file system management and operations.
 *
 * @author Monalis Behera
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileUtils extends org.apache.commons.io.FileUtils {

    /**
     * Gets path from the specified pathname.
     *
     * @param pathname path name
     * @return path
     */
    public static Path getPath(final String pathname) {
        Validate.notNullOrBlank(pathname, "Specified path name is NULL or empty/blank!");
        return Paths.get(pathname);
    }

    /**
     * Gets file from the specified path.
     *
     * @param pathname path name
     * @return file
     */
    public static File getFile(final String pathname) {
        return getPath(pathname).toFile();
    }

    /**
     * Gets files list from the specified directory and subdirectories recursively.
     *
     * @param dirPath path to the directory
     * @return list of files in the directory
     */
    public static Collection<File> getFiles(final String dirPath) {
        return listFiles(getFile(dirPath), null, true);
    }

    /**
     * Checks if the specified file exists, and it's not a directory.
     *
     * @param file File to check
     * @return true if the file exists, false otherwise
     */
    public static boolean isFileExists(final File file) {
        return file.exists() && !file.isDirectory();
    }

    /**
     * Checks if the specified file exists, and it's not a directory.
     *
     * @param filepath path to the file to check
     * @return true if the file exists, false otherwise
     */
    public static boolean isFileExists(final String filepath) {
        return isFileExists(getFile(filepath));
    }

    /**
     * Checks if the specified directory exist.
     *
     * @param dirpath path to the directory to check
     * @return true if the directory exists, false otherwise
     */
    public static boolean isDirectoryExists(final String dirpath) {
        final File file = getFile(dirpath);
        return file.exists() && file.isDirectory();
    }

    /**
     * Creates parent directories for the specified file.
     *
     * @param filepath path to the file
     * @return FileUtils instance
     */
    public static FileUtils createParentDirectories(final String filepath) {
        try {
            createParentDirectories(getFile(filepath));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return new FileUtils();
    }

    /**
     * Creates file if it doesn't exist.
     *
     * @param file file
     * @return the file instance
     */
    public static File createFile(final File file) {
        return createFile(file, true);
    }

    /**
     * Creates file if it doesn't exist.
     *
     * @param file file
     * @param logEvent true if event should be logged, false otherwise
     * @return the file instance
     */
    public static File createFile(final File file, final boolean logEvent) {
        Validate.notNull(file, "Specified file is NULL object!");
        if (!isFileExists(file)) {
            try {
                if (file.createNewFile() && logEvent) {
                    log.info("New file created: [" + file.getPath() + "].");
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        } else {
            if (logEvent) {
                log.warn("File [{}] is NOT created: file already exists!", file.getPath());
            }
        }
        return file;
    }

    /**
     * Creates file if it doesn't exist.
     *
     * @param filepath path to the file
     * @return the file instance
     */
    public static File createFile(final String filepath) {
        return createFile(filepath, true);
    }

    /**
     * Creates file if it doesn't exist.
     *
     * @param filepath path to the file
     * @param logEvent true if event should be logged, false otherwise
     * @return the file instance
     */
    public static File createFile(final String filepath, final boolean logEvent) {
        return createFile(getFile(filepath), logEvent);
    }

    /**
     * Deletes file if it exists.
     *
     * @param filepath path to the file
     */
    public static void deleteFile(final String filepath) {
        final File file = getFile(filepath);
        if (isFileExists(file)) {
            if (file.delete()) {
                log.info("File deleted: [{}].", filepath);
            }
        } else {
            log.warn("File [{}] is NOT deleted: file is NOT found!", filepath);
        }
    }

    /**
     * Creates the directory and all subdirectories recursively.
     *
     * @param dirpath path to the directory
     * @return FileUtils instance
     */
    public static FileUtils createDirectory(final String dirpath) {
        if (!isDirectoryExists(dirpath)) {
            try {
                Files.createDirectories(getPath(dirpath));
                log.info("New directory created: [{}].", dirpath);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        } else {
            log.warn("Directory [{}] is NOT created: directory already exists!", dirpath);
        }
        return new FileUtils();
    }

    /**
     * Deletes the directory and all internal subdirectories and files recursively.
     *
     * @param dirpath path to the directory
     * @return FileUtils instance
     */
    public static FileUtils deleteDirectory(final String dirpath) {
        if (isDirectoryExists(dirpath)) {
            try {
                deleteDirectory(getFile(dirpath));
                log.info("Directory deleted: [{}].", dirpath);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        } else {
            log.warn("Directory [{}] is NOT deleted: directory is NOT found!", dirpath);
        }
        return new FileUtils();
    }

    /**
     * Copies the specified directory (as is) to a directory with the same name at specified destination.
     *
     * @param dirpathFrom path to the directory to copy from
     * @param dirpathTo path to the directory to copy to
     */
    public static void copyDirectory(final String dirpathFrom, final String dirpathTo) {
        try {
            copyDirectoryToDirectory(getFile(dirpathFrom), getFile(dirpathTo));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Copies the specified directory content (all child directories and files) to the specified destination.
     *
     * @param dirpathFrom path to the directory to copy from
     * @param dirpathTo path to the directory to copy to
     */
    public static void copyDirectoryContent(final String dirpathFrom, final String dirpathTo) {
        try {
            copyDirectory(getFile(dirpathFrom), getFile(dirpathTo));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Writes the specified content to the specified file, file must exist before writing.
     *
     * @param filepath path to file
     * @param content content to write as bytes array
     */
    public static void writeToFile(final String filepath, final byte[] content) {
        try (OutputStream fos = new FileOutputStream(validateFileExists(filepath))) {
            fos.write(content);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Writes the specified content to the specified file, file must exist before writing.
     *
     * @param filepath path to file
     * @param content content to write as input stream
     */
    public static void writeToFile(final String filepath, final InputStream content) {
        try {
            writeToFile(filepath, IOUtils.toByteArray(content));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Writes the specified content to the specified file, also creates the file if is defined.
     *
     * @param filepath path to file
     * @param content content to write as bytes array
     * @param createFile true if file should be created, false otherwise
     */
    public static void writeToFile(final String filepath, final byte[] content, boolean createFile) {
        if (createFile) {
            createParentDirectories(filepath).createFile(filepath);
        }
        writeToFile(filepath, content);
    }

    /**
     * Writes specified content to the specified file, file should be created or exist before writing.
     *
     * @param file file
     * @param content content to write as bytes array
     */
    public static void writeToFile(final File file, final byte[] content) {
        try (OutputStream fos = new FileOutputStream(file)) {
            fos.write(content);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Writes the specified content to the specified file, file must exist before writing.
     *
     * @param file file
     * @param content content to write as input stream
     */
    public static void writeToFile(final File file, final InputStream content) {
        try {
            writeToFile(file, IOUtils.toByteArray(content));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Reads specified file as bytes array.
     *
     * @param filepath path to file
     * @return The bytes array read from the file
     */
    public static byte[] readAsBytes(final String filepath) {
        try {
            return Files.readAllBytes(Paths.get(validateFileExists(filepath)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return new byte[]{};
    }

    /**
     * Reads the specified file as input stream.
     *
     * @param filepath path to file
     * @return The input stream
     */
    public static InputStream readAsStream(final String filepath) {
        try {
            return new FileInputStream(validateFileExists(filepath));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Reads the specified file as string.
     *
     * @param filepath path to file
     * @return The content read from the file as a string
     */
    public static String readAsString(final String filepath) {
        return new String(readAsBytes(filepath));
    }

    /**
     * Validates that the specified file exists.
     *
     * @param filepath path to file
     * @return The specified file path as a {@link String}.
     */
    public static String validateFileExists(final String filepath) {
        if (!isFileExists(filepath)) {
            log.error("Specified file [{}] does NOT exist or is NOT found in the file system!", filepath);
            throw new IllegalArgumentException("Specified file [" + filepath
                    + "] does NOT exist or is NOT found in the file system!");
        }
        return filepath;
    }
}
