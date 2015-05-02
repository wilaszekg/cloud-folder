package pl.cloudfolder.application;

import org.springframework.stereotype.Component;
import pl.cloudfolder.application.dto.FileDto;

import java.util.Collection;
import java.util.LinkedList;

@Component
public class StoragePort {
    public Collection<FileDto> userMainFolder(String userId) {
        LinkedList<FileDto> files = new LinkedList<>();
        files.add(new FileDto("1", userId, "images", null, true));
        files.add(new FileDto("2", userId, "documents", null, true));
        files.add(new FileDto("3", userId, "prezentacja.doc", null, false));
        return files;
    }

    public Collection<FileDto> folderContent(String userId, String folderId) {
        LinkedList<FileDto> files = new LinkedList<>();
        files.add(new FileDto("4", userId, "wakacje 2014", null, true));
        files.add(new FileDto("5", userId, "feie 2015", null, true));
        files.add(new FileDto("6", userId, "swieta 2014.jpg", null, false));
        return files;
    }
}
