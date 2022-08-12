package umc_sjs.smallestShelter.animal.animalDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikeAnimalRes {

    private Long userIdx;
    private Long animalIdx;
    private boolean isLike;

    public LikeAnimalRes(Long userIdx, Long animalIdx, boolean isLike) {
        this.userIdx = userIdx;
        this.animalIdx = animalIdx;
        this.isLike = isLike;
    }
}
