package umc_sjs.smallestShelter.dto.getAnimalDetailDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommandAnimalDto {

    private Long recommandAnmIdx;
    private String recommandImgUrl;

    public RecommandAnimalDto(Long recommandAnmIdx, String recommandImgUrl) {
        this.recommandAnmIdx = recommandAnmIdx;
        this.recommandImgUrl = recommandImgUrl;
    }
}
