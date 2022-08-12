package umc_sjs.smallestShelter.animal.animalDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdoptAnimalRes {

    private Long animalIdx;
    private boolean isAdopted;

    public AdoptAnimalRes(Long animalIdx, boolean isAdopted) {
        this.animalIdx = animalIdx;
        this.isAdopted = isAdopted;
    }
}
