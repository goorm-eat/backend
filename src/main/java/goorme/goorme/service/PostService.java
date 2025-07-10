package goorme.goorme.service;

import goorme.goorme.domain.Participant;
import goorme.goorme.domain.Post;
import goorme.goorme.login.memberService.domain.Member;
import goorme.goorme.login.memberService.repository.MemberRepository;
import goorme.goorme.repository.ParticipantRepository;
import goorme.goorme.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final ParticipantRepository  participantRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }
    public Post findOne(String postId){
        return (Post) postRepository.findByName(postId);
    }
    public long count() {
        return postRepository.count();
    }
    @Transactional
    public void joinPost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물 없음"));

        Member member = memberRepository.findByNickname(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        boolean alreadyJoined = participantRepository.existsByPostAndMember(post, member);
        if (alreadyJoined) {
            throw new IllegalStateException("이미 참여한 사용자입니다.");
        }

        if (post.getParticipants().size() >= post.getCruitNum()) {
            throw new IllegalStateException("모집 마감");
        }

        Participant participation = new Participant(post, member);
        post.addParticipation(participation);
        member.addParticipation(participation);
        participantRepository.save(participation);
    }
    public List<Post> findPostsByMember(Member member) {
        List<Participant> participations = participantRepository.findByMember(member);
        return participations.stream()
                .map(Participant::getPost)
                .collect(Collectors.toList());
    }

    public List<Post> findPostsByParticipant(Member member, int limit) {
        return participantRepository.findPostsByMember(member, PageRequest.of(0, limit, Sort.by("post.createdDate").descending()));
    }

    public List<Post> findRecentPosts(int limit) {
        return postRepository.findAll(PageRequest.of(0, limit, Sort.by("createdDate").descending())).getContent();
    }


}
