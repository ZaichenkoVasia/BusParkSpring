package com.spring.controller;

import com.spring.model.domain.User;
import com.spring.model.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public String getSignIn() {
        return "/index";
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/")
    public ModelAndView postSignIn(@RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   HttpSession session,
                                   Model model) {
        User user = userService.findByEmailAndPassword(email, password);
        session.setAttribute("user", user);
        if (Objects.isNull(user)) {
            session.setAttribute("userNotExists", true);
            return new ModelAndView("redirect:/");
        }
        String userType = user.getUserType().getType();
        if (userType.equalsIgnoreCase("admin")) {
            return new ModelAndView("redirect:/bus");
        } else if (userType.equalsIgnoreCase("driver")) {
            return new ModelAndView("redirect:/route");
        }
        model.addAttribute("user", user);
        return new ModelAndView("redirect:/");
    }
}
