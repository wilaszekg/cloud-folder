package pl.cloudfolder.infrastructure.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    private final Config config;

    public AppConfig() {
        config = ConfigFactory.load();
    }

    public String googleClientId() {
        return config.getString("google.cliendId");
    }

    public String googleClientSecret() {
        return config.getString("google.clientSecret");
    }

    public String dropboxAppKey() {
        return config.getString("dropbox.appKey");
    }

    public String dropboxAppSecret() {
        return config.getString("dropbox.appSecret");
    }
}
