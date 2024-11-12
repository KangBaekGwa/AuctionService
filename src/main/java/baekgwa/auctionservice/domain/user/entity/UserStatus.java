package baekgwa.auctionservice.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserStatus {

    ACTIVE("정상 회원"),
    BLOCKED("차단 회원"),
    WITHDRAWN("탈퇴 회원");

    private final String text;
}
