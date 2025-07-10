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
    private String provider;
    private String providerId;
    private String nickname;
    private String name;
    private String mobileNumber;
    private String email;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants = new ArrayList<>();

    public void addParticipation(Participant participation) {
        participants.add(participation);
    }

    @OneToOne(mappedBy="host", fetch=FetchType.LAZY)
    private Post participantHost;

    @PrePersist
    protected void onCreate() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}
