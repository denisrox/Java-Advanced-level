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
        if (!msg.startsWith("/"))
            AuthService.addMsg(msg);
        for(Map.Entry<String, ClientHandler> item : clients.entrySet()){
            if(!msg.startsWith("/"))
                if(!AuthService.IsIgnore(item.getKey(),msg.substring(0,msg.indexOf(":"))))
                    item.getValue().sendMsg(msg);
                else;
            else {
                item.getValue().sendMsg(msg);
            }
        }
    }
    public void DeleteClientHandler(String nick){
        broadCastMsg("/removeUser "+nick);
        clients.remove(nick);
        System.out.println(nick+" отключился, онлайн осталось "+clients.size());
    }
    public void AddClientHandler(ClientHandler thisClient, String nick)
    {
        broadCastMsg("/addUsers "+nick);
        String users = new String();
        for (String key:clients.keySet()) {
            users+=" "+key;  //ПЕРЕДЕЛАТЬ В СТРИНГБАФФЕР!!!!!!!!!!!!!!!!!!НЕ ЗАБЫТЬ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }
        thisClient.sendMsg("/addUsers"+users);
        clients.put(nick,thisClient);
        AuthService.allMsg(thisClient);
        System.out.println(nick+" авторизовался");
    }
    public void sendMsgClientHadler(String nickOfRecipient,String msg,String nickOfSender)
    {
        if(clients.get(nickOfRecipient)!=null)
        {
            clients.get(nickOfRecipient).sendMsg("/w "+nickOfSender+" "+msg);
            clients.get(nickOfSender).sendMsg("Сообщиение для "+nickOfRecipient+ " отправлено");
        }

        else
            clients.get(nickOfSender).sendMsg("Такого пользователя в чате в данный момент нет");
    }
    public boolean getClient(String nick)
    {
        if(clients.get(nick)==null)
            return true;
        else
            return false;
    }
}
