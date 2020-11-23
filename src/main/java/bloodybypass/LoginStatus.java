package bloodybypass;

public class LoginStatus {

    boolean state;
    String error;

    public LoginStatus(boolean state, String error) {
        this.state = state;
        this.error = error;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
