package goorme.goorme.repository;

import goorme.goorme.domain.Participant;
import goorme.goorme.domain.Post;
import goorme.goorme.login.memberService.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    boolean existsByPostAndMember(Post post, Member member);
    List<Participant> findByMember(Member member);
    @Query("SELECT p.post FROM Participant p WHERE p.member = :member ORDER BY p.post.createdDate DESC")
    List<Post> findPostsByMember(@Param("member") Member member, Pageable pageable);
}
