package pl.cloudfolder.infrastructure.web;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import pl.cloudfolder.application.StoragePort;
import pl.cloudfolder.application.dto.StorageItemDto;

@Controller
@RequestMapping("/storage")
public class StorageAdapter {

    private StoragePort storagePort;

    @Autowired
    public StorageAdapter(StoragePort storagePort) {
        this.storagePort = storagePort;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public Collection<StorageItemDto> userMainFolder(@PathVariable String userId) {
        return storagePort.rootListingForUserId(userId);
    }

    @RequestMapping(value = "/{userId}/{directoryId}", method = RequestMethod.GET)
    @ResponseBody
    public Collection<StorageItemDto> folderContent(@PathVariable String userId, @PathVariable String directoryId) {
        return storagePort.listingForUserIdAndDirectoryId(userId, directoryId);
    }

    @RequestMapping(value = "/{userId}/{directoryId}/{newDirectory}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void createDirectory(@PathVariable String userId,
                                @PathVariable String directoryId,
                                @PathVariable String newDirectory) {
        storagePort.createDirectoryWithNameForUserIdInDirectoryWithId(newDirectory, userId, directoryId);
    }

    @RequestMapping(value = "/{userId}/{newDirectory}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void createDirectory(@PathVariable String userId,
                                @PathVariable String newDirectory) {
        storagePort.createDirectoryWithNameInRootDirectory(newDirectory, userId);
    }

    @RequestMapping(value = "/{sourceUser}/{sourceFile}/copy", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void copyTo(@PathVariable String sourceUser,
                       @PathVariable String sourceFile,
                       @RequestParam String clientId,
                       @RequestParam(required = false) String directoryId) {
        storagePort.copyFile(sourceUser, sourceFile, clientId, Optional.ofNullable(directoryId));
    }

    @RequestMapping(value = "/{sourceUser}/{sourceFile}/move", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void moveTo(@PathVariable String sourceUser,
                       @PathVariable String sourceFile,
                       @RequestParam String clientId,
                       @RequestParam(required = false) String directoryId) {
        storagePort.moveFile(sourceUser, sourceFile, clientId, Optional.ofNullable(directoryId));
    }

    @RequestMapping(value = "/{userId}/{fileId}/remove", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void removeFile(@PathVariable String userId,
                           @PathVariable String fileId) {
        storagePort.removeFile(userId, fileId);
    }

    @RequestMapping(value = "/{userId}/{directoryId}/upload", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String uploadFile(@PathVariable String userId,
                             @PathVariable String directoryId,
                             @RequestParam MultipartFile file) throws IOException {
        storagePort.uploadFile(userId, directoryId, file.getBytes(), file.getOriginalFilename());
        return "redirect:/";
    }

    @RequestMapping(value = "/{userId}/upload", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void uploadFileToMainFolder(@PathVariable String userId,
                                       @RequestParam MultipartFile file) throws IOException {
        storagePort.uploadFile(userId, null, file.getBytes(), file.getOriginalFilename());
    }

    @RequestMapping(value = "/{userId}/{fileId}/download/{visibleName}", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource downloadFile(@PathVariable String userId,
                                           @PathVariable String fileId,
                                           @PathVariable String visibleName) {
        return new FileSystemResource(storagePort.downloadFile(userId, fileId));
    }
}
