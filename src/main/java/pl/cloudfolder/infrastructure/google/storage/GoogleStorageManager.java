package pl.cloudfolder.infrastructure.google.storage;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.util.IOUtils;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentList;
import com.google.api.services.drive.model.ParentReference;
import pl.cloudfolder.domain.storage.Directory;
import pl.cloudfolder.domain.storage.StorageItem;
import pl.cloudfolder.infrastructure.google.clients.GoogleException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileLocation);
            File file = getFile(fileId);
            if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
                HttpResponse response = drive.getRequestFactory()
                        .buildGetRequest(new GenericUrl(file.getDownloadUrl())).execute();
                IOUtils.copy(response.getContent(), outputStream);
            }
        } catch (IOException e) {
            throw new GoogleException();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void uploadFileFromPathToDirectory(String filePath, String directoryId) {
        java.io.File inputFile = new java.io.File(filePath);
        File file = new File();
        file.setTitle(inputFile.getName());
        file.setParents(Arrays.asList(new ParentReference().setId(directoryId)));
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(inputFile);
            InputStreamContent inputStreamContent = new InputStreamContent(null, fileInputStream);
            drive.files().insert(file, inputStreamContent).execute();
        } catch (IOException e) {
            throw new GoogleException(e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deleteFileOrDirectory(String id) {
        try {
            drive.files().delete(id).execute();
        } catch (IOException e) {
            throw new GoogleException(e);
        }
    }
}
