package com.newshub.core.controller;

import com.newshub.core.access_layer.Access;
import com.newshub.core.utils.Tabs;
import com.newshub.core.utils.UsersEnum;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Natalie_2 on 5/7/2015.
 */
@Controller
@SessionAttributes("accessAtribute")
@RequestMapping("/connect")
public class LoginController {

    private Logger logger = Logger.getLogger(LoginController.class);

    @RequestMapping(method = {RequestMethod.GET})
    public String showLoginPage(ModelMap modelMap) {
        logger.info("Login page shown successfully in class LoginController");
        modelMap.addAttribute("current_nav", "login");
        return "login_31";
    }

    @RequestMapping(value = "/user", method = {RequestMethod.POST})
    public String login(ModelMap modelMap, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Access access = new Access();
        access.connect(login, password);
        Tabs tabs = new Tabs();
        if (access.getCurrentPrivilege().getName().equals(UsersEnum.ADMIN.getName())) {
            tabs.setPublished(true);
            tabs.setRejected(true);
            tabs.setBeing_processed_by_corrector(true);
            tabs.setBeing_processed_by_editor(true);
            tabs.setDrafts(true);
            tabs.setChecked_by_corrector(true);
            access.setTabs(tabs);
            redirectAttributes.addFlashAttribute("access", access);
            logger.info("Admin login performed successfully in method login() in class LoginController");
            return "redirect:/admin";
        } else if (access.getCurrentPrivilege().getName().equals(UsersEnum.EDITOR.getName())) {
            tabs.setDrafts(true);
            tabs.setBeing_processed_by_editor(true);
            tabs.setChecked_by_corrector(true);
            tabs.setPublished(true);
            access.setTabs(tabs);
            redirectAttributes.addFlashAttribute("access", access);
            logger.info("Editor login performed successfully in method login() in class LoginController");
            return "redirect:/editor";
        } else if (access.getCurrentPrivilege().getName().equals(UsersEnum.CORRECTOR.getName())) {
            tabs.setBeing_processed_by_corrector(true);
            tabs.setChecked_by_corrector(true);
            access.setTabs(tabs);
            redirectAttributes.addFlashAttribute("access", access);
            logger.info("Corrector login performed successfully in method login() in class LoginController");
            return "redirect:/corrector";
        } else if (access.getCurrentPrivilege().getName().equals(UsersEnum.AUTHOR.getName())) {
            tabs.setDrafts(true);
            tabs.setBeing_processed_by_corrector(true);
            tabs.setBeing_processed_by_editor(true);
            tabs.setRejected(true);
            tabs.setPublished(true);
            access.setTabs(tabs);
            redirectAttributes.addFlashAttribute("access", access);
            logger.info("Author login performed successfully in method login() in class LoginController");
            return "redirect:/author";
        }
        logger.info("Authentication failed");
        return "redirect:/";
    }

}

