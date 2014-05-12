package omsu.svion.images;

import java.io.IOException;

/**
 * Created by victor on 12.05.14.
 */
public interface ImageLoader {
    public byte[] loadByName(String name) throws IOException;
}
