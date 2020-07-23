package bloodybypass;

public class Preferences {

    String username;
    String password;

    public Preferences() {
        username = "admin";
        setPassword("admin");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
