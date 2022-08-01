package umc_sjs.smallestShelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc_sjs.smallestShelter.dto.JoinDto;

public interface JoinDtoRepository extends JpaRepository<JoinDto, Long> {
    JoinDto findByUserName(String username);

}
