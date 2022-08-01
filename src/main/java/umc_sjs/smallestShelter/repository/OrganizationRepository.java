package umc_sjs.smallestShelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc_sjs.smallestShelter.domain.OrganizationMember;

public interface OrganizationRepository extends JpaRepository<OrganizationMember, Long> {
    OrganizationMember findByUserName(String username);
}
