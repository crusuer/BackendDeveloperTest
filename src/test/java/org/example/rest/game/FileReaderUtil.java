package org.example.rest.game;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class FileReaderUtil {
    public static byte[] readAllBytes(URL url) {
        try {
            return Files.readAllBytes(Paths.get(url.toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
