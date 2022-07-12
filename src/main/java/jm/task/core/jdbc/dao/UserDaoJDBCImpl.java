package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Statement statement;
    Connection connection;

    {
        try {
            connection = Util.getMySQLConnection();
            statement = connection.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setAutoCommitFalse() throws SQLException {
        connection.setAutoCommit(false);
    }

    public UserDaoJDBCImpl() {

    }

    public void toStartTransaction() {
        try {
            statement.execute("START TRANSACTION");
        } catch (SQLException ignored) {

        }
    }

    public void toCommit() {
        try {
            statement.execute("COMMIT");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void toRollback() {
        try {
            statement.execute("ROLLBACK");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {
        toStartTransaction();
        String createUserTable = "create table if not exists USER(ID integer not null AUTO_INCREMENT, USER_NAME varchar(50) not null," +
                "USER_LASTNAME varchar(50) not null, USER_AGE integer not null, primary key (ID))";
        try {
            statement.executeUpdate(createUserTable);
            toCommit();
        } catch (SQLException e) {
            toRollback();
        }
    }

    public void dropUsersTable() {
        toStartTransaction();
        String dropTableUser = "drop table if exists user";
        try {
            statement.executeUpdate(dropTableUser);
            toCommit();
        } catch (SQLException e) {
            toRollback();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        toStartTransaction();
        String saveUser = "insert into user(USER_NAME, USER_LASTNAME, USER_AGE) values('" + name + "', '"
                + lastName + "', " + age + ")";
        try {
            statement.executeUpdate(saveUser);
            toCommit();
        } catch (SQLException e) {
            toRollback();
        }
    }

    public void removeUserById(long id) {
        toStartTransaction();
        try {
            statement.executeUpdate("delete from user where id = " + id);
            toCommit();
        } catch (SQLException e) {
            toRollback();
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
        } catch (SQLException ignored) {
        }
        return usersList;
    }

    public void cleanUsersTable() {
        toStartTransaction();
        String truncateTableUser = "truncate table user";
        try {
            statement.executeUpdate(truncateTableUser);
            toCommit();
        } catch (SQLException e) {
            toRollback();
        }
    }
}
