package goorme.goorme.dto;

import goorme.goorme.domain.Post;
import goorme.goorme.login.memberService.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostDto {
    private int partNum;
    private String title;
    private String storename;
    private String storeadd;
    private String meetDate;
    private int cruitNum;
    private LocalDateTime createdDate;
    private String lastDate;
    public PostDto(Post post) {
        this.partNum = post.getPartNum();
        this.title = post.getTitle();
        this.storename = post.getStorename();
        this.storeadd = post.getStoreadd();
        this.meetDate = post.getMeetDate();
        this.cruitNum = post.getCruitNum();
        this.createdDate = post.getCreatedDate();
        this.lastDate = post.getLastDate();
    }
}
