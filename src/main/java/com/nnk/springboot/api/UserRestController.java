package com.nnk.springboot.api;

import com.nnk.springboot.model.UserModel;
import com.nnk.springboot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * API REST/JSON exposant le CRUD des utilisateurs ({@link UserModel}).
 * <p>
 * Contrairement au {@link UserController} (orienté vues Thymeleaf), ce contrôleur
 * renvoie des représentations JSON et utilise les verbes HTTP standard. Toutes les
 * routes sont préfixées par {@code /api/user}. La conversion entité/Model reste
 * assurée par le {@link UserService}.
 * <p>
 * Deux précautions propres aux utilisateurs : le mot de passe est encodé en BCrypt
 * via le {@link BCryptPasswordEncoder} avant chaque enregistrement, et le hash n'est
 * jamais renvoyé dans les réponses (il est masqué avant sérialisation).
 */
@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructeur par défaut.
     *
     * @param userService service métier des utilisateurs, injecté par Spring
     * @param passwordEncoder encodeur BCrypt pour le hachage des mots de passe
     */
    public UserRestController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Masque le mot de passe (hash) d'un utilisateur avant de le renvoyer au client.
     *
     * @param user l'utilisateur à assainir (peut être {@code null})
     * @return le même utilisateur, mot de passe vidé
     */
    private UserModel hidePassword(UserModel user) {
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    /**
     * Récupère tous les utilisateurs (sans leur mot de passe).
     *
     * @return la liste des utilisateurs au format JSON (statut 200)
     */
    @GetMapping
    public List<UserModel> getAll() {
        return userService.findAll()
                .stream()
                .map(this::hidePassword)
                .toList();
    }

    /**
     * Récupère un utilisateur par son identifiant (sans son mot de passe).
     *
     * @param id identifiant de l'utilisateur
     * @return l'utilisateur (statut 200) ou un statut 404 s'il n'existe pas
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getById(@PathVariable("id") Integer id) {
        return userService.findById(id)
                .map(this::hidePassword)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crée un nouvel utilisateur, mot de passe encodé en BCrypt avant persistance.
     *
     * @param user l'utilisateur à créer, désérialisé depuis le corps JSON et validé
     * @return l'utilisateur créé sans son mot de passe (statut 201) avec l'en-tête
     * {@code Location} vers la ressource
     */
    @PostMapping
    public ResponseEntity<UserModel> create(@Valid @RequestBody UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserModel saved = userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(hidePassword(saved));
    }

    /**
     * Met à jour un utilisateur existant, mot de passe ré-encodé en BCrypt.
     *
     * @param id identifiant de l'utilisateur à mettre à jour
     * @param user les nouvelles données, désérialisées depuis le corps JSON et validées
     * @return l'utilisateur mis à jour sans son mot de passe (statut 200), ou un
     * statut 404 s'il n'existe pas
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> update(@PathVariable("id") Integer id,
                                            @Valid @RequestBody UserModel user) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(id);
        return ResponseEntity.ok(hidePassword(userService.save(user)));
    }

    /**
     * Supprime un utilisateur.
     *
     * @param id identifiant de l'utilisateur à supprimer
     * @return un statut 204 (No Content) en cas de succès, ou 404 s'il n'existe pas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
