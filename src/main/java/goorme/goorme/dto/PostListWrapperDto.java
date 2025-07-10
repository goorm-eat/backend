package goorme.goorme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostListWrapperDto {
    private List<PostDto> posts;
    private int count;
    public PostListWrapperDto(List<PostDto> posts) {
        this.posts = posts;
        this.count = posts.size(); // 리스트 길이로 count 계산
    }

    public List<PostDto> getPosts() {
        return posts;
    }

    public int getCount() {
        return count;
    }
}
