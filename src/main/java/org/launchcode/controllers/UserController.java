package org.launchcode.controllers;

import org.launchcode.models.User;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.data.forms.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    CheeseDao cheeseDao;

    @Autowired
    MenuDao menuDao;

    @Autowired
    UserDao userDao;

    @RequestMapping(value = "")
    public String login(Model model) {

        model.addAttribute("title", "Login");

        return "user/login";
    }

    @RequestMapping(value = "register", method= RequestMethod.GET)
    public String displayRegisterForm(Model model) {

        model.addAttribute("title", "Register New User");
        model.addAttribute(new User());

        return "user/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String processRegisterFrom(Model model, @ModelAttribute  @Valid User user, Errors errors ) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Register New User");

            return "user/login";
        }

        userDao.save(user);

        return "redirect:cheese/";
    }
}
