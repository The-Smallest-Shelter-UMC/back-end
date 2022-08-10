package umc_sjs.smallestShelter.dto.animal;

import lombok.Getter;
import lombok.Setter;
import umc_sjs.smallestShelter.domain.Gender;
import umc_sjs.smallestShelter.domain.Species;
import umc_sjs.smallestShelter.domain.Status;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ModifyAnimalFormRes {

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
}
