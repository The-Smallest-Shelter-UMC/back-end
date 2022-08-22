package umc_sjs.smallestShelter.animal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import umc_sjs.smallestShelter.domain.FavoriteAnimal;

import java.util.List;

public interface FavoriteAnimalRepository extends JpaRepository<FavoriteAnimal, Long> {
    Page<FavoriteAnimal> findByLikeUserIdx(Long likeUserIdx, Pageable pageable);
}
