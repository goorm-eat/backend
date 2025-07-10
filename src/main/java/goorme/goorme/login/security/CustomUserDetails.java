package goorme.goorme.login.security;

import goorme.goorme.login.memberService.domain.Member;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

    public class CustomUserDetails implements UserDetails {
        private final Member member;

        public CustomUserDetails(Member member) {
            this.member = member;
        }

        public Member getMember() {
            return member;
        }

        public String getNickname() {
            return member.getNickname();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.emptyList(); // 권한 없음 처리
        }

        @Override
        public String getPassword() {
            return null; // 소셜 로그인 시 패스워드 없음 허용
        }

        @Override
        public String getUsername() {
            return member.getNickname();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

