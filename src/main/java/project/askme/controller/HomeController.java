package project.askme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.askme.dto.FormLoginDto;
import project.askme.dto.FormQuestDto;
import project.askme.dto.FormRegisterDto;
import project.askme.model.Category;
import project.askme.model.User;
import project.askme.service.CategoryService;

import project.askme.service.QuestionService;
import project.askme.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private QuestionService questionService;


    @RequestMapping("/")
    public String goToHome() {
        return "index";
    }

    // ============================== GET REGISTER ==============================
    @GetMapping("/register")
    public ModelAndView register() {

        return new ModelAndView("register", "registerForm", new FormRegisterDto());
    }
    // ============================== POST REGISTER ==============================
    @PostMapping("/register")
    public String register(@ModelAttribute("registerForm") FormRegisterDto formRegisterDto) {
        userService.formToModel(formRegisterDto);
        return "redirect:login";
    }

    // ============================== GET LOGIN ==============================
    @GetMapping("/login")
    public ModelAndView login() {

        return new ModelAndView("login", "loginForm", new FormLoginDto());
    }
    // ============================== POST LOGIN ==============================
    @PostMapping("/login")
    public String login(HttpSession session, @ModelAttribute("loginForm") FormLoginDto formLoginDto) {
        User user = userService.login(formLoginDto);
        session.setAttribute("userIsLogin", user);
        System.out.println("session account: " + user.getUsername());
        if (user.getRoleId() == 1) {
            // admin
            return "redirect:admin/dashboard";
        } else {
            // user
            return "redirect:/";
        }

    }
    // ============================== GET LOGOUT ==============================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userIsLogin");
        return "redirect:/";
    }




    // ============================== GET USER LIST ==============================
    @GetMapping("/admin/users")
    public String showAllUser(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/users";
    }
    // ============================== GET CAT LIST ==============================
    @GetMapping("/admin/categories")
    public String showAllCat(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "admin/categories";
    }

    // ============================== GET USER PROFILE ==============================
    @GetMapping("/profile")
    public String showUserProfile(@SessionAttribute("userIsLogin") User user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
            return "user/profile";
        }
        return "redirect:/login";
    }

}