package com.trod.controller;

import com.trod.dto.LoginRequestDto;
import com.trod.dto.RegisterRequestDto;
import com.trod.dto.UserResponseDto;
import com.trod.sevice.AuthService;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final HttpServletResponse response;

    public AuthController(
            HttpServletResponse response,
            AuthService authService
    ) {
        this.authService = authService;
        this.response = response;
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

    @GetMapping("/checkLogin")
    public Map<String, String> checkLogin () throws AuthException, IOException {
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
