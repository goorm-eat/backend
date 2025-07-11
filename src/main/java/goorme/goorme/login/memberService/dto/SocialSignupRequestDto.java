package goorme.goorme.login.memberService.dto;

import goorme.goorme.login.memberService.domain.Member;
import goorme.goorme.login.memberService.oauth.naver.NaverUserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
@AllArgsConstructor
public class SocialSignupRequestDto {
    private String provider;     // "kakao", "naver"
    private String providerId;   // 외부 서비스의 유저 ID
    private String email;
    private String nickname;


    public static SocialSignupRequestDto fromNaver(NaverUserResponse naver) {
        return new SocialSignupRequestDto(
                "naver",
                naver.getResponse().getId(),
                naver.getResponse().getEmail(),
                naver.getResponse().getNickname()
        );
    }
}
