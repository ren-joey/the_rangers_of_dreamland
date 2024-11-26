package com.trod.sevice;

import com.trod.dto.LoginRequestDto;
import com.trod.dto.RegisterRequestDto;
import com.trod.dto.UserResponseDto;
import com.trod.entity.User;
import com.trod.mapper.UserMapper;
import com.trod.security.JwtUtil;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private static final Dotenv dotenv = Dotenv.load();

    public AuthService(UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User register(RegisterRequestDto registerRequest) {
        if (userMapper.findByEmail(registerRequest.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        userMapper.insert(user);

        return user;
    }

    public void login(LoginRequestDto loginRequest, HttpServletResponse response) {
        User user = userMapper.findByUsername(loginRequest.getUsername());
        if (user == null) throw new RuntimeException("User not found");
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());
            addJwtToCookie(response, token);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public void addJwtToCookie(HttpServletResponse response, String jwt) {
        Cookie cookie = new Cookie(dotenv.get("JWT_KEY_NAME"), jwt);
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
                user.getEmail()
        );
    }
}
