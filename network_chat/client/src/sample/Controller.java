package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Struct;
import java.util.HashMap;


public class Controller   {

    @FXML
    TextField textField;
    @FXML
    TextFlow textFlow;
    @FXML
    Button sentBtn;
    @FXML
    VBox regiserPanel,loginPanel,regPanel;
    @FXML
    VBox chatPanel;
    @FXML
    TextField loginFiled,loginForRegistrFiled, nickForRegistrFiled;
    @FXML
    Label label;
    @FXML
    PasswordField passwordField, passwordForRegistrFiled;
    @FXML
    VBox PanelForText,OnlineUsers;

    private boolean isAuthorized=false;
    HashMap<String,Label> users = new HashMap<>();

    serverHendler hendler=new serverHendler(this);
    String nick="";

    public void handleClick(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        addInfo(sourceButton.getId());
    }

    public void tryToAuth() {
        if(hendler.socket==null) {
            hendler.connect();
        }
        if(hendler.socket!=null){
            if((!loginFiled.getText().equals(""))&&(!passwordField.getText().equals("")) ) {
                System.out.println(loginFiled.getText());
                hendler.sendMsg("/auth " + loginFiled.getText() + " " + passwordField.getText());
                loginFiled.clear();
                passwordField.clear();
            }else
                addInfo("Заполните все поля");
        }
        }

    public void tryToReg() {
        if(hendler.socket==null) {
            hendler.connect();
        }
        if(hendler.socket!=null){
            if(!loginForRegistrFiled.getText().equals("") && !passwordForRegistrFiled.getText().equals("") && !nickForRegistrFiled.getText().equals("")) {
                hendler.sendMsg("/reg " + loginForRegistrFiled.getText() + " " + nickForRegistrFiled.getText() +" " +passwordForRegistrFiled.getText());
                loginFiled.clear();
                passwordField.clear();
            }else
                addInfo("Заполните все поля");
        }
        else
            addInfo("!");
    }

    public void sendMsg()
    {
        hendler.sendMsg(textField.getText());
        textField.clear();
        textField.requestFocus();
    }

    public void getMsg(String str)
    {
        System.out.println(nick);
        HBox hbox = new HBox();
        Text text;
        if(str.startsWith(nick))
        {
            text=new Text(str.replace(nick,"Вы"));
            hbox.getStyleClass().add("hboxMyMsg");
            hbox.setAlignment(Pos.TOP_RIGHT);
        }else {
            text=new Text(str);
            hbox.getStyleClass().add("hboxNotMyMsg");
            hbox.setAlignment(Pos.TOP_LEFT);
        }
        Smile smile = new Smile(text);
        hbox.getChildren().addAll(smile.getPiecesOfText());
        PanelForText.getChildren().addAll(hbox);
        HBox.setHgrow(PanelForText, Priority.ALWAYS);
    }
    public void getCmdMsg(String msg)
    {
        System.out.println("Пришла команда"+msg);
        String[] tokens = msg.split(" ");
        switch (tokens[0])
        {
            case "/addUsers":
                for(int i = 1;i<tokens.length;i++){
                    Label userLabel = new Label(tokens[i]);
                    userLabel.setOnMouseClicked(this::selectClient);
                    users.put(tokens[i],userLabel);
                    OnlineUsers.getChildren().add(users.get(tokens[i]));
                }
                break;
            case "/removeUser":
                System.out.println("удаление "+tokens[1]);
                OnlineUsers.getChildren().remove(users.get(tokens[1]));
                users.remove(tokens[1]);
                break;
            case "/w":
                StringBuilder msgForUser = new StringBuilder("");
                for(int i = 2;i<tokens.length;i++)
                    msgForUser.append(" "+tokens[i]);
                getMsg(tokens[1] +" написал вам: "+msgForUser.toString());//нужно помимо tokens[2] сделать и остальные, иначе в личных сообщениях будет приходить онли одно слово
                break;
            default:
                System.out.println("Неизвестная команда: "+tokens[0]);
        }

    }
    public void selectClient(MouseEvent mouseEvent){
        if(mouseEvent.getButton()== MouseButton.PRIMARY){
            if(mouseEvent.getClickCount()==2)
            {
                hendler.sendMsg("/w "+((Label)mouseEvent.getSource()).getText()+" "+ textField.getText());
                textField.clear();
                textField.requestFocus();
            }
        }
        if(mouseEvent.getButton()== MouseButton.SECONDARY){
            hendler.sendMsg("/blaclist "+((Label)mouseEvent.getSource()).getText()); //в этой функции я дважды использовал то, что вы говорите не использовать "(label)" как это можно избежать?
        }
    }
    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;

        if(!isAuthorized) {
            chatPanel.setVisible(true);
            chatPanel.setManaged(true);
            regiserPanel.setVisible(false);
            regiserPanel.setManaged(false);
        } else {
            chatPanel.setVisible(false);
            chatPanel.setManaged(false);
            regiserPanel.setVisible(true);
            regiserPanel.setManaged(true);
        }
    }

    public void setRegOrAuth(boolean isReg) {
        if(isReg) {
            regPanel.setVisible(true);
            regPanel.setManaged(true);
            loginPanel.setVisible(false);
            loginPanel.setManaged(false);
        } else {
            regPanel.setVisible(false);
            regPanel.setManaged(false);
            loginPanel.setVisible(true);
            loginPanel.setManaged(true);
        }
    }
    public void setButtonReg(){
        setRegOrAuth(true);
    }
    public void setButtonLogin(){
        setRegOrAuth(false);
    }

    public void exit()
    {
        if(hendler!=null && hendler.socket!=null)
            hendler.sendMsg("/end");
    }
    public void addInfo(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }

}
