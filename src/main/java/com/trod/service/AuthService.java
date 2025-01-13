package com.trod.service;

import com.trod.constant.RoleEnum;
import com.trod.dto.LoginRequestDto;
import com.trod.dto.RegisterRequestDto;
import com.trod.dto.UserResponseDto;
import com.trod.entity.GameRole;
import com.trod.entity.User;
import com.trod.mapper.UserMapper;
import com.trod.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User register(RegisterRequestDto registerRequest) {
        if (userMapper.findByEmail(registerRequest.email()) != null) {
            throw new RuntimeException("Email already exists");
        }

        if (registerRequest.role().getIndex() > 0) {
            checkPermission(RoleEnum.fromIndex(registerRequest.role().getIndex() + 1));
        }

        User user = new User();
        user.setUsername(registerRequest.username());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setEmail(registerRequest.email());
        GameRole gameRole = new GameRole();
        gameRole.setRoleEnum(registerRequest.role());
        gameRole.setUser(user);
        user.setGameRole(gameRole);
        userMapper.insert(user);

        return user;
    }

    public void login(LoginRequestDto loginRequest, HttpServletResponse response) {
        User user = userMapper.findByUsername(loginRequest.username());
        if (user == null) throw new RuntimeException("User not found");
        if (passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());
            addJwtToCookie(response, token);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public void addJwtToCookie(HttpServletResponse response, String jwt) {
        Cookie cookie = new Cookie(System.getProperty("JWT_KEY_NAME"), jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
    }

    public UserResponseDto convertToDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getGameRole().getRoleEnum()
        );
    }

    public User checkPermission(RoleEnum requiredRole) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new InsufficientAuthenticationException("User not authenticated");
        }
        var user = userMapper.findByUsername(authentication.getPrincipal().toString());
        if (user.getGameRole().getRoleEnum().getIndex() < requiredRole.getIndex()) {
            throw new InsufficientAuthenticationException("User not authorized");
        }
        return user;
    }
}
