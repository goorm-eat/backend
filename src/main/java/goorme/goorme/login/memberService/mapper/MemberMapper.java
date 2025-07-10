package goorme.goorme.login.memberService.mapper;


import goorme.goorme.login.memberService.domain.Member;
import goorme.goorme.login.memberService.dto.SocialSignupRequestDto;

public class MemberMapper {
    public static Member toEntity(SocialSignupRequestDto dto) {
        return Member.builder()
                .provider(dto.getProvider())
                .providerId(dto.getProviderId())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .role("ROLE_USER")
                .build();
    }
}
