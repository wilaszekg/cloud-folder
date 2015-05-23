package pl.cloudfolder.domain.storage;

/**
 * Created by Adiki on 2015-05-23.
 */
public class StorageException extends Exception {
    public StorageException(Exception e) {
        super(e);
    }
}
