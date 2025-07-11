package goorme.goorme.login.memberService.domain;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import goorme.goorme.domain.Participant;
import goorme.goorme.domain.Post;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String name;
    private LocalDateTime createdAt;
    private int cnt;
    private String token;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants = new ArrayList<>();


    public void addParticipation(Participant participation) {
        participants.add(participation);
    }

    @OneToOne(mappedBy="host", fetch=FetchType.LAZY)
    private Post participantHost;


}
