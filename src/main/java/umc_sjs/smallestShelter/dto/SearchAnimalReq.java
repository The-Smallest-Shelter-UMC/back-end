package umc_sjs.smallestShelter.dto;

import lombok.Getter;
import lombok.Setter;
import umc_sjs.smallestShelter.domain.Gender;
import umc_sjs.smallestShelter.domain.Species;

@Getter
@Setter
public class SearchAnimalReq {

    private Species species;
    private Gender gender;
    private String age;
    private Boolean isAdopted;
}
