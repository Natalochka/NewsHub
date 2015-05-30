package com.newshub.core.services;

import com.newshub.core.dao.PrivilegesDAO;
import com.newshub.core.dao.UsersArticlesDAO;
import com.newshub.core.dao.UsersDAO;
import com.newshub.core.domain.Users;
import com.newshub.core.domain.UsersArticles;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Natalie on 25.04.2015.
 */
public class UsersServices {

    private Session session;
    private UsersDAO usersDAO;
    private PrivilegesDAO privilegesDAO;
    private UsersArticlesDAO usersArticlesDAO;

    private Logger logger = Logger.getLogger(UsersServices.class);

    public UsersServices(Session session) {
        this.session = session;
        usersDAO = new UsersDAO(this.session);
        privilegesDAO = new PrivilegesDAO(this.session);
        usersArticlesDAO = new UsersArticlesDAO(this.session);
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
        logger.info("User added successfully in method addUser() in class UsersServices");
    }

    public void changeUserPrivileges(int privilegesId, int id) {
        Users user = usersDAO.get(id);
        user.setPrivilegesByPrivilegeId(privilegesDAO.get(privilegesId));
        usersDAO.update(user);
        logger.info("User's privileges changed successfully in method changeUserPrivileges() in class UsersServices");
    }

    public void deleteUser(int id) {
        usersDAO.delete(id);
        logger.info("User deleted successfully in method deleteUser() in class UsersServices");
    }

    public void editUserInfo(int id, String login, String password, String email, String firstName, String lastName) {
        Users user = usersDAO.get(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        usersDAO.update(user);
        logger.info("User's info changed successfully in method editUserInfo() in class UsersServices");
    }

    public Users getUser(int id) {
        logger.info("User got successfully in method getUser() in class UsersServices");
        return usersDAO.get(id);
    }

    public List<Users> getAllUsers() {
        logger.info("List of all users got successfully in method getAllUsers() in class UsersServices");
        return usersDAO.getAll();
    }

    public Users getUserByArticleId (int articleId) {
        Users user = null;
        for (UsersArticles usersArticles : usersArticlesDAO.getAll()){
            if (usersArticles.getUsersArticlesPK().getArticleId().getId() == articleId){
                user=usersArticles.getUsersArticlesPK().getUserId();
            }
        }
        logger.info("User got successfully in method getUserByArticleId() in class UsersServices");
        return user;
    }
}
