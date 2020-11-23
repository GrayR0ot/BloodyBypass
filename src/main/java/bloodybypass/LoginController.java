package bloodybypass;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @FXML
    private JFXTextField loginstatus;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField macAddress;

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws Exception {
        String user = StringUtils.trimToEmpty(username.getText());
        LoginStatus loginStatus = checkLogin(user);
        if(!loginStatus.isState()) {
            loginstatus.getStyleClass().add("wrong-credentials");
            loginstatus.setText("ERROR: " + loginStatus.getError());
        } else {
            loginstatus.setText("SUCCESS: Launching the app");
            closeStage();
            loadMain();
        }
    }

    private LoginStatus checkLogin(String username) {
        LoginStatus loginstatus = new LoginStatus(false, "Network unavailable!");
        try {
            OkHttpClient client = new OkHttpClient();
            JSONObject jsonData = new JSONObject();
            jsonData.put("username", username);
            jsonData.put("mac_address", Main.getMac());
            System.out.println(jsonData.toString());
            RequestBody requestBody = RequestBody.create(jsonData.toString(), JSON);
            Request request = new Request.Builder()
                    .url("https://dev.grayroot.eu/api/bloodybypass")
                    .post(requestBody)
                    .addHeader("content-type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            String jsonOutput = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonOutput);
            if (jsonObject.has("error")) {
                loginstatus.setError(jsonObject.getString("error"));
            } else if (jsonObject.has("hash")) {
                if (jsonObject.getString("hash").equalsIgnoreCase(Main.MD5(username + Main.getMac()))) {
                    loginstatus.setError(null);
                    loginstatus.setState(true);
                } else {
                    loginstatus.setError("Bad credentials");
                }
            } else {
                loginstatus.setError("Unknown error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginstatus;
    }

    @FXML
    private void closeStage() {
        ((Stage) username.getScene().getWindow()).close();
    }

    void loadMain() throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/main.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("BloodyBypass by Grayr0ot");
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}