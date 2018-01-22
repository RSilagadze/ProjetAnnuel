package controller;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by Nicolas_Travail on 11/10/2017.
 */
public class AuthentController extends Stage implements Initializable {
    public static User user;
    @FXML
    TextField codeField;
    private String key;

    private static void sendMessage(String subject, String text, String destinataire) {
        // 1 -> Création de la session
        Properties properties = new Properties();


        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.user", "esgi.crypto@gmail.com");
        properties.setProperty("mail.from", "esgi.crypto@gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setText(text);
            message.setSubject(subject);
            message.addRecipients(Message.RecipientType.TO, destinataire);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        Transport transport = null;
        try {
            transport = session.getTransport("smtp");
            transport.connect("esgi.crypto@gmail.com", "passwordcrypto");
            transport.sendMessage(message, new Address[]{new InternetAddress(destinataire)});
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            try {
                if (transport != null) {
                    transport.close();
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }


    }

    private static String generateKey() {
        Random random = new Random();

        StringBuilder generatedString = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int letter = random.nextInt(74) + 48;
            generatedString.append((char) letter);
        }

        return generatedString.toString();
    }

    @FXML
    public void handleAuthentOnClick(ActionEvent event) {
        AnchorPane root;
        try {


            if (!codeField.getText().equals(key)) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        sendMessage("Authentification", key, user.getMail());
                    }
                }).run();
                Stage dialog = new Stage();
                dialog.initStyle(StageStyle.DECORATED);
                Scene scene = new Scene(new Group(new Text(20, 20, "Login Error!")), 150, 50);
                dialog.setScene(scene);
                dialog.show();
                key = generateKey();
            } else {
                root = FXMLLoader.load(mainpackage.MainApplication.class.getResource("/mainWindow.fxml"));
                Stage linkStage = new Stage();
                linkStage.setTitle("Downloader");
                linkStage.initStyle(StageStyle.DECORATED);
                linkStage.setScene(new Scene(root, 1366, 800));
                linkStage.show();
                Stage stage = (Stage) this.codeField.getScene().getWindow();
                stage.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        key = generateKey();
        new Thread(new Runnable() {
            @Override
            public void run() {
               // if(user!=null)
                sendMessage("Authentification", key, AuthentController.user.getMail());
            }
        }).run();
    }
}
