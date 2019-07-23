package sample;

import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ClientInfoStatus;

public class serverHendler {
    final String IP_ADRESS = "127.0.0.1";
    final int PORT = 8189;
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    Controller Client;
    public serverHendler(Controller client)
    {
        Client=client;
        connect();
    }
    public void connect()
    {
        try {
            socket = new Socket(IP_ADRESS, PORT);
            handlerClient();
        } catch (IOException e) {
            Client.addInfo("Не удалось подключиться к серверу");
            return;
        }
    }
    public boolean auth(){
        while (true) {
            String str;
            try {
                str = in.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
                str="error";
            }
            switch (str){
                case ("/authok"):
                    Client.setAuthorized(false);
                    return true;
                case ("/reg"):
                    Client.setRegOrAuth(false);
                    break;
                case ("/noauth"):
                    sendInf("Проверьте правильность написания логина и пароля");
                    break;
                case ("/alreadyOnline"):
                    sendInf("Под данной учетной записью уже авторизовался пользователь!");
                    break;
                case ("/noreg"):
                    sendInf("Данное имя уже занято");
                    break;
                case ("/serverClosed"):
                    return false;
                default:
                    sendInf("Не известная ошибка");
                    break;
            }
        }
    }
    public void handlerClient(){
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(auth()){
                            String nick = in.readUTF();
                            Client.nick=nick;
                            System.out.println("Ваш ник = "+nick);
                            while (true) {
                                String str = in.readUTF();
                                if(str.equals("/serverClosed")) {
                                   Client.setAuthorized(true);
                                    break;
                                }
                                Platform.runLater(()->{
                                    if(str.startsWith("/"))
                                        Client.getCmdMsg(str);
                                    else
                                        Client.getMsg(str);
                                });
                                                        }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (IOException e) {

            Client.addInfo("Произошел разрыв с сервером");
            e.printStackTrace();
        }
    }
    public void sendMsg(String msg)
    {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }
    void sendInf(String msg)
    {
        Platform.runLater(()->{
            Client.addInfo(msg);
        });
    }
}
