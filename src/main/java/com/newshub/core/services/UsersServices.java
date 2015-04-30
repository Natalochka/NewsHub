package com.newshub.core.services;

import com.newshub.core.dao.PrivilegesDAO;
import com.newshub.core.dao.UsersDAO;
import com.newshub.core.domain.Users;

/**
 * Created by Natalie on 25.04.2015.
 */
public class UsersServices {

    UsersDAO usersDAO = new UsersDAO();
    PrivilegesDAO privilegesDAO = new PrivilegesDAO();

    public void addUser(int privilegeId, int id, String login, String password, String email, String firstName, String lastName) {
        Users user = new Users();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPrivilegeId(privilegeId);
        usersDAO.create(user);
    }

    public void changeUserPrivileges(int privilegesId, int id) {
        Users user = usersDAO.get(id);
        user.setPrivilegeId(privilegesId);
        usersDAO.update(user);
    }

    public void deleteUser(int id) {
        usersDAO.delete(id);
    }

    public void editUserInfo(int id, String login, String password, String email, String firstName, String lastName) {
        Users user = usersDAO.get(id);
        user.setId(id);
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

}
