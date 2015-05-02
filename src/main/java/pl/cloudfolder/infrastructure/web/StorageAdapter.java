package pl.cloudfolder.infrastructure.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.cloudfolder.application.StoragePort;
import pl.cloudfolder.application.dto.FileDto;

import java.util.Collection;

@Controller
@RequestMapping("/storage")
public class StorageAdapter {

    private StoragePort storagePort;

    @Autowired
    public StorageAdapter(StoragePort storagePort) {
        this.storagePort = storagePort;
    }

    @RequestMapping("/{userId}")
    @ResponseBody
    public Collection<FileDto> userMainFolder(@PathVariable String userId) {
        return storagePort.userMainFolder(userId);
    }

    @RequestMapping("/{userId}/{folderId}")
    @ResponseBody
    public Collection<FileDto> folderContent(@PathVariable String userId, @PathVariable String folderId) {
        return storagePort.folderContent(userId, folderId);
    }
}
