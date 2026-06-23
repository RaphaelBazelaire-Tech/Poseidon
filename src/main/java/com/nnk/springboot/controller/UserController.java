package com.nnk.springboot.controller;

import com.nnk.springboot.model.UserModel;
import com.nnk.springboot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur web gérant le CRUD des utilisateurs ({@link UserModel}).
 * <p>
 * Particularité : le mot de passe est encodé en BCrypt via le
 * {@link BCryptPasswordEncoder} avant chaque enregistrement.
 * Les vues associées se trouvent dans {@code templates/user/}.
 * L'accès à ces routes est réservé au rôle ADMIN par la configuration de sécurité.
 */
@Controller
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructeur par défaut.
     *
     * @param userService service métier des utilisateurs, injecté par Spring
     * @param passwordEncoder encodeur BCrypt pour le hachage des mots de passe
     */
    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Affiche la liste de tous les utilisateurs.
     *
     * @param model modèle de la vue, alimenté avec l'attribut {@code users}
     * @return la vue {@code user/list}
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    /**
     * Affiche le formulaire de création d'un utilisateur.
     *
     * @param user objet vide lié au formulaire (attribut {@code user})
     * @return la vue {@code user/add}
     */
    @GetMapping("/user/add")
    public String addForm(@ModelAttribute("user") UserModel user) {
        return "user/add";
    }

    /**
     * Valide puis enregistre un nouvel utilisateur, mot de passe encodé en BCrypt.
     *
     * @param user données soumises par le formulaire, validées via {@code @Valid}
     * @param result résultat de la validation
     * @param model modèle de la vue
     * @return une redirection vers {@code /user/list} en cas de succès,
     * sinon la vue {@code user/add}
     */
    @PostMapping("/user/validate")
    public String validate(@Valid @ModelAttribute("user") UserModel user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
            return "redirect:/user/list";
        }
        return "user/add";
    }

    /**
     * Affiche le formulaire de modification d'un utilisateur.
     * <p>
     * Le mot de passe est volontairement vidé avant l'affichage pour ne pas
     * exposer le hash et imposer une nouvelle saisie.
     *
     * @param id identifiant de l'utilisateur à modifier
     * @param model modèle de la vue, alimenté avec l'attribut {@code user}
     * @return la vue {@code user/update}
     * @throws IllegalArgumentException si aucun utilisateur ne correspond à l'identifiant
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        UserModel user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * Valide puis met à jour un utilisateur, mot de passe ré-encodé en BCrypt.
     *
     * @param id identifiant de l'utilisateur à mettre à jour
     * @param user données soumises par le formulaire, validées via {@code @Valid}
     * @param result résultat de la validation
     * @param model modèle de la vue
     * @return une redirection vers {@code /user/list} en cas de succès,
     * sinon la vue {@code user/update}
     */
    @PostMapping("/user/update/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("user") UserModel user,
                         BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "user/update";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(id);
        userService.save(user);
        return "redirect:/user/list";
    }

    /**
     * Supprime un utilisateur.
     *
     * @param id identifiant de l'utilisateur à supprimer
     * @param model modèle de la vue
     * @return une redirection vers {@code /user/list}
     * @throws IllegalArgumentException si aucun utilisateur ne correspond à l'identifiant
     */
    @GetMapping("/user/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userService.deleteById(id);
        return "redirect:/user/list";
    }
}
