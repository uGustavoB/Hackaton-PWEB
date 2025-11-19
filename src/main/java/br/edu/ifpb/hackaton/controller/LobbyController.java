package br.edu.ifpb.hackaton.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lobby")
@RequiredArgsConstructor
public class LobbyController {

    @GetMapping()
    public String lobby(HttpSession session, Model model) {
        String usuario = (String) session.getAttribute("usuario");
        model.addAttribute("nomeUsuario", usuario);
        return "lobby";
    }
}
