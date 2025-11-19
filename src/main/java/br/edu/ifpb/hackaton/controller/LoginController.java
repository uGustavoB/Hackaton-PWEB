package br.edu.ifpb.hackaton.controller;

import br.edu.ifpb.hackaton.model.Usuario;
import br.edu.ifpb.hackaton.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final UsuarioRepository usuarioRepository;

    @GetMapping()
    public String showLoginPage() {
        return "login/login";
    }

    @PostMapping()
    public String showLoginPage(
            @RequestParam String nome,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (nome == null || nome.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "O nome do jogador não pode ser vazio.");
            return "redirect:/";
        }

        Usuario usuario = usuarioRepository.getByNome(nome);

        session.setAttribute("usuario", usuario.getNome());

        return "redirect:/lobby";
    }

    @GetMapping("/sair")
    public String sair(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("logout", "Você saiu do sistema.");
        return "redirect:/login";
    }
}
