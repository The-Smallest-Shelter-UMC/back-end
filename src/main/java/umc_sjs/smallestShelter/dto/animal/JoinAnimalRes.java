package umc_sjs.smallestShelter.dto.animal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinAnimalRes {

    private Long anmIdx;

    public JoinAnimalRes(Long anmIdx) {
        this.anmIdx = anmIdx;
    }
}
