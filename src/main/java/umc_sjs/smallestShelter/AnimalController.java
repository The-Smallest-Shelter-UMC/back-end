package umc_sjs.smallestShelter;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc_sjs.smallestShelter.domain.OrganizationMember;
import umc_sjs.smallestShelter.dto.GetAnimalDto;
import umc_sjs.smallestShelter.dto.JoinAnimalReq;
import umc_sjs.smallestShelter.dto.JoinAnimalRes;
import umc_sjs.smallestShelter.domain.Animal;

import java.util.ArrayList;
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

    @GetMapping("/animal/animals")
    @ResponseBody
    public List<GetAnimalDto> getAnimalsRes(@RequestParam int page) {

        List<Animal> animalList = animalService.getAnimals(page);
        List<GetAnimalDto> animalDtoList = new ArrayList<>();

        for (Animal animal : animalList) {
            GetAnimalDto animalDto = new GetAnimalDto();
            animalDto.setName(animal.getName());
            animalDto.setImgUrl(animal.getMainImgUrl());
            animalDto.setGender(animal.getGender());
            animalDto.setSpecies(animal.getSpecies());
            animalDto.setIsAdopted(animal.getIsAdopted());
            animalDtoList.add(animalDto);
        }

        return animalDtoList;
    }


}
