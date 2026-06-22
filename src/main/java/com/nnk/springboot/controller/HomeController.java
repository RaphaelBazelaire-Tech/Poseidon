package com.nnk.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Contrôleur des pages d'accueil de l'application.
 */
@Controller
public class HomeController {

    /**
     * Affiche la page d'accueil publique.
     *
     * @param model modèle de la vue
     * @return la vue {@code home}
     */
    @RequestMapping("/")
    public String home(Model model) {
        return "home";
    }

    /**
     * Point d'entrée administrateur : redirige vers la liste des offres.
     *
     * @param model modèle de la vue
     * @return une redirection vers {@code /bidList/list}
     */
    @RequestMapping("/admin/home")
    public String adminHome(Model model) {
        return "redirect:/bidList/list";
    }

}