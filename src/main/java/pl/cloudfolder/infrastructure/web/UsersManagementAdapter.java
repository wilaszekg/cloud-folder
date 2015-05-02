package pl.cloudfolder.infrastructure.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.cloudfolder.application.UsersManagementPort;
import pl.cloudfolder.application.dto.ClientDto;

import java.util.Collection;

@Controller
@RequestMapping("/")
public class UsersManagementAdapter {
    @Autowired
    private UsersManagementPort usersManagementPort;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) throws Exception {
        buildModelMap(model);
        return "main";
    }

    @RequestMapping("/dropbox/auth")
    public String auth(ModelMap model, String state, String code) {
        ClientDto clientDto = usersManagementPort.finishDropboxAuthenticationWithStateAndCode(state, code);
        buildModelMap(model);
        model.addAttribute("newClient", clientDto);
        return "main";
    }

    @RequestMapping("/google/auth")
    public String auth(ModelMap model, String code) {
        ClientDto clientDto = usersManagementPort.finishGoogleAuthenticationWithCode(code);
        buildModelMap(model);
        model.addAttribute("newClient", clientDto);
        return "main";
    }

    private void buildModelMap(ModelMap model) {
        model.addAttribute("dropbox_redirect_url", usersManagementPort.loginUrlForDropbox());
        model.addAttribute("google_redirect_url", usersManagementPort.loginUrlForGoogle());
        Collection<ClientDto> clients = usersManagementPort.userClients();
        try {
            model.addAttribute("clients", objectMapper.writeValueAsString(clients));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}