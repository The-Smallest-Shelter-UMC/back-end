package umc_sjs.smallestShelter.dto.getAnimalDto;

import lombok.Getter;
import lombok.Setter;
import umc_sjs.smallestShelter.domain.Age;
import umc_sjs.smallestShelter.domain.Gender;
import umc_sjs.smallestShelter.domain.Species;

@Getter
@Setter
public class GetAnimalDto {

    private Long animalIdx;
    private String name;
    private String imgUrl;
    private Species species;
    private Gender gender;
    private Boolean isAdopted;
    private Age age;

    public GetAnimalDto(Long animalIdx, String name, String imgUrl, Species species, Gender gender, Boolean isAdopted, Age age) {
        this.animalIdx = animalIdx;
        this.name = name;
        this.imgUrl = imgUrl;
        this.species = species;
        this.gender = gender;
        this.isAdopted = isAdopted;
        this.age = age;
    }
}
