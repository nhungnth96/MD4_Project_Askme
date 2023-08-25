package project.askme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import project.askme.dto.FormEditDto;
import project.askme.model.User;

import project.askme.service.UserService;


import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;



    // ============================== GET edit profile form ==============================
    @GetMapping("/edit/{userId}")
    public ModelAndView edit(@PathVariable("userId") Long userId) {
             User user = userService.findById(userId);
            FormEditDto formEditDto = new FormEditDto();

            formEditDto.setFullName(user.getFullName());
            formEditDto.setAddress(user.getAddress());
            formEditDto.setAbout(user.getAbout());
            return new ModelAndView("user/edit", "editForm", formEditDto);
    }
    // ============================== POST edit profile form ==============================
    @Value("${uploadAvatarPath}")
    private String uploadAvatarPath;
    @PostMapping("/update")
    public String updateProfile(@ModelAttribute("editForm") FormEditDto formEditDto, HttpSession session) {
        User userInSession = (User) session.getAttribute("userIsLogin");

        if (userInSession != null) {
            MultipartFile avatarFile = formEditDto.getAvatar();
            String avatar = avatarFile.getOriginalFilename();
            try {
                FileCopyUtils.copy(avatarFile.getBytes(), new File(uploadAvatarPath + avatar));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            userInSession.setFullName(formEditDto.getFullName());
            userInSession.setAddress(formEditDto.getAddress());
            userInSession.setAvatar(avatar);
            userInSession.setAbout(formEditDto.getAbout());

            userService.save(userInSession);
            session.setAttribute("userIsLogin", userInSession);
        }

        return "redirect:/profile";
    }
}
//        if (user == null) {
//            model.addAttribute("error", "Username or password is incorrect!!!");
//        } else if (!userLogin.isStatus()) {
//            model.addAttribute("error", "Your account has been locked!!!");
//        } else {
//            model.addAttribute("userLogin", userLogin);
//            System.out.println(userLogin);
//            session.setAttribute("userLogin", userLogin);
//            if (userLogin.getRole() == 0) {
//                System.out.println("account đang đăng nhập: " + userLogin);
//                // admin
//                return "admin/dashboard";
//            } else {
//                // user
//                return "index";
//            }
//        }
//        return "index";





//    @PostMapping("/register")
//    public String register(@RequestParam("fullName") String fullName,
//                           @RequestParam("email") String email,
//                           @RequestParam("username") String username,
//                           @RequestParam("password") String password,
//                           Model model) {
//
//        if (userService.checkExistUsername(username)) {
//            model.addAttribute("error", "Username is existed");
//        } else if (userService.validateUsername(username)) {
//            model.addAttribute("error", "Username must be at least 5 characters");
//        }
//
//        if (userService.checkExistEmail(email)) {
//            model.addAttribute("error", "Email is existed");
//        } else if (userService.validateEmail(email)) {
//            model.addAttribute("error", "Invalid email format");
//        }
//        if (!userService.validatePassword(password)) {
//            model.addAttribute("error", "Error: Password must be at least 6 characters, at least 1 uppercase letter, 1 lowercase letter and 1 symbol character");
//        }
//        userService.save(new User(fullName, email, username, password));
//        model.addAttribute("success", "Register successfully! Please login!");
//        return"login";




