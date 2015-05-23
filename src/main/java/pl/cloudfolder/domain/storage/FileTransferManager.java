package pl.cloudfolder.domain.storage;

import org.apache.commons.io.FileUtils;
import pl.cloudfolder.domain.clients.AppClient;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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

    public void copyFileToDirectory(String fileId, String directoryId) throws StorageException, IOException {
        File tempDir = new File("temp" + File.pathSeparator + UUID.randomUUID().toString());
        tempDir.mkdirs();
        String filename = UUID.randomUUID().toString();
        sourceClient.downloadFileToLocation(fileId, tempDir.getPath(), filename);
        destinationClient.uploadFileFromPathToDirectory(tempDir.getPath() + File.pathSeparator + filename, directoryId);
        FileUtils.deleteDirectory(tempDir);

    }

    public void moveFileToDirectory(String fileId, String directoryId) throws StorageException, IOException {
        copyFileToDirectory(fileId, directoryId);
        sourceClient.deleteFileOrDirectory(fileId);
    }
}
