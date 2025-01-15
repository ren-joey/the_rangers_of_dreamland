package com.trod.controller;

import com.trod.dto.LoginRequestDto;
import com.trod.dto.RegisterRequestDto;
import com.trod.dto.UserResponseDto;
import com.trod.entity.User;
import com.trod.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final HttpServletResponse response;
    private final AuthService authService;

    @GetMapping("/users/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return UserResponseDto.convert(
                authService.getUserById(id)
        );
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return authService.getAllUsers();
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody RegisterRequestDto registerRequestDto) {
        authService.updateUser(id, registerRequestDto);
        return "User updated successfully!";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        authService.deleteUser(id);
        return "User deleted successfully!";
    }

    @PostMapping("/register")
    public UserResponseDto register(@Valid @RequestBody RegisterRequestDto registerRequest) {
        return authService.convertToDto(
                authService.register(registerRequest)
        );
    }

    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginRequestDto loginRequest) {
        authService.login(loginRequest, response);
    }

    @Profile("dev")
    @GetMapping("/checkLogin")
    public Map<String, String> checkLogin () {
        Map<String, String> map = new HashMap<>();
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            map.put(
                    "getName()",
                    SecurityContextHolder.getContext().getAuthentication().getName()
            );
            map.put(
                    "getPrincipal()",
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
            );
            map.put(
                    "getAuthorities()",
                    SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString()
            );
            map.put(
                    "getDetails()",
                    SecurityContextHolder.getContext().getAuthentication().getDetails().toString()
            );
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
        return map;
    }
}
