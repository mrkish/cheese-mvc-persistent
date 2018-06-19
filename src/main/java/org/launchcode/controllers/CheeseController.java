package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("cheese")
public class CheeseController {

    @Autowired
    CheeseDao cheeseDao;

    @Autowired
    CategoryDao categoryDao;

    // Request path: /cheese
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "My Cheeses");

        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {

        model.addAttribute("title", "Add Cheese");
        model.addAttribute(new Cheese());
        model.addAttribute("categories", categoryDao.findAll());

        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute  @Valid Cheese newCheese, @RequestParam int categoryId, Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Cheese");
            model.addAttribute("categories", categoryDao.findAll());
            return "cheese/add";
        }

        Category cat = categoryDao.findOne(categoryId);
        newCheese.setCategory(cat);
        cheeseDao.save(newCheese);

        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "Remove Cheese");

        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] cheeseIds) {

        for (int cheeseId : cheeseIds) {
            cheeseDao.delete(cheeseId);
        }

        return "redirect:";
    }

    @RequestMapping(value = "category/{categoryId}", method = RequestMethod.GET)
    public String category(Model model, @PathVariable int categoryId) {

        Category cat = categoryDao.findOne(categoryId);
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("category", cat);
        model.addAttribute("title", "Cheeses in Category: " + cat.getName());

        return "cheese/index";
    }

    @RequestMapping(value="edit/{id}", method=RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int id) {

        Cheese getThisCheese = cheeseDao.findOne(id);
        model.addAttribute("cheese", getThisCheese);
        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("title", "Editing Cheese: " + getThisCheese.getName());

        return "cheese/edit";
    }

    @RequestMapping(value="edit", method=RequestMethod.POST)
    public String processEditForm(int id, int categoryId, String name, String description) {

        System.out.println(categoryId);
        Cheese c = cheeseDao.findOne(id);
        Category cat = categoryDao.findOne(categoryId);

        c.setName(name);
        c.setDescription(description);
        c.setCategory(cat);
        cheeseDao.save(c);

        return "redirect:/cheese/";
    }
}
