package baekgwa.auctionservice.model.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {

    NONE("ROLE_NONE"),
    BUYER("ROLE_BUYER"),
    TRADER("ROLE_TRADER"),
    ADMIN("ROLE_ADMIN");

    private final String text;
}
