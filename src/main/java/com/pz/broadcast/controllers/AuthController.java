package com.pz.broadcast.controllers;

import com.pz.broadcast.dtos.RoleData;
import com.pz.broadcast.dtos.UserData;
import com.pz.broadcast.services.AuthService;
import com.pz.broadcast.utils.UserUtils;
import com.pz.broadcast.validators.RegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserUtils userUtils;
    private final AuthService authService;

    @Autowired
    public AuthController(UserUtils userUtils, AuthService authService) {
        this.userUtils = userUtils;
        this.authService = authService;
    }



    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody UserData user) {
        RegisterValidator registerValidator = new RegisterValidator();
        if (!registerValidator.userValidate(user))
            return null;
        else {
            UserData savedUser = authService.registerUser(user);
            if (savedUser == null)
                return "Registration failed";
        }
        return "Registration successful";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        try {
            response.sendRedirect("/hello");
            return "redirect:/hello";
        } catch (Exception e) {
            return "fail to redirect";
        }
    }

    @RequestMapping(value = "/isLogged", method = RequestMethod.GET)
    public boolean isLogged(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null)
            return false;

        String name = auth.getName();
        return name != null && !name.equals("anonymousUser");

    }

    @RequestMapping(value = "/getRoles", method = RequestMethod.GET)
    public List<RoleData> getRoles(HttpServletRequest request, HttpServletResponse response) {
        return userUtils.getUserRoles();
    }

}
