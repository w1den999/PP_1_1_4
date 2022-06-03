package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()){
            Statement statement = connection
                    .createStatement();
            statement.executeUpdate("CREATE TABLE if NOT EXISTS USER ("
                    + "   id INT NOT NULL AUTO_INCREMENT, name VARCHAR(30) NOT NULL, lastname VARCHAR(50) NOT NULL, "
                    + "   age INT, PRIMARY KEY (id) ); ");
            System.out.println("Таблица создана.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()){
            Statement statement = connection
                    .createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS USER");
            System.out.println("Таблица удалена.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()){
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO USER(name, lastName, age) VALUES(?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM USER WHERE id=?");
            preparedStatement.setLong (1, id);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь по id = " + id + " удален из таблицы.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, name, lastName, age FROM USER");

            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()){
            Statement statement =
                    connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE USER");
            System.out.println("Таблица успешно очищена.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
