package umc_sjs.smallestShelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc_sjs.smallestShelter.domain.PrivateMember;

public interface PrivateMemberRepository extends JpaRepository<PrivateMember, Long> {
}
