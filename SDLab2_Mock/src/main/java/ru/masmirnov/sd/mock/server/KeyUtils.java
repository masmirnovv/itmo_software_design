package ru.masmirnov.sd.mock.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public abstract class KeyUtils {

    public static Optional<String> extractKey(String filename) {
        Path path = getFullPath(filename);
        try {
            return Optional.of(Files.readString(path));
        } catch (IOException e) {
            System.err.println("An IO exception occurred while reading key from " + path.toAbsolutePath() + ":");
            e.printStackTrace();
            System.err.println("Returning an empty key.");
            return Optional.empty();
        }
    }



    private static Path getFullPath(String filename) {
        File file = new File("SDLab2_Mock/src/main/resources/security/" + filename);    // running from the IDE case
        if (!file.exists()) {
            file = new File("src/main/resources/security/" + filename);                 // running as a Maven module case
        }
        return Path.of(file.toURI());
    }

}
