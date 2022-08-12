package umc_sjs.smallestShelter.animal.animalDto;

import lombok.Getter;
import lombok.Setter;
import umc_sjs.smallestShelter.domain.Gender;
import umc_sjs.smallestShelter.domain.Species;
import umc_sjs.smallestShelter.domain.Status;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ModifyAnimalReq {

    private Long userIdx;
    private String name;
    private Integer year;
    private Integer month;
    private boolean isGuessed;
    private Gender gender;
    private Species species;
    private String mainImgUrl;
    private Status socialization;
    private Status separation;
    private Status toilet;
    private Status bark;
    private Status bite;
    private List<String> illnessList = new ArrayList<>();

    public boolean getIsGuessed() {
        return isGuessed;
    }

    public void setIsGuessed(boolean guessed) {
        isGuessed = guessed;
    }
}
