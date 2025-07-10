package goorme.goorme.login.memberService.jwt;

import goorme.goorme.login.memberService.domain.Member;
import goorme.goorme.login.memberService.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, MemberRepository memberRepository) {
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            try {
                Claims claims = jwtUtil.parseToken(token);
                String userEmail = claims.getSubject();
                System.out.println("JWT Claims subject: " + userEmail);

                List<Member> members = memberRepository.findAllByEmail(userEmail);
                if (members.isEmpty()) {
                    // 인증 실패 처리 (예: 로그 남기거나 다음 필터로 넘김)
                } else {
                    Member member = members.get(0); // 첫번째 멤버 선택
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(member, null, List.of());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                System.out.println("JWT parsing failed: " + e.getMessage());
            }
        }
        else {
            System.out.println("No Bearer token found in Authorization header");
        }

        filterChain.doFilter(request, response);
    }
}

