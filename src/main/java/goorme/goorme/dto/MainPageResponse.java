package goorme.goorme.dto;

import java.util.List;

public class MainPageResponse {
    private String nickname;
    private List<PostDetailDto> myJoinedPosts;
    private List<PostDetailDto> recentPosts;

    public MainPageResponse(String nickname, List<PostDetailDto> myJoinedPosts, List<PostDetailDto> recentPosts) {
        this.nickname = nickname;
        this.myJoinedPosts = myJoinedPosts;
        this.recentPosts = recentPosts;
    }
}
