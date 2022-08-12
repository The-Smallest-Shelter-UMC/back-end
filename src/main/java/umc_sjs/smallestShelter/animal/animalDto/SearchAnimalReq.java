package umc_sjs.smallestShelter.animal.animalDto;

import lombok.Getter;
import lombok.Setter;
import umc_sjs.smallestShelter.domain.AgeBoundary;
import umc_sjs.smallestShelter.domain.Gender;
import umc_sjs.smallestShelter.domain.Species;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class SearchAnimalReq {

    @Enumerated(EnumType.STRING)
    private Species species;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private AgeBoundary ageBoundary;
    private Boolean isAdopted;
}
