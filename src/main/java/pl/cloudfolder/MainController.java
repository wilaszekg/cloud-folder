package pl.cloudfolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.cloudfolder.application.UsersManager;

import java.util.Iterator;

@Controller
@Scope("session")
@RequestMapping("/")
public class MainController {
    @Autowired
    private UsersManager usersManager;

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) throws Exception {
        buildModelMap(model);
		return "hello";
	}

    @RequestMapping("/dropbox/auth")
    public String auth(ModelMap model, String state, String code) throws Exception {
        usersManager.finishDropboxAuthenticationWithStateAndCode(state, code);
        buildModelMap(model);
        return "hello";
    }

    @RequestMapping("google/auth")
    public String auth(ModelMap model, String code) throws Exception {
        usersManager.finishGoogleAuthenticationWithCode(code);
        buildModelMap(model);
        return "hello";
    }

    private void buildModelMap(ModelMap model) throws Exception {
        model.addAttribute("dropbox_redirect_url", usersManager.redirectionURLForDropboxService());
        model.addAttribute("google_redirect_url", usersManager.redirectionURLForGoogleService());
        Iterator<String> iterator = usersManager.loggedDropboxUserIds().iterator();
        model.addAttribute("dropbox_username", usersManager.usernameForUserId(iterator.hasNext() ? iterator.next() : null));
        iterator = usersManager.loggedGoogleUserIds().iterator();
        model.addAttribute("google_username", usersManager.usernameForUserId(iterator.hasNext() ? iterator.next() : null));
    }

}