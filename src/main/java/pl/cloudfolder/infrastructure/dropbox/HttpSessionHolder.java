package pl.cloudfolder.infrastructure.dropbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * Created by adrian on 25/04/15.
 */
@Component
public class HttpSessionHolder {
    @Autowired
    private HttpSession httpSession;

    public HttpSession getSession() {
        return httpSession;
    }
}
