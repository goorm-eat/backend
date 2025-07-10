package goorme.goorme.login.memberService.repository;


import goorme.goorme.login.memberService.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


    public interface MemberRepository extends JpaRepository<Member, Long> {
        Optional<Member> findByProviderAndProviderId(String provider, String providerId);
        Optional<Member> findByNickname(String nickname);

        Optional<Member> findByEmail(String userEmail);
        List<Member> findAllByEmail(String email);
    }
