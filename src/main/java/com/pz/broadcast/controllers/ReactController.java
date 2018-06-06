package com.pz.broadcast.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReactController {

    @GetMapping(value = {
            "/{path:(?!.*.js|.*.css|.*.jpg).*$}",
            "/users/**{path:(?!.*.js|.*.css|.*.jpg).*$}",
            "/login/error"
    })
    public String react() {
        return "index";
    }

}
