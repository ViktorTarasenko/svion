package omsu.svion.images.impl;

import omsu.svion.images.ImageLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by victor on 13.05.14.
 */
@Service
public class ImageLoaderImpl implements ImageLoader {
    private static final String  BASE_DIR = "/home/dev/images/";
    public byte[] loadByName(String name) throws IOException {
        Path path = Paths.get(BASE_DIR+name);
        return  Files.readAllBytes(path);
    }
}
