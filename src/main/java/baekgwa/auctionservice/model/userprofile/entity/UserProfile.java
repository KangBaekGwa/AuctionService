package baekgwa.auctionservice.model.userprofile.entity;

import baekgwa.auctionservice.model.BaseEntity;
import baekgwa.auctionservice.model.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile extends BaseEntity {
    @Id
    private Long userId;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserGrade grade;

    @Builder
    private UserProfile(User user, String name, String nickName, String email, String phone,
            UserGrade grade) {
        this.user = user;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.phone = phone;
        this.grade = grade;
    }

    public static UserProfile createNewUserProfile(User user, String name, String nickName, String email, String phone){
        return UserProfile
                .builder()
                .user(user)
                .name(name)
                .nickName(nickName)
                .email(email)
                .phone(phone)
                .grade(UserGrade.BRONZE)
                .build();
    }

    public void updateGrade(UserGrade grade) {
        this.grade = grade;
    }

    public void updateUserProfile(String nickName, String name, String email, String phone) {
        this.nickName = nickName;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
