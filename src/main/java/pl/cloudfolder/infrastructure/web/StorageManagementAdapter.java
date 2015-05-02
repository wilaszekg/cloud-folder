package pl.cloudfolder.infrastructure.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cloudfolder.application.StorageManagementPort;
import pl.cloudfolder.application.UsersManagementPort;

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
        storageManagementPort.rootListeningForUserClient(usersManagementPort.userClients().iterator().next());
        return "main";
    }
}
