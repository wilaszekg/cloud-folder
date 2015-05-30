package pl.cloudfolder.domain.storage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

    public FileTransferManager(AppClient sourceClient) {
        this.sourceClient = sourceClient;
    }

    public void uploadFile(byte[] bytes, String directoryId, String name) {
        try {
            File tempDir = createTempDir();
            File file = new File(tempDir.getPath() + "/" + name);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            stream.write(bytes);
            stream.close();
            sourceClient.uploadFileFromPathToDirectory(tempDir.getPath() + "/" + name, directoryId);
            deleteTempDir(tempDir);
        } catch (IOException e) {
            throw new StorageException(e);
        }
    }

    public File downloadFile(String fileId) {
        File tempDir = createTempDir();
        String filename = sourceClient.filenameForFileId(fileId);
        sourceClient.downloadFileToLocation(fileId, tempDir.getPath(), filename);
        //deleteTempDir(tempDir);
        return new File(tempDir.getAbsolutePath() + "/" + filename);
    }

    public void copyFileToDirectory(String fileId, String directoryId) {
        File tempDir = createTempDir();
        String filename = sourceClient.filenameForFileId(fileId);
        sourceClient.downloadFileToLocation(fileId, tempDir.getPath(), filename);
        destinationClient.uploadFileFromPathToDirectory(tempDir.getPath() + "/" + filename, directoryId);
        deleteTempDir(tempDir);

    }

    private void deleteTempDir(File tempDir) {
        try {
            FileUtils.deleteDirectory(tempDir);
        } catch (IOException e) {
            throw new StorageException(e);
        }
    }

    private File createTempDir() {
        File tempDir = new File("temp" + "/" + UUID.randomUUID().toString());
        tempDir.mkdirs();
        return tempDir;
    }

    public void moveFileToDirectory(String fileId, String directoryId) {
        copyFileToDirectory(fileId, directoryId);
        sourceClient.deleteFileOrDirectory(fileId);
    }
}
