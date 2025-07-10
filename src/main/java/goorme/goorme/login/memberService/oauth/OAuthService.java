package goorme.goorme.login.memberService.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import goorme.goorme.login.memberService.domain.Member;
import goorme.goorme.login.memberService.dto.LoginResponseDto;
import goorme.goorme.login.memberService.dto.SocialSignupRequestDto;
import goorme.goorme.login.memberService.jwt.JwtUtil;
import goorme.goorme.login.memberService.oauth.naver.NaverUserResponse;
import goorme.goorme.login.memberService.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthService {

        @Value("${naver.client-id}")
        private String clientId;

        @Value("${naver.client-secret}")
        private String clientSecret;

        @Value("${naver.redirect-uri}")
        private String redirectUri;

        private final ObjectMapper objectMapper = new ObjectMapper();
        private final MemberRepository memberRepository;
        private final JwtUtil jwtUtil;

        // 🔹 단순 redirect URL 생성 (트랜잭션 필요 없음)
        public void redirectToNaver(HttpServletResponse response) throws IOException {
            String state = UUID.randomUUID().toString();

            String naverUrl = "https://nid.naver.com/oauth2.0/authorize?response_type=code"
                    + "&client_id=" + clientId
                    + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)
                    + "&state=" + state;

            response.sendRedirect(naverUrl);
        }

        // 🔹 실제 인증 처리 (DB 저장 포함 → 트랜잭션 필요)
        @Transactional
        public LoginResponseDto processNaverLogin(String code, String state) {
            try {
                // 1. 액세스 토큰 요청
                String tokenUrl = "https://nid.naver.com/oauth2.0/token"
                        + "?grant_type=authorization_code"
                        + "&client_id=" + clientId
                        + "&client_secret=" + clientSecret
                        + "&code=" + code
                        + "&state=" + state;

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> tokenResponse = restTemplate.getForEntity(tokenUrl, String.class);
                System.out.println("🟡 [네이버 토큰 응답] =====================");
                System.out.println(tokenResponse.getBody());
                System.out.println("=======================================");

                JsonNode tokenJson = objectMapper.readTree(tokenResponse.getBody());

                // 🔐 access_token 없을 때 디버깅
                if (!tokenJson.has("access_token")) {
                    throw new RuntimeException("토큰 응답에 access_token이 없습니다: " + tokenJson.toString());
                }
                String accessToken = tokenJson.get("access_token").asText();

                // 2. 사용자 정보 요청
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + accessToken);
                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<String> profileRes = restTemplate.exchange(
                        "https://openapi.naver.com/v1/nid/me",
                        HttpMethod.GET,
                        entity,
                        String.class
                );


                NaverUserResponse userResponse = objectMapper.readValue(profileRes.getBody(), NaverUserResponse.class);

                // 3. 내부 DTO로 변환
                SocialSignupRequestDto dto = SocialSignupRequestDto.fromNaver(userResponse);

                // 4. DB 저장 (중복 체크 로직은 추가 가능)
                Member member = dto.toEntity();
                memberRepository.save(member);

                // 5. JWT 생성
                String jwt = jwtUtil.createToken(member.getEmail());
                return new LoginResponseDto(jwt, member.getNickname());

            } catch (Exception e) {
                throw new RuntimeException("네이버 로그인 실패: " + e.getMessage());
            }
        }
    }


