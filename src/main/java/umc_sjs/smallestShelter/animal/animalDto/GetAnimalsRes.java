package umc_sjs.smallestShelter.animal.animalDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAnimalsRes {

    long totalPage;
    List<AnimalRes> animalResList;
}
