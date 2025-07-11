package goorme.goorme.login.memberService.repository;


import goorme.goorme.login.memberService.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


    public interface MemberRepository extends JpaRepository<Member, Long> {
      Optional<Member> findByNickname(String nickname);
        List<Member> findByCnt(int cnt);
        Optional<Object> findByToken(String token);
    }
