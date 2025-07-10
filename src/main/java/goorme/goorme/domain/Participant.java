package goorme.goorme.domain;

import goorme.goorme.login.memberService.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@Entity
    public class Participant {
        @Id
        @GeneratedValue
        private Long id;
        @ManyToOne(fetch = FetchType.LAZY)
        private Member member;
        @ManyToOne(fetch = FetchType.LAZY)
        private Post post;
        private LocalDateTime joinedAt;
        public Participant(Post post, Member member) {
        this.post = post;
        this.member = member;
        this.joinedAt = LocalDateTime.now();
    }

    public Participant() {

    }
}
