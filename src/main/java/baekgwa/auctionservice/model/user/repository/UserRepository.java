package baekgwa.auctionservice.model.user.repository;

import baekgwa.auctionservice.model.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginId(String loginId);

    Boolean existsByLoginId(String loginId);
}
