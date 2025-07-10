package goorme.goorme.login.memberService.controller;

import goorme.goorme.domain.GlobalCounter;
import goorme.goorme.login.memberService.domain.Member;
import goorme.goorme.login.memberService.dto.LoginResponseDto;
import goorme.goorme.login.memberService.oauth.OAuthService;
import goorme.goorme.login.memberService.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Result;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/naver")
public class NaverLoginController {

    private final MemberRepository memberRepository;
    private final OAuthService oAuthService;
    GlobalCounter test=new GlobalCounter();
    @GetMapping("/login")
    //public void redirectToNaver(HttpServletResponse response) throws IOException {
    //    oAuthService.redirectToNaver(response);
    //}

    public ResponseEntity<LoginResponseDto> login() {
        test.increment();
        Member member= (Member) memberRepository.findByCnt(test.getValue());
        LoginResponseDto result = new LoginResponseDto(member.getToken(), member.getNickname());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/callback")
    public ResponseEntity<LoginResponseDto> handleNaverCallback(
            @RequestParam String code,
            @RequestParam String state) {
        LoginResponseDto result = oAuthService.processNaverLogin(code, state);
        return ResponseEntity.ok(result);
    }
}
