package bloodybypass;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTabPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    private Boolean isReadyForSubmission = false;
    private PieChart bookChart;
    private PieChart memberChart;

    @FXML
    private HBox mouse_info;
    @FXML
    private HBox bypass_info;
    @FXML
    private TextField mouseIDInput;
    @FXML
    private TextField bypassTimeInput;
    @FXML
    private StackPane rootPane;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private Tab mouseTab;
    @FXML
    private JFXTabPane mainTabPane;
    @FXML
    private JFXButton btnBypass;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            mouseIDInput.setText(Main.getBloodyHWID());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initComponents();
    }

    @FXML
    private void loadMouseInfo(ActionEvent event) throws IOException, InterruptedException {

        String id = mouseIDInput.getText();
        mouseIDInput.setText(Main.getBloodyHWID());
    }

    @FXML
    private void loadBypassTime(ActionEvent event) {
    }

    @FXML
    private void loadBypassOperation(ActionEvent event) throws IOException, InterruptedException {
        if(mouseIDInput.getText().isEmpty() || bypassTimeInput.getText().isEmpty()) {
            JFXButton btn = new JFXButton("Ok");
            AlertMaker.showMaterialDialog(rootPane, rootAnchorPane, Arrays.asList(btn), "Invalid Input", "You have to provide a real mouse ID & Bypass time in seconds !");
            return;
        }

        String mouseID = mouseIDInput.getText();
        try {
            int time = Integer.parseInt(bypassTimeInput.getText());
        } catch (NumberFormatException e) {
            JFXButton btn = new JFXButton("Ok");
            AlertMaker.showMaterialDialog(rootPane, rootAnchorPane, Arrays.asList(btn), "Invalid Input", "You have to provide a real mouse ID & Bypass time in seconds !");
            return;
        }
        Integer bypassTime = Integer.valueOf(bypassTimeInput.getText());

        btnBypass.setText("Bypassing...");
        btnBypass.setDisable(true);
        if(Main.bypassBloody(mouseID, bypassTime)) {
            btnBypass.setText("Bypass");
            btnBypass.setDisable(false);
        }
    }

    @FXML
    private void loadMouseInfo2(ActionEvent event) {
        clearEntries();
        ObservableList<String> issueData = FXCollections.observableArrayList();
        isReadyForSubmission = false;

    }

    private Stage getStage() {
        return (Stage) rootPane.getScene().getWindow();
    }

    @FXML
    private void handleMenuClose(ActionEvent event) {
        getStage().close();
    }

    @FXML
    private void handleMenuAddBook(ActionEvent event) {
    }

    @FXML
    private void handleMenuAddMember(ActionEvent event) {
    }

    @FXML
    private void handleMenuViewBook(ActionEvent event) {
    }

    @FXML
    private void handleAboutMenu(ActionEvent event) {
    }

    @FXML
    private void handleMenuSettings(ActionEvent event) {
    }

    @FXML
    private void handleMenuViewMemberList(ActionEvent event) {
    }

    @FXML
    private void handleIssuedList(ActionEvent event) {
    }

    @FXML
    private void handleMenuFullScreen(ActionEvent event) {
        Stage stage = getStage();
        stage.setFullScreen(!stage.isFullScreen());
    }

    private void clearEntries() {

        disableEnableControls(false);
    }

    private void disableEnableControls(Boolean enableFlag) {
    }

    @FXML
    private void handleBypassButtonKeyPress(KeyEvent event) throws IOException, InterruptedException {
        if (event.getCode() == KeyCode.ENTER) {
            loadBypassOperation(null);
        }
    }

    private void initComponents() {
        mainTabPane.tabMinWidthProperty().bind(rootAnchorPane.widthProperty().divide(mainTabPane.getTabs().size()).subtract(15));
    }
}
