package bloodybypass;

import com.jfoenix.controls.JFXPasswordField;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Main extends Application {

    @FXML
    private JFXPasswordField macAddress;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        stage.setTitle("BloodyBypass by GrayR0ot");
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        launch(args);
    }

    public static String getBloodyHWID() throws IOException, InterruptedException {
        String[] commands = {"devcon.exe", "find", "*VID_09DA*"};
        Process proc = Runtime.getRuntime().exec(commands);
        InputStream stdIn = proc.getInputStream();
        InputStreamReader isr = new InputStreamReader(stdIn);
        BufferedReader br = new BufferedReader(isr);
        String line = br.readLine();
        String mouseHWID = line;
        try {
            mouseHWID = mouseHWID.substring(0, 21).replaceAll("HID\\V", "");
        } catch (Exception e) {
            mouseHWID = "Not Found";
        }
        return mouseHWID;
    }

    public static boolean bypassBloody(String hwid, int time) throws IOException, InterruptedException {

        //Launch bypass
        String[] commands1 = {"Elevate.exe", "devcon.exe", "remove", "*USB\\VID_09DA*"};
        Runtime.getRuntime().exec(commands1);

        Thread.sleep(time * 1000);


        //Fnish bypass
        String[] commands2 = {"Elevate.exe", "devcon.exe", "rescan"};
        Runtime.getRuntime().exec(commands2);
        return true;
    }

    public static String getMac() {
        String MAC = "";
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            MAC = sb.toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return MAC;
    }

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}