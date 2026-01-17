package com.springweb.springWeb.controllers.Web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cliente")
public class ClienteSesionControllerWeb {

    @GetMapping("/login")
    public String login(HttpSession session){
        session.setAttribute("userName", "admin");
        session.setAttribute("password", "admin");
        session.setAttribute("role", "ROLE_ADMIN");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/cliente/info";
    }

    @GetMapping("/idioma")
    public String cambiarIdioma(@RequestParam String idioma, HttpServletResponse response) {
        Cookie cookie = new Cookie("idioma", idioma);
        cookie.setMaxAge(60 * 60 * 24 * 7); // 7 dias
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/cliente/info";
    }
}
