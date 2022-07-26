package umc_sjs.smallestShelter;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import umc_sjs.smallestShelter.domain.AnimalIllness;
import umc_sjs.smallestShelter.domain.Illness;
import umc_sjs.smallestShelter.domain.OrganizationMember;
import umc_sjs.smallestShelter.dto.JoinAnimalReq;
import umc_sjs.smallestShelter.dto.JoinAnimalRes;
import umc_sjs.smallestShelter.domain.Animal;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@NoArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalRepository animalRepository;

    @ResponseBody
    @PostMapping("/animal/join")
    public JoinAnimalRes joinAnimal(@RequestBody JoinAnimalReq joinAnimalReq){

        Animal joinAnimal = new Animal();

        joinAnimal.setName(joinAnimalReq.getName());
        joinAnimal.setAge(joinAnimalReq.getAge());
        joinAnimal.setGender(joinAnimalReq.getGender());
        joinAnimal.setSpecies(joinAnimalReq.getSpecies());
        joinAnimal.setMainImgUrl(joinAnimalReq.getMainImgUrl());
        joinAnimal.setIsAdopted(joinAnimalReq.getIsAdopted());
        joinAnimal.setSocialization(joinAnimalReq.getSocialization());
        joinAnimal.setSeparation(joinAnimalReq.getSeparation());
        joinAnimal.setToilet(joinAnimalReq.getToilet());
        joinAnimal.setBark(joinAnimalReq.getBark());
        joinAnimal.setBite(joinAnimalReq.getBite());

        System.out.println("======================================");

        OrganizationMember findOrganizationMember = animalRepository.findOrganizationMember(joinAnimalReq.getUserIdx());
        joinAnimal.modifyOrganizationMember(findOrganizationMember);

        List<String> illnessList = joinAnimalReq.getIllnessList();

        Long saveAnimalIdx = animalService.saveAnimal(joinAnimal, illnessList);

        return new JoinAnimalRes(saveAnimalIdx);
    }
}
