package WEB3D.controller;

import WEB3D.controller.request.ChangePasswordRequest;
import WEB3D.controller.request.LoginRequest;
import WEB3D.controller.request.RegisterRequest;
import WEB3D.security.jwt.JwtTokenUtil;
import WEB3D.service.AuthService;
import WEB3D.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthService authService, UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.authService = authService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        logger.debug("RegistrationForm: " + request.toString());
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        logger.debug("LoginForm: " + request.toString());
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(authService.changePassword(request.getPassword(), request.getRePassword(), request.getToken()));
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> checkToken() {
        logger.debug("Expire user token");
        System.out.println("refresh");
        Map<String, String> result = new HashMap<>();
        result.put("token", "ok");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/center")
    public ResponseEntity<?> center(@RequestParam String token) {
        return ResponseEntity.ok(authService.center(token));
    }

}
