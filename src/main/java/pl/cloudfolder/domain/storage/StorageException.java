package pl.cloudfolder.domain.storage;

/**
 * Created by Adiki on 2015-05-23.
 */
public class StorageException extends RuntimeException {
    public StorageException(Exception e) {
        super(e);
    }
}
