package pl.cloudfolder.application.dto;

public class FileDto {
    private String id;
    private String clientId;
    private String name;
    private boolean folder;

    public FileDto(String id, String clientId, String name, boolean folder) {
        this.id = id;
        this.clientId = clientId;
        this.name = name;
        this.folder = folder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFolder() {
        return folder;
    }

    public void setFolder(boolean folder) {
        this.folder = folder;
    }
}
