package pl.cloudfolder.domain.storage;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import pl.cloudfolder.domain.clients.AppClient;

/**
 * Created by Adiki on 2015-05-23.
 */
public class FileTransferManager {
    private AppClient sourceClient;
    private AppClient destinationClient;

    public FileTransferManager(AppClient sourceClient, AppClient destinationClient) {
        this.sourceClient = sourceClient;
        this.destinationClient = destinationClient;
    }

    public void copyFileToDirectory(String fileId, String filename, String directoryId) {
        File tempDir = new File("temp" + "/" + UUID.randomUUID().toString());
        tempDir.mkdirs();
        sourceClient.downloadFileToLocation(fileId, tempDir.getPath(), filename);
        destinationClient.uploadFileFromPathToDirectory(tempDir.getPath() + "/" + filename, directoryId);
        try {
            FileUtils.deleteDirectory(tempDir);
        } catch (IOException e) {
            throw new StorageException(e);
        }

    }

    public void moveFileToDirectory(String fileId, String filename, String directoryId) {
        copyFileToDirectory(fileId, filename, directoryId);
        sourceClient.deleteFileOrDirectory(fileId);
    }
}
