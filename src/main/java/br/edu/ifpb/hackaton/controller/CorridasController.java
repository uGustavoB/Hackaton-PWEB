package br.edu.ifpb.hackaton.controller;

import br.edu.ifpb.hackaton.model.Corrida;
import br.edu.ifpb.hackaton.model.Pergunta;
import br.edu.ifpb.hackaton.repositories.CorridaRepository;
import br.edu.ifpb.hackaton.services.CorridaService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/corridas")
@RequiredArgsConstructor
public class CorridasController {
    private final CorridaRepository corridaRepository;
    private final CorridaService corridaService;

//  Usuário normal
    @GetMapping()
    public String showCorridas(Model model) {
        model.addAttribute("listaCorridas", corridaRepository.findAll());
        return "corridas/corrida";
    }

//  Admin
    @GetMapping("/visualizar")
    public String visualizarCorridas(Model model) {
        model.addAttribute("corrida", new Corrida());
        model.addAttribute("listaCorridas", corridaRepository.findAll());
        model.addAttribute("listaCorridasAtivas", corridaService.getCorridasAtivas());
        return "corridas/gerenciar-corrida";
    }

    @PostMapping("/salvar")
    public String salvarCorrida(@ModelAttribute("corrida") Corrida corrida) {
        Corrida corridaCriada = corridaRepository.save(corrida);
        return "redirect:/corridas/visualizar";
    }

    @GetMapping("/editar")
    public String editarCorrida(@RequestParam(name = "id") int id, Model model) {
        model.addAttribute("corrida", corridaRepository.findById(id).orElse(new Corrida()));
        model.addAttribute("listaCorridas", corridaRepository.findAll());
        model.addAttribute("listaCorridasAtivas", corridaService.getCorridasAtivas());
        return "corridas/gerenciar-corrida";
    }

    @PostMapping("/deletar")
    public String deletarCorrida(@RequestParam(name = "id") int id) {
        corridaRepository.deleteById(id);
        return "redirect:/corridas/visualizar";
    }

    @PostMapping("/iniciar")
    public String iniciarCorrida(@RequestParam int id, HttpSession session) {

        session.setAttribute("corridaAtualId", id);
        session.setAttribute("perguntaIndex", 0);
        session.setAttribute("inicioCorrida", System.currentTimeMillis());
        session.setAttribute("pontos", 0);

        return "redirect:/corridas/pergunta";
    }

    @GetMapping("/pergunta")
    public String mostrarPergunta(HttpSession session, Model model) {

        Integer corridaId = (Integer) session.getAttribute("corridaAtualId");
        Integer index = (Integer) session.getAttribute("perguntaIndex");

        if (corridaId == null) return "redirect:/corridas";

        Corrida corrida = corridaRepository.findByIdWithPerguntas(corridaId)
                .orElseThrow();

        if (index >= corrida.getPerguntas().size()) {
            return "redirect:/corridas/finalizar";
        }

        Pergunta perguntaAtual = corrida.getPerguntas().get(index);

        long inicio = (long) session.getAttribute("inicioCorrida");
        long tempoRestante = corrida.getTempoSegundos() - (System.currentTimeMillis() - inicio);

        if (tempoRestante <= 0) {
            return "redirect:/corridas/finalizar";
        }

        model.addAttribute("pergunta", perguntaAtual);
        model.addAttribute("tempoRestante", tempoRestante / 1000);

        return "corridas/pergunta";
    }

    @PostMapping("/responder")
    public String responder(
            @RequestParam int perguntaId,
            @RequestParam String resposta,
            HttpSession session) {

        Integer corridaId = (Integer) session.getAttribute("corridaAtualId");
        Integer index = (Integer) session.getAttribute("perguntaIndex");

        Corrida corrida = corridaRepository.findByIdWithPerguntas(corridaId)
                .orElseThrow();

        Pergunta pergunta = corrida.getPerguntas().get(index);

        if (pergunta.getRespostaCorreta().equals(resposta)) {
            Integer pontos = (Integer) session.getAttribute("pontos");
            session.setAttribute("pontos", pontos + 1);
        }

        session.setAttribute("perguntaIndex", index + 1);

        return "redirect:/corridas/pergunta";
    }

    @GetMapping("/finalizar")
    public String finalizar(HttpSession session, Model model) {

        Integer corridaId = (Integer) session.getAttribute("corridaAtualId");
        Corrida corrida = corridaRepository.findByIdWithPerguntas(corridaId).orElse(null);

        Integer pontos = (Integer) session.getAttribute("pontos");
        if (pontos == null) pontos = 0;

        model.addAttribute("pontuacao", pontos);
        model.addAttribute("corrida", corrida);

        session.removeAttribute("pontos");
        session.removeAttribute("corridaAtualId");
        session.removeAttribute("perguntaIndex");
        session.removeAttribute("inicioCorrida");

        return "corridas/resultado";
    }


}
