package com.newshub.core.services;

import com.newshub.core.dao.PrivilegesDAO;
import com.newshub.core.dao.UsersDAO;
import com.newshub.core.domain.Users;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Natalie on 25.04.2015.
 */
public class UsersServices {

    private Session session;
    private UsersDAO usersDAO = new UsersDAO(session);
    private PrivilegesDAO privilegesDAO = new PrivilegesDAO(session);

    public UsersServices (Session session) {
        this.session = session;
    }

    public void addUser(int privilegeId, int id, String login, String password, String email, String firstName, String lastName) {
        Users user = new Users();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPrivilegesByPrivilegeId(privilegesDAO.get(privilegeId));
        usersDAO.create(user);
    }

    public void changeUserPrivileges(int privilegesId, int id) {
        Users user = usersDAO.get(id);
        user.setPrivilegesByPrivilegeId(privilegesDAO.get(privilegesId));
        usersDAO.update(user);
    }

    public void deleteUser(int id) {
        usersDAO.delete(id);
    }

    public void editUserInfo(int id, String login, String password, String email, String firstName, String lastName) {
        Users user = usersDAO.get(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        usersDAO.update(user);
    }

    public Users getUser(int id) {
        return usersDAO.get(id);
    }

    public List<Users> getAllUsers() {
        return usersDAO.getAll();
    }

}
