package umc_sjs.smallestShelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc_sjs.smallestShelter.domain.OrganizationMember;

public interface OrganizationRepository  extends JpaRepository<OrganizationMember, Long> {
}
