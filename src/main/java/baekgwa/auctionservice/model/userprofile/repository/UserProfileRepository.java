package baekgwa.auctionservice.model.userprofile.repository;

import baekgwa.auctionservice.model.userprofile.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Boolean existsByNickNameOrEmailOrPhone(String nickName, String email, String phone);
}