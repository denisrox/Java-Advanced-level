package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Listener server;
    private String nick="1";

    public ClientHandler(Socket socket, Listener server) {
        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            authorization();
                            handlingClient();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                in.close();
                                out.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                server.DeleteClientHandler(nick);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void authorization() throws IOException {
        while (true) {
            String str = in.readUTF();
            if (str.startsWith("/auth")) {
                String[] tokens = str.split(" ");
                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                if (newNick != null) {
                    sendMsg("/authok");
                    nick = newNick;
                    server.AddClientHandler(ClientHandler.this,nick);
                    break;
                } else {
                    sendMsg("Неверный логин/пароль!");
                }
            }
            else if(str.startsWith("/reg"))
            {
                String[] tokens = str.split(" ");
                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                if (newNick != null) {
                    sendMsg("/reg");
                    nick = newNick;
                    server.AddClientHandler(ClientHandler.this,nick);
                    break;
                } else {
                    sendMsg("такой логин уже существует");
                }
            }
        }
    }
    public void handlingClient() throws IOException {
        while (true) {
            String str = in.readUTF();
            if(str.startsWith("/")) {
                String[] tokens = str.split(" ",3);
                switch (tokens[0]){
                    case "/end":
                        out.writeUTF("/serverClosed");
                        return;
                    case "/w":
                        if(!server.sendMsgClientHadler(tokens[1],tokens[2],nick))
                            sendMsg("В данный момент пользователя с таким ником нет!");
                        break;
                    case "/blaclist":
                        break;
                    default:
                        out.writeUTF("/commandNotFound");
                        break;
                }
            }
            else{
                server.broadCastMsg(str);
            }
        }
    }
}
