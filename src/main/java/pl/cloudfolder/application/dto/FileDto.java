package pl.cloudfolder.application.dto;

public class FileDto {
    private String id;
    private String clientId;
    private String name;
    private String parentId;
    private boolean folder;

    public FileDto(String id, String clientId, String name, String parentId, boolean folder) {
        this.id = id;
        this.clientId = clientId;
        this.name = name;
        this.parentId = parentId;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isFolder() {
        return folder;
    }

    public void setFolder(boolean folder) {
        this.folder = folder;
    }
}
