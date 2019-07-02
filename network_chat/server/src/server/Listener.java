package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Listener {
    private Map<String, ClientHandler> clients= Collections.synchronizedMap(new HashMap<String, ClientHandler>());
    public Listener() {
        ServerSocket server = null;
        Socket socket = null;

        try {
            AuthService.connect();
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился и пытается авторизоваться");
                new ClientHandler(socket, this);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                AuthService.disconnect();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void broadCastMsg(String msg) {
        for(Map.Entry<String, ClientHandler> item : clients.entrySet()){
            item.getValue().sendMsg(msg);
        }
    }
    public void DeleteClientHandler(String nick){
        clients.remove(nick);
        System.out.println(nick+" отключился, онлайн осталось "+clients.size());
    }
    public void AddClientHandler(ClientHandler thisClient, String nick)
    {
        clients.put(nick,thisClient);
        System.out.println(nick+" авторизовался");
    }
    public void sendMsgClientHadler(String nickOfRecipient,String msg,String nickOfSender)
    {
        if(clients.get(nickOfRecipient)!=null)
            clients.get(nickOfRecipient).sendMsg("/w "+nickOfSender+":"+msg);
        else
            clients.get(nickOfSender).sendMsg("Такого пользователя в чате в данный момент нет");
    }
}
