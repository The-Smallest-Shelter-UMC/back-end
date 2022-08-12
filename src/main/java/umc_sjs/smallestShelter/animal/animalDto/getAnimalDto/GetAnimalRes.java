package umc_sjs.smallestShelter.animal.animalDto.getAnimalDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
