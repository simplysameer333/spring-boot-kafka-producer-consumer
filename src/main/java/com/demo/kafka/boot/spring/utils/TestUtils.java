package com.demo.kafka.boot.spring.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Utility functions to be used in unit/integration tests
 */
public class TestUtils {

    /**
     * Create a temporary directory. The directory and any contents will be deleted when the test
     * process terminates.
     */
    public static File tempDirectory() {
        final File file;
        try {
            file = Files.createTempDirectory("confluent").toFile();
        } catch (final IOException ex) {
            throw new RuntimeException("Failed to create a temp dir", ex);
        }
        file.deleteOnExit();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    delete(file);
                } catch (IOException e) {
                    System.out.println("Error deleting " + file.getAbsolutePath());
                }
            }
        });

        return file;
    }

    /**
     * Recursively delete the given file/directory and any subfiles (if any exist)
     *
     * @param file The root file at which to begin deleting
     */
    public static void delete(final File file) throws IOException {
        if (file == null) {
            return;
        }
        Files.walkFileTree(file.toPath(), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFileFailed(Path path, IOException exc) throws IOException {
                // If the root path did not exist, ignore the error; otherwise throw it.
                if (exc instanceof NoSuchFileException && path.toFile().equals(file)) {
                    return FileVisitResult.TERMINATE;
                }
                throw exc;
            }

            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                Files.delete(path);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path path, IOException exc) throws IOException {
                Files.delete(path);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}