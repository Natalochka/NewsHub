package com.newshub.core.controller;


import com.newshub.core.access_layer.Access;
import com.newshub.core.domain.Articles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by admin on 16.05.2015.
 */

@Controller
@RequestMapping(value = "/controller")
public class correctorController {

    //private Articles articles;

    @RequestMapping(method = RequestMethod.GET)
    //@ResponseBody
    public Articles getArticle(Integer id){
        return  new Access().getArticle(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public void setArticle(int id,String title, String content){ ///????
        new Access().addArticle(id, title, content);
    }


}
