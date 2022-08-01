package umc_sjs.smallestShelter.dto;

import lombok.Getter;
import lombok.Setter;
import umc_sjs.smallestShelter.domain.Age;
import umc_sjs.smallestShelter.domain.Gender;
import umc_sjs.smallestShelter.domain.Species;

@Getter
@Setter
public class GetAnimalRes {

    private Long animalIdx;
    private String name;
    private String imgUrl;
    private Species species;
    private Gender gender;
    private Boolean isAdopted;
    /*private int year;
    private int month;
    private boolean isGuessed;*/
    private Age age;

    /*public GetAnimalRes(Long animalIdx, String name, String imgUrl, Species species, Gender gender, Boolean isAdopted, int year, int month, boolean isGuessed) {
        this.animalIdx = animalIdx;
        this.name = name;
        this.imgUrl = imgUrl;
        this.species = species;
        this.gender = gender;
        this.isAdopted = isAdopted;
        this.year = year;
        this.month = month;
        this.isGuessed = isGuessed;
    }*/

    public GetAnimalRes(Long animalIdx, String name, String imgUrl, Species species, Gender gender, Boolean isAdopted, Age age) {
        this.animalIdx = animalIdx;
        this.name = name;
        this.imgUrl = imgUrl;
        this.species = species;
        this.gender = gender;
        this.isAdopted = isAdopted;
        this.age = age;
    }
}
