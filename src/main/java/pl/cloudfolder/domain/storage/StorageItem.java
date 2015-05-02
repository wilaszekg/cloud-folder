package pl.cloudfolder.domain.storage;

/**
 * Created by Adiki on 2015-05-01.
 */
public class StorageItem {
    public final String name;
    public final String path;

    public StorageItem(String name, String path) {
        this.name = name;
        this.path = path;
    }
}
