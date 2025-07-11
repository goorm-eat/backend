package goorme.goorme;

import goorme.goorme.login.memberService.domain.Member;
import goorme.goorme.login.memberService.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class initDb {

    private final InitService initService;
    private final MemberRepository memberRepository;
    @PostConstruct
    public void init() {
        memberRepository.deleteAll();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public void dbInit2() {
            Member member = new Member();
            member.setNickname("test2");
            member.setCnt(2);
            em.persist(member);
        }
    }
}