package omsu.svion.images.impl;

import omsu.svion.images.ImageLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by victor on 13.05.14.
 */
@Service
public class ImageLoaderImpl implements ImageLoader {
    @Autowired
    private ServletContext servletContext;
    public byte[] loadByName(String name) throws IOException {
        Path path = Paths.get(servletContext.getRealPath("/WEB-INF/images/"+name));
        return  Files.readAllBytes(path);
    }
}
