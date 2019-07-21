package server;

import java.sql.*;

public class AuthService {

    private static Connection connection;
    private static Statement stmt;

    public static void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:MyDB.db");
            stmt = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        String sql = String.format("SELECT nick FROM accounts where login = '%s' and password = '%s'", login, pass);
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                return rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
    public static boolean insertNewUser(String login, String nick,String pass) {
        String sql = String.format("INSERT INTO Accounts(login, password, nick) VALUES('%s','%s','%s')", login, pass, nick);
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
    public static boolean IgnoreAddOrDelete(String nickSours, String nickDest)
    {
        String sqlSELECT = String.format("SELECT * FROM blackList where nickUser = '%s' and NickBlockUser='%s'", nickSours,nickDest);
        try {
            ResultSet rs = stmt.executeQuery(sqlSELECT);
            if(rs.next()) {
                String sqlINSERT = String.format("DELETE FROM blackList WHERE nickUser = '%s' and NickBlockUser='%s';", nickSours, nickDest);
                stmt.executeUpdate(sqlINSERT);
                return false;
            }
            else
            {
                String sqlINSERT = String.format("INSERT INTO blackList(nickUser, NickBlockUser) VALUES('%s','%s')", nickSours, nickDest);
                stmt.executeUpdate(sqlINSERT);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public static void allMsg(ClientHandler thisClient)
    {
        String sql="SELECT msg FROM chat";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                System.out.println(rs.getString(1));
                thisClient.sendMsg(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void addMsg(String msg)
    {
        String sql = String.format("INSERT INTO chat (msg) VALUES('%s')", msg);
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean IsIgnore(String nickSours, String nickDest)
    {
        String sqlSELECT = String.format("SELECT * FROM blackList where nickUser = '%s' and NickBlockUser='%s'", nickSours,nickDest);
        System.out.println(sqlSELECT);
        try {
            ResultSet rs = stmt.executeQuery(sqlSELECT);
            if(rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public static void disconnect() {
        try {
            // закрываем соединение
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
