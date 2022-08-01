package umc_sjs.smallestShelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc_sjs.smallestShelter.domain.OrganizationMember;
import umc_sjs.smallestShelter.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);

}
