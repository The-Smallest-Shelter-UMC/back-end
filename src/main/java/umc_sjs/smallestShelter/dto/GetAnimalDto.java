package umc_sjs.smallestShelter.dto;

import lombok.Getter;
import lombok.Setter;
import umc_sjs.smallestShelter.domain.Gender;
import umc_sjs.smallestShelter.domain.Species;

import java.util.List;

@Getter
@Setter
public class GetAnimalDto {

    private String name;
    private String imgUrl;
    private Species species;
    private Gender gender;
    private Boolean isAdopted;

}
