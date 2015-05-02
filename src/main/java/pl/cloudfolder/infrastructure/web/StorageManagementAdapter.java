package pl.cloudfolder.infrastructure.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cloudfolder.application.StorageManagementPort;
import pl.cloudfolder.application.UsersManagementPort;
import pl.cloudfolder.application.dto.ClientDto;
import pl.cloudfolder.domain.storage.Directory;
import pl.cloudfolder.domain.storage.StorageItem;

import java.util.List;

/**
 * Created by Adiki on 2015-05-02.
 */

@Controller
@RequestMapping("/")
public class StorageManagementAdapter {
    @Autowired
    private UsersManagementPort usersManagementPort;

    @Autowired
    private StorageManagementPort storageManagementPort;


    @RequestMapping("/test")
    public String auth() {
        ClientDto userClient = usersManagementPort.userClients().iterator().next();
        List<StorageItem> l = storageManagementPort.rootListingForUserClient(userClient);
        Directory dir = null;
        for (StorageItem storageItem : l) {
            if (storageItem instanceof Directory) {
                dir = (Directory) storageItem;
            }
        }
        List<StorageItem> l2 = storageManagementPort.listingForDirectoryAndUserClient(dir, userClient);
        return "main";
    }
}
