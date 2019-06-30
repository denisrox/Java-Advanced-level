package server;

public class Main {
    public static void main(String[] args) {
        //new Listener();
        String newNick = AuthService.getNickByLoginAndPass("login1", "123456");
    }
}