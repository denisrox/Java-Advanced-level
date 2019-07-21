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
            System.out.println(str);
            if (str.startsWith("/auth")) {
                String[] tokens = str.split(" ");
                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                if (newNick != null) {
                    if(server.getClient(newNick))
                    {
                        sendMsg("/authok");
                        nick = newNick;
                        out.writeUTF(nick);
                        server.AddClientHandler(ClientHandler.this,nick);
                        break;
                    }else
                        sendMsg("/alreadyOnline");

                } else {
                    sendMsg("/noauth");
                }
            }
            else if(str.startsWith("/reg"))
            {
                System.out.println("as");
                String[] tokens = str.split(" ");
                System.out.println(tokens);
                if(AuthService.insertNewUser(tokens[1], tokens[2],tokens[3]))
                    sendMsg("/reg");
                else
                    sendMsg("/noreg");
            }
            else if(str.startsWith("/end"))
                sendMsg("/serverClosed");
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
                        server.sendMsgClientHadler(tokens[1],tokens[2],nick);
                        break;
                    case "/blaclist":
                        if(AuthService.IgnoreAddOrDelete(nick,tokens[1]))
                            sendMsg("Вы заблокировали "+tokens[1]);
                        else
                            sendMsg("Вы разблокировали "+tokens[1]);
                        break;
                    default:
                        out.writeUTF("/commandNotFound");
                        break;
                }
            }
            else{
                server.broadCastMsg(nick+": "+str);
            }
        }
    }
}
