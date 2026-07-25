package com.RideOrDie.Ride_Or_Die;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/borruhak")
    public String borruhak() {
        return "borruhak";
    }

    @GetMapping("/bukosisakok")
    public String bukosisakok() {
        return "bukosisakok";
    }

    @GetMapping("/csizmak-cipok")
    public String csizmak() {
        return "csizmak-cipok";
    }

    @GetMapping("/kabatok")
    public String kabatok() {
        return "kabatok";
    }

    @GetMapping("/kesztyuk")
    public String kesztyuk() {
        return "kesztyuk";
    }

    @GetMapping("/nadragok")
    public String nadragok() {
        return "nadragok";
    }

    @GetMapping("/kosar")
    public String cart() { return "kosar"; }

}

