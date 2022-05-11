package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    PreparedStatement statement;
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users" +
                    "(id BIGINT NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(50) NOT NULL, " +
                    "lastname VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL, " +
                    "PRIMARY KEY (id))");
            statement.executeUpdate();
            System.out.println("Таблица создана.");
        } catch (SQLException e) {
            System.out.println("Таблица не создана или уже существует.");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            statement = connection.prepareStatement("DROP TABLE IF EXISTS `dbtest`.`users`;");
            statement.executeUpdate();
            System.out.println("Таблица удалена.");
        } catch (SQLException e) {
            System.out.println("Таблица не удалена или такой таблицы не существует.");
        }
    }

    public void saveUser(String name, String lastname, byte age) {
        try (Connection connection = Util.getConnection()) {
            statement = connection.prepareStatement("INSERT INTO users (name, lastname, age) VALUES (?, ?, ?);");
            statement.setString(1, name);
            statement.setString(2, lastname);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных.");
        } catch (SQLException e) {
            System.out.println("Пользователь не добавлен в таблицу.");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("Пользователь по id = " + id + " удален из таблицы.");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении пользователя или пользователя с таким id не существует.");
        }
    }
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong("id"));
                user.setName(result.getString("name"));
                user.setLastName(result.getString("lastname"));
                user.setAge(result.getByte("age"));
                list.add(user);
            }
        } catch (SQLException ignored) {}
        System.out.println(list);
        return list;
    }


    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            statement = connection.prepareStatement("TRUNCATE users");
            statement.executeUpdate();
            System.out.println("Таблица успешно очищена.");
        } catch (SQLException e) {
            System.out.println("Таблица не очищена.");
        }
    }
}
