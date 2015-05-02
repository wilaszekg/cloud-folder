package pl.cloudfolder.application.dto;

public class ClientDto {
    private String id;
    private String name;
    private boolean google;
    private boolean dropbox;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGoogle() {
        return google;
    }

    public void setGoogle(boolean google) {
        this.google = google;
    }

    public boolean isDropbox() {
        return dropbox;
    }

    public void setDropbox(boolean dropbox) {
        this.dropbox = dropbox;
    }
}
