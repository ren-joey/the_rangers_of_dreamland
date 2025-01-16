package com.trod.service;

import com.trod.constant.RoleEnum;
import com.trod.dto.LoginRequestDto;
import com.trod.dto.RegisterRequestDto;
import com.trod.dto.UserResponseDto;
import com.trod.entity.GameRole;
import com.trod.entity.User;
import com.trod.mapper.GameRoleMapper;
import com.trod.mapper.UserMapper;
import com.trod.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final GameRoleMapper gameRoleMapper;

    public User getUserById(Long id) {
        return userMapper.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    public void updateUser(Long id, RegisterRequestDto registerRequestDto) {
        User user = getUserById(id);
        if (user == null)
            throw new RuntimeException("User not found");
        if (registerRequestDto.username() != null)
            user.setUsername(registerRequestDto.username());
        if (registerRequestDto.password() != null)
            user.setPassword(passwordEncoder.encode(registerRequestDto.password()));
        if (registerRequestDto.email() != null)
            user.setEmail(registerRequestDto.email());
        userMapper.update(user);

        if (registerRequestDto.role() != null) {
            GameRole gameRole = user.getGameRole();
            gameRole.setRole(registerRequestDto.role());
            gameRoleMapper.update(gameRole);
        }
    }

    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }

    @Transactional
    public User register(RegisterRequestDto registerRequest) {
        if (userMapper.findByEmail(registerRequest.email()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (registerRequest.role().getIndex() > 0) {
            checkPermission(RoleEnum.fromIndex(registerRequest.role().getIndex() + 1));
        }

        User user = new User();
        user.setUsername(registerRequest.username());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setEmail(registerRequest.email());
        userMapper.insert(user);

        GameRole gameRole = new GameRole();
        gameRole.setRole(registerRequest.role());
        gameRole.setUser(user);
        user.setGameRole(gameRole);
        gameRoleMapper.insert(gameRole);

        return user;
    }

    public void login(LoginRequestDto loginRequest, HttpServletResponse response) {
        User user = userMapper.findByUsername(loginRequest.username())
                .orElseThrow(() -> new RuntimeException("User not found"));
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
                user.getGameRole().getRole()
        );
    }

    public User checkPermission(RoleEnum requiredRole) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new InsufficientAuthenticationException("User not authenticated");
        }
        var user = userMapper.findByUsername(authentication.getPrincipal().toString())
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("User not found"));
        if (user.getGameRole().getRole().getIndex() < requiredRole.getIndex()) {
            throw new InsufficientAuthenticationException("User not authorized");
        }
        return user;
    }
}
