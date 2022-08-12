package umc_sjs.smallestShelter.animal.animalDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import umc_sjs.smallestShelter.domain.Age;
import umc_sjs.smallestShelter.domain.Gender;
import umc_sjs.smallestShelter.domain.Species;

@Data
@Builder
@NoArgsConstructor
public class AnimalRes {

    private Long animalIdx;
    private String mainImgUrl;
    private String name;
    private Species species;
    private Gender gender;
    private Boolean isAdopted;
    private Age age;

    public AnimalRes(Long animalIdx, String mainImgUrl, String name, Species species, Gender gender, Boolean isAdopted, Age age) {
        this.animalIdx = animalIdx;
        this.mainImgUrl = mainImgUrl;
        this.name = name;
        this.species = species;
        this.gender = gender;
        this.isAdopted = isAdopted;
        this.age = age;
    }
}
