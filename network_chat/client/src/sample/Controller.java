package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.WindowEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Struct;


public class Controller   {

    @FXML
    TextField textField;
    @FXML
    TextFlow textFlow;
    @FXML
    Button sentBtn;
    @FXML
    VBox bottomPanel;
    @FXML
    VBox upperPanel;
    @FXML
    TextField loginFiled;
    @FXML
    Label label;
    @FXML
    PasswordField passwordField;

    private boolean isAuthorized=false;



    final String IP_ADRESS = "192.168.0.10";
    final int PORT = 8189;
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    serverHendler hendler;
    public void connect()
    {
        new serverHendler(this);
    }
    public void label()
    {
        label.setText("Вы ввели не верный пароль!");
    }
    public void tryToAuth() {
        if(hendler==null || hendler.getSocket().isClosed()) {
            // сначала подключаемся к серверу
            hendler=new serverHendler(this);
        }
        else
        {
            // отправка сообщений на сервер для авторизации
            hendler.sendMsg("/auth " + loginFiled.getText() + " " + passwordField.getText());
            //out.writeUTF("/auth " + loginFiled.getText() + " " + passwordField.getText());
            loginFiled.clear();
            passwordField.clear();
        }

    }
    public void sendMsg()
    {
        hendler.sendMsg(textField.getText());
        textField.clear();
        textField.requestFocus();

    }

    public void getMsg(String str)
    {

        Text text;
        if(textFlow.getChildren().size()==0){
            text = new Text(str);
        } else {
            text = new Text("\n" + str);
        }
        Smile smile=new Smile(text);
        textFlow.getChildren().addAll(smile.getPiecesOfText());
    }
    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;

        if(!isAuthorized) {
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);
        } else {
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
        }
    }

    public void exit()
    {
        if(hendler!=null)
            hendler.sendMsg("/end");
    }
    public void addInfo(String msg)
    {
        Platform.runLater(()->{
            label.setText(msg);
        });
    }

}
