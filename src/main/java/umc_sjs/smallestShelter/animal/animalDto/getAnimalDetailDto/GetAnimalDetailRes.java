package umc_sjs.smallestShelter.animal.animalDto.getAnimalDetailDto;

import lombok.Getter;
import lombok.Setter;
import umc_sjs.smallestShelter.domain.*;

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
    private OrganizationName organizationName;
    private String organizationMemberId;
    private int organizationMemberImgUrl;
    private String phoneNumber;
    private String address;

    private List<IllnessDto> Illness = new ArrayList<>();

    private List<PostDto> post = new ArrayList<>();
    private List<RecommandAnimalDto> recommandAnimal = new ArrayList<>();
}
