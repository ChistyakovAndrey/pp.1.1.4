package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserDaoHibernateImpl implements UserDao {
    Session session = Util.getSession();


    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSession();) {
            session.beginTransaction();
            String createUserTable = "create table if not exists User(ID integer not null AUTO_INCREMENT, USER_NAME varchar(50) not null," +
                    "USER_LASTNAME varchar(50) not null, USER_AGE integer not null, primary key (ID))";
            try {
                new UserDaoJDBCImpl().statement.executeUpdate(createUserTable);
                session.getTransaction().commit();
            } catch (SQLException e) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSession();) {
            session.beginTransaction();
            String dropTableUser = "drop table if exists User";
            try {
                new UserDaoJDBCImpl().statement.executeUpdate(dropTableUser);
                session.getTransaction().commit();
            } catch (SQLException e) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSession();) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            try {
                session.save(user);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSession();) {
            session.beginTransaction();
            try {
                User user = session.get(User.class, id);
                session.delete(user);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSession()) {
            List list;
            list = session.createQuery("from User").getResultList();
            return list;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getSession()){
            session.beginTransaction();
            try{
                session.createQuery("delete from User").executeUpdate();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
