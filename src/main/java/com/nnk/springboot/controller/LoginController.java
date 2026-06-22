package com.nnk.springboot.controller;

import com.nnk.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôleur gérant les pages liées à la sécurité, préfixées par {@code /app}.
 * <p>
 * Fournit la page de connexion personnalisée, une page de consultation des
 * utilisateurs et la page d'erreur d'accès refusé (403).
 */
@Controller
@RequestMapping("app")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Affiche la page de connexion.
     *
     * @return la vue {@code login}
     */
    @GetMapping("login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    /**
     * Affiche la liste de tous les utilisateurs (zone sécurisée).
     *
     * @return la vue {@code user/list} alimentée avec l'attribut {@code users}
     */
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    /**
     * Affiche la page d'erreur d'accès refusé.
     *
     * @return la vue {@code 403} alimentée avec l'attribut {@code errorMsg}
     */
    @GetMapping("error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }
}