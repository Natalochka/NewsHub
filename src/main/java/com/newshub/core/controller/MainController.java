package com.newshub.core.controller;

import com.newshub.core.utils.HibernateUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Locale;

/**
 * Created by Natalie on 17.04.2015.
 */
@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public String printWelcome (ModelMap modelMap){
        Locale.setDefault(Locale.ENGLISH);
        HibernateUtils hibernateUtils = new HibernateUtils();

        return "index";
    }
}