package in.adwait.website.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @RequestMapping(value = "/homepage", method = RequestMethod.GET)
    public String homePage() {
        return "home";
    }

    @GetMapping(value = "")
    public String pageHome() {
        return "home";
    }
}
