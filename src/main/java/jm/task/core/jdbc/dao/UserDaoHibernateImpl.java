package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;
    private Transaction transaction;

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        session = Util.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.createNativeQuery("CREATE TABLE IF NOT EXISTS users(" +
                "id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(50) NOT NULL, " +
                "lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT)",User.class).executeUpdate();
        transaction.commit();
        session.close();
        System.out.println("Таблица успешно создана.");
    }


    @Override
    public void dropUsersTable() {
        session = Util.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.createNativeQuery("drop table if exists users",User.class).executeUpdate();
        transaction.commit();
        session.close();
        System.out.println("Таблица удалена.");
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        session = Util.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.persist(new User(name, lastName, age));
        transaction.commit();
        session.close();
        System.out.println("Пользователь добавлен в таблицу.");
    }

    @Override
    public void removeUserById(long id) {
        session = Util.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.delete(session.get(User.class, id));
        transaction.commit();
        session.close();
        System.out.println("Пользователь удален из таблицы.");
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list;
        session = Util.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        list = session.createNativeQuery("FROM User ").list();
        transaction.commit();
        session.close();
        System.out.println(list);
        return list;
    }

    @Override
    public void cleanUsersTable() {
        session = Util.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE users");
        transaction.commit();
        session.close();
        System.out.println("Таблица очищена.");
    }
}
