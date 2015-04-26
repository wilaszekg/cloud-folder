package pl.cloudfolder.domain;

public interface UserService<Credentials> {
    public String loginURL();

    public Credentials finishAuthentication(String status, String code);

}
