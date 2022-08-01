package umc_sjs.smallestShelter.dto.getAnimalDetailDto;

import lombok.Getter;
import lombok.Setter;
import umc_sjs.smallestShelter.domain.*;
import umc_sjs.smallestShelter.dto.getAnimalDetailDto.IllnessDto;
import umc_sjs.smallestShelter.dto.getAnimalDetailDto.PostDto;
import umc_sjs.smallestShelter.dto.getAnimalDetailDto.RecommandAnimalDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetAnimalDetailRes {

    private String name;
    private String mainImgUrl;
    private Species species;
    private Age age;
    private Gender gender;
    private Boolean isAdopted;
    //private Boolean isOrganization;
    private OrganizationName organizationName;
    private String phoneNumber;
    private String address;

    private List<IllnessDto> Illness = new ArrayList<>();

    private List<PostDto> post = new ArrayList<>();
    private List<RecommandAnimalDto> recommandAnimal = new ArrayList<>();
}
