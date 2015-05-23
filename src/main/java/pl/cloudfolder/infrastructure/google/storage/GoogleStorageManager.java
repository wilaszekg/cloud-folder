package pl.cloudfolder.infrastructure.google.storage;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;
import pl.cloudfolder.domain.storage.Directory;
import pl.cloudfolder.domain.storage.StorageItem;
import pl.cloudfolder.infrastructure.google.clients.GoogleException;

import java.io.IOException;
import java.util.*;

/**
 * Created by Adiki on 2015-05-03.
 */
public class GoogleStorageManager {
    private static final String MIME_TYPE_FOLDER = "application/vnd.google-apps.folder";
    private final Drive drive;

    public GoogleStorageManager(Drive drive) {
        this.drive = drive;
    }

    public Drive.About about() {
        return drive.about();
    }

    public Collection<StorageItem> listingForDirectoryId(String directoryId) {
        File file = getFile(directoryId);
        List<File> children = getChildrenForFile(file);
        List<StorageItem> storageItems = new ArrayList<>();
        for (File child : children) {
            StorageItem storageItem;
            if (isDirectory(child)) {
                storageItem = new Directory(child.getTitle(), child.getId());
            } else {
                storageItem = new pl.cloudfolder.domain.storage.File(child.getTitle(), child.getId());
            }
            storageItems.add(storageItem);
        }
        return storageItems;
    }

    private File getFile(String id) {
        try {
            return drive.files().get(id).execute();
        } catch (IOException e) {
            throw new GoogleException(e);
        }
    }

    private List<File> getChildrenForFile(File file) {
        try {
            List<File> resultList = new LinkedList<>();
            Drive.Files.List request = drive.files().list();
            request.setQ("trashed = false and '" + file.getId() + "' in parents");
            request.setMaxResults(1000);
            do {
                FileList fileList = request.execute();
                List<File> items = fileList.getItems();
                resultList.addAll(items);
                request.setPageToken(fileList.getNextPageToken());
            } while (request.getPageToken() != null && request.getPageToken().length() > 0);
            return resultList;
        } catch (IOException e) {
            throw new GoogleException(e);
        }
    }

    public boolean isDirectory(File file) {
        return MIME_TYPE_FOLDER.equals(file.getMimeType());
    }

    public void createDirectoryWithNameInDirectoryWithId(String name, String parentDirectoryId) {
        File newDirectory = new File();
        newDirectory.setTitle(name);
        newDirectory.setMimeType(MIME_TYPE_FOLDER);
        newDirectory.setParents((Arrays.asList(new ParentReference().setId(parentDirectoryId))));
        try {
            drive.files().insert(newDirectory).execute();
        } catch (IOException e) {
            throw new GoogleException(e);
        }
    }

    public void downloadFileToLocation(String fileId, String fileLocation) {
    }
}
