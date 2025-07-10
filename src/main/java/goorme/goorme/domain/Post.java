package goorme.goorme.domain;

import goorme.goorme.login.memberService.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="POST_TB")
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name="host_id")
    private Member host;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants = new ArrayList<>();
    public void addParticipation(Participant participant) {
        participants.add(participant);
    }

    @Column
    private String title;
    @Column
    private String name;

    @Column
    private int partNum;

    @Column
    private String storename;

    @Column
    private String storeadd;

    @Column(name = "meet_date")
    private String meetDate;

    @Column(name = "cerated_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "last_date")
    private String lastDate;

    @Column(name = "open_link")
    private String openLink;

    @Column
    private Integer detail;

    @Column
    private int cruitNum;


}
