package umc_sjs.smallestShelter.dto.getAnimalDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import umc_sjs.smallestShelter.domain.Age;
import umc_sjs.smallestShelter.domain.Gender;
import umc_sjs.smallestShelter.domain.Species;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetAnimalRes {

    private List<GetAnimalDto> animal = new ArrayList<>();
    private int pageNumber;

    public GetAnimalRes(List<GetAnimalDto> getAnimalDtoList, int pageNumber) {
        this.animal = getAnimalDtoList;
        this.pageNumber = pageNumber;
    }
}
