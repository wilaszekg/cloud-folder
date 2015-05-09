package pl.cloudfolder.infrastructure.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.cloudfolder.application.StoragePort;
import pl.cloudfolder.application.dto.StorageItemDto;

import java.util.Collection;

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
}
