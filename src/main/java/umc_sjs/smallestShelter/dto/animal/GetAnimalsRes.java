package umc_sjs.smallestShelter.dto.animal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import umc_sjs.smallestShelter.dto.animal.AnimalRes;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAnimalsRes {

    List<AnimalRes> animalResList;
}
