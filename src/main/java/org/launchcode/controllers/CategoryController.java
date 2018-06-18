package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value="category")
public class CategoryController {

    @Autowired
    CategoryDao categoryDao;

    @RequestMapping(value= "")
    public String index(Model model) {
        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("title", "Categories");

        return "category/index";
    }

    @RequestMapping(value="add", method = RequestMethod.GET)
    public String displayAddCategoryForm(Model model) {

        model.addAttribute("title", "Add Category");
        model.addAttribute("category", new Category());

        return "category/add";
    }

    @RequestMapping(value="add", method = RequestMethod.POST)
    public String processAddCategoryForm(Model model, @ModelAttribute @Valid Category newCategory, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Category");
            return "category/add";
        }

        categoryDao.save(newCategory);
        return "redirect:";
    }

    @RequestMapping(value="view/{categoryId}", method=RequestMethod.GET)
    public String viewCategoryCheeses(Model model, @PathVariable int categoryId) {

        Category currentCategory = categoryDao.findOne(categoryId);
        model.addAttribute("category", currentCategory);
        model.addAttribute("title", "Category: " + currentCategory.getName());
        return "category/view";
    }

}
