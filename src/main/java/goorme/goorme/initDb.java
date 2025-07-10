package goorme.goorme;

import goorme.goorme.login.memberService.domain.Member;
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

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {

            Member member = new Member();
            member.setNickname("test1");
            member.setLoginss("test");
            member.setPassword("test");
            member.setCnt(1);
            em.persist(member);
        }

        public void dbInit2() {

            Member member = new Member();
            member.setNickname("test2");
            member.setCnt(2);
            em.persist(member);
        }
    }
}