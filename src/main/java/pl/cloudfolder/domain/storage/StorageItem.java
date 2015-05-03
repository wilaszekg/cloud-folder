package pl.cloudfolder.domain.storage;

/**
 * Created by Adiki on 2015-05-01.
 */
public class StorageItem {
    public final String name;
    public final String id;

    public StorageItem(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public boolean isDirectory() {
        return this instanceof Directory;
    }
}
