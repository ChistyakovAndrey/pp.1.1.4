package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl userDao = new UserDaoHibernateImpl();

        // Создание базы
        userDao.createUsersTable();

        // Добавление users
        List<User> users = new ArrayList<>();
        users.add(new User("Andrey", "Chistyakov", (byte) 44));
        users.add(new User("Georgiy", "Romanov", (byte) 53));
        users.add(new User("John", "Doe", (byte) 127));
        users.add(new User("John", "Connor", (byte) 36));
        for (User user : users) {
            userDao.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.println("User с именем " + user.getName() + " добавлен в базу данных");
        }

        // Достаем всех users
        List<User> list = userDao.getAllUsers();
        for (User user : list) {
            System.out.println(user);
        }

        // Truncate & drop
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
