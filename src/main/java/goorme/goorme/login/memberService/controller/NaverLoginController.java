package goorme.goorme.login.memberService.controller;

import goorme.goorme.domain.GlobalCounter;
import goorme.goorme.login.memberService.domain.Member;
import goorme.goorme.login.memberService.dto.LoginResponseDto;
import goorme.goorme.login.memberService.jwt.JwtUtil;
import goorme.goorme.login.memberService.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NaverLoginController {

    private final MemberRepository memberRepository;
    GlobalCounter test=new GlobalCounter();
    @PostMapping("/login")
    //public void redirectToNaver(HttpServletResponse response) throws IOException {
    //    oAuthService.redirectToNaver(response);
    //}
    public ResponseEntity<LoginResponseDto> login(@RequestParam String id,
                                                  @RequestParam String pass) {

        Optional<Member> member= memberRepository.findByNicknameAndPassword(id, pass);
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.createToken("eteefe", member.get().getId());
        LoginResponseDto result = new LoginResponseDto(member.get().getToken(), member.get().getNickname());
        return ResponseEntity.ok(result);
    }

}
