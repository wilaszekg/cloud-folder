package pl.cloudfolder;

import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWebAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.cloudfolder.application.UsersManager;

import java.io.IOException;
import java.util.HashMap;

@Controller
@Scope("session")
@RequestMapping("/")
public class HelloController {
    @Autowired
    private UsersManager usersManager;

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
        model.addAttribute("dropbox_redirect_url", usersManager.redirectionURLForDropboxService());
        model.addAttribute("google_redirect_url", usersManager.redirectionURLForGoogleService());
		return "hello";
	}

    @RequestMapping("/dropbox/auth")
    public String auth(ModelMap model, String state, String code) throws Exception {
        String dropboxUserId = usersManager.dropboxUserIdForStateAndCode(state, code);
        String dropboxUsername = usersManager.usernameForUserId(dropboxUserId);
        model.addAttribute("dropbox_user_id", dropboxUsername);
        return "hello";
    }
}