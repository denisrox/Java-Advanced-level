package server;

import java.sql.*;

public class AuthService {

    private static Connection connection;
    private static Statement stmt;

    public static void connect() throws SQLException {
        try {
            // обращение к драйверу
            Class.forName("org.sqlite.JDBC");
            // установка подключения
            connection = DriverManager.getConnection("jdbc:sqlite:MyDB.db");
            // создание Statement для возможности оправки запросов
            stmt = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        // формирование запроса
        System.out.println("1");
        String sql = String.format("SELECT nick FROM accounts where login = '%s' and password = '%s'", login, pass);
        System.out.println("2");
        try {
            // оправка запроса и получение ответа
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery("SELECT * FROM accounts"/*sql*/);
            System.out.println("4");
            // если есть строка возвращаем результат если нет то вернеться null
            if(rs.next()) {
                System.out.println("6");
                return rs.getString(1);
            }
            System.out.println("5");

        } catch (SQLException e) {
            System.out.println("ошибка при обращению к драйверу2");
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
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
