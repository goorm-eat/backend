package goorme.goorme.controller;

import goorme.goorme.domain.Post;
import goorme.goorme.dto.MainPageResponse;
import goorme.goorme.dto.PostDetailDto;
import goorme.goorme.dto.PostDto;
import goorme.goorme.dto.PostListWrapperDto;
import goorme.goorme.login.memberService.domain.Member;
import goorme.goorme.login.memberService.repository.MemberRepository;
import goorme.goorme.login.security.CustomUserDetails;
import goorme.goorme.repository.PostRepository;
import goorme.goorme.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class mainController {
    private final PostService postService;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/postlist")
    public Results<PostListWrapperDto> postlist() {
        List<Post> posts = postService.findAll();
        List<PostDto> dtos = posts.stream()
                .map(PostDto::new) // 생성자로 바로 DTO 변환
                .collect(Collectors.toList());
        return new Results<>(new PostListWrapperDto(dtos));
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @GetMapping("/post/{id}")
    public Results<PostDetailDto> postdetail(@PathVariable Long id) {
        Optional<Post> posts = postRepository.findById(id);
        Post postss = posts.orElseThrow(() ->
                new IllegalArgumentException("게시물이 없습니다. id=" + id));
        PostDetailDto collected = new PostDetailDto(postss);
        return new Results<>(collected);
    }


    @Data
    @AllArgsConstructor
    static class Results<T> {
        private T data;
    }
    @PostMapping("/posts/{postId}/join")
    public ResponseEntity<?> joinPost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails user) {
        postService.joinPost(postId, user.getUsername());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/postlist/res")
    public Results<PostListWrapperDto> myPosts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = memberRepository.findByNickname(userDetails.getNickname())
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다"));
        List<Post> posts = postService.findPostsByMember(member);
        List<PostDto> dtos = posts.stream()
                .map(PostDto::new) // 생성자로 바로 DTO 변환
                .collect(Collectors.toList());
        return new Results<>(new PostListWrapperDto(dtos));
    }
    @GetMapping("")
    public Results<MainPageResponse> getMainPageData(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String nickname = userDetails.getNickname();

        // 1. 닉네임
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다"));

        // 2. 내가 참여한 게시글 2개
        List<Post> myJoinedPosts = postService.findPostsByParticipant(member, 2);

        List<PostDetailDto> joinedDtos = myJoinedPosts.stream()
                .map(PostDetailDto::new)
                .collect(Collectors.toList());

        // 3. 전체 게시글 3개
        List<Post> recentPosts = postService.findRecentPosts(3);

        List<PostDetailDto> recentDtos = recentPosts.stream()
                .map(PostDetailDto::new)
                .collect(Collectors.toList());

        MainPageResponse response = new MainPageResponse(nickname, joinedDtos, recentDtos);
        return new Results<>(response);
    }


}
