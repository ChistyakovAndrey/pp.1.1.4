package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Statement statement;

    {
        try {
            Connection connection = Util.getMySQLConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createUserTable = "create table if not exists USER(ID integer not null AUTO_INCREMENT, USER_NAME varchar(50) not null," +
                "USER_LASTNAME varchar(50) not null, USER_AGE integer not null, primary key (ID))";
        try {
            statement.executeUpdate(createUserTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String dropTableUser = "drop table if exists user";
        try {
            statement.executeUpdate(dropTableUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUser = "insert into user(USER_NAME, USER_LASTNAME, USER_AGE) values('" + name + "', '"
                + lastName + "', " + age + ")";
        try {
            statement.executeUpdate(saveUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String removeUser = "delete from user where id = " + id;
        try {
            statement.executeUpdate(removeUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        String getAllFromUser = "select * from USER";
        try {
            ResultSet rs = statement.executeQuery(getAllFromUser);
            while (rs.next()) {
                User user = new User(
                        rs.getString("USER_NAME"),
                        rs.getString("USER_LASTNAME"),
                        Byte.parseByte(rs.getString("USER_AGE"))
                );
                usersList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("SQLException");
        }
        return usersList;
    }

    public void cleanUsersTable() {
        String truncateTableUser = "truncate table user";
        try {
            statement.executeUpdate(truncateTableUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
