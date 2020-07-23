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
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws Exception {
        String user = StringUtils.trimToEmpty(username.getText());
        String pass = StringUtils.trimToEmpty(password.getText());

        if (Main.MD5(user + pass + Main.getMac()).equals("950e04400d66d9b83538ca59c4426102")) {
            closeStage();
            loadMain();
        } else if (Main.MD5(user + pass + Main.getMac()).equals("3de9ff940c17788ceb6400cb4aa87e90")) {
            closeStage();
            loadMain();
        } else {
            username.getStyleClass().add("wrong-credentials");
            password.getStyleClass().add("wrong-credentials");
        }
    }

    @FXML
    private void closeStage() {
        ((Stage) username.getScene().getWindow()).close();
    }

    void loadMain() throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/bloodybypass/main.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("BloodyBypass by Grayr0ot");
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}