package org.launchcode.controllers;

import org.launchcode.models.User;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.data.forms.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
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
        model.addAttribute(new User());

        return "user/login";
    }

    @RequestMapping(value = "register", method= RequestMethod.GET)
    public String displayRegisterForm(Model model) {

        model.addAttribute("title", "Register New User");
        model.addAttribute("user", new User());

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

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayUserLogin(Model model) {

        model.addAttribute("title", "Login");

        return "user/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processUserLogin(Model model, @ModelAttribute User user, Errors errors) {

        if(errors.hasErrors()) {
            model.addAttribute("title", "Login");

            return "redirect:login";
        }

        // TODO: Add HTTPResponseServlet stuff here to set cookies/store a user session.

        return "cheese/index";

    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public String processUserLogout(Model model, @CookieValue User user) {

        // TODO: Remove user cookies from session to handle logout functionality.

        return "user/login";
    }
}
