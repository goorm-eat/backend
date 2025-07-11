package goorme.goorme.dto;

import goorme.goorme.domain.Post;
import goorme.goorme.login.memberService.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostDetailDto {
    private Member host;
    private String title;
    private String name;
    private int partNum;
    private String storename;
    private String storeadd;
    private String meetDate;
    private LocalDateTime createdDate;
    private String lastDate;
    private String openLink;
    private Integer detail;
    private int cruitNum;

    public PostDetailDto(Post post){
        this.host = host;
        this.title = title;
        this.name = name;
        this.partNum = partNum;
        this.storename = storename;
        this.storeadd = storeadd;
        this.meetDate = meetDate;
        this.lastDate = lastDate;
        this.openLink = openLink;
        this.detail = detail;
        this.cruitNum = cruitNum;
    }
}
