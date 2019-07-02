package sample;

import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class serverHendler {
    final String IP_ADRESS = "192.168.0.10";
    final int PORT = 8189;
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    Controller Client;
    public serverHendler(Controller client)
    {
        Client=client;
        try {
            socket = new Socket(IP_ADRESS, PORT);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF(); //ТУТ ВОЗНИКАЕТ ОШИБКА
                            if(str.startsWith("/authok")) {
                                Client.setAuthorized(false);
                                break;
                            } else {
                                Client.addInfo("Не верный логин!");
                            }
                        }

                        while (true) {
                            String str = in.readUTF();
                            if(str.equals("/serverClosed")) {
                                Client.setAuthorized(true);
                                break;
                            }
                            Platform.runLater(()->{
                                Client.getMsg(str);
                            });
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
}
