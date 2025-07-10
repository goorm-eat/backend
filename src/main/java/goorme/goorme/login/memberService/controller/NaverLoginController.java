package goorme.goorme.login.memberService.controller;

import goorme.goorme.login.memberService.dto.LoginResponseDto;
import goorme.goorme.login.memberService.oauth.OAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/naver")
public class NaverLoginController {

    private final OAuthService oAuthService;
    @GetMapping("/login")
    public void redirectToNaver(HttpServletResponse response) throws IOException {
        oAuthService.redirectToNaver(response);
    }
    @GetMapping("/callback")
    public ResponseEntity<LoginResponseDto> handleNaverCallback(
            @RequestParam String code,
            @RequestParam String state) {
        LoginResponseDto result = oAuthService.processNaverLogin(code, state);
        return ResponseEntity.ok(result);
    }
}
