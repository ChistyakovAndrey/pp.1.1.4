package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws SQLException {

        UserServiceImpl us = new UserServiceImpl();
        us.setAutoCommitFalse();

        // Создание базы
        us.createUsersTable();

        us.toStartTransaction();

        // Добавление users
        List<User> users = new ArrayList<>();
        users.add(new User("Andrey", "Chistyakov", (byte) 44));
        users.add(new User("Georgiy", "Romanov", (byte) 53));
        users.add(new User("John", "Doe", (byte) 127));
        users.add(new User("John", "Connor", (byte) 36));
        for (User user : users) {
            us.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.println("User с именем " + user.getName() + " добавлен в базу данных");
        }

        // Достаем всех users
        List<User> list = us.getAllUsers();
        for (User user : list) {
            System.out.println(user);
        }

        if (UserServiceImpl.itGoesFine) {
            us.toCommit();
        } else {
            us.toRollback();
        }

        // Truncate & drop
        us.cleanUsersTable();
        us.dropUsersTable();

        //System.out.println("itGoesFine: " + UserServiceImpl.itGoesFine);
    }
}
