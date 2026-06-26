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

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserRestController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    private UserModel hidePassword(UserModel user) {
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    @GetMapping
    public List<UserModel> getAll() {
        return userService.findAll()
                .stream()
                .map(this::hidePassword)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getById(@PathVariable("id") Integer id) {
        return userService.findById(id)
                .map(this::hidePassword)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
