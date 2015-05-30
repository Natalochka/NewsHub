package com.newshub.core.controller;

import com.newshub.core.access_layer.Access;
import com.newshub.core.utils.UsersEnum;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Natalie_2 on 5/7/2015.
 */
@Controller
@RequestMapping("/connect")
public class LoginController {

    private Logger logger = Logger.getLogger(LoginController.class);

    @RequestMapping(value = "/user", method = {RequestMethod.POST})
    public String login(ModelMap modelMap, HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        ApplicationContext context = new ClassPathXmlApplicationContext("mvc-dispatcher-servlet.xml");
        Access access = (Access)context.getBean("accessBean");
        modelMap.addAttribute("access", (access.connect(login, password)) ? access : "");
        if (access.getCurrentPrivilege().getName().equals(UsersEnum.ADMIN)) {
            logger.info("Admin login performed successfully in method login() in class LoginController");
            return "admin_page";
        } else if (access.getCurrentPrivilege().getName().equals(UsersEnum.EDITOR)) {
            logger.info("Editor login performed successfully in method login() in class LoginController");
            return "editor_page";
        } else if (access.getCurrentPrivilege().getName().equals(UsersEnum.CORRECTOR)) {
            logger.info("Corrector login performed successfully in method login() in class LoginController");
            return "corrector_page";
        } else if (access.getCurrentPrivilege().getName().equals(UsersEnum.AUTHOR)) {
            logger.info("Author login performed successfully in method login() in class LoginController");
            return "author_page";
        }
        logger.info("Authentication failed");
        return "login_page";
    }
}

