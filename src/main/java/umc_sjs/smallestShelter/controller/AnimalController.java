package umc_sjs.smallestShelter.controller;

import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import umc_sjs.smallestShelter.domain.*;
import umc_sjs.smallestShelter.dto.*;
import umc_sjs.smallestShelter.dto.getAnimalDetailDto.GetAnimalDetailRes;
import umc_sjs.smallestShelter.dto.getAnimalDetailDto.IllnessDto;
import umc_sjs.smallestShelter.dto.getAnimalDetailDto.PostDto;
import umc_sjs.smallestShelter.dto.getAnimalDetailDto.RecommandAnimalDto;
import umc_sjs.smallestShelter.dto.getAnimalDto.GetAnimalRes;
import umc_sjs.smallestShelter.dto.SearchAnimalReq;
import umc_sjs.smallestShelter.service.*;
import umc_sjs.smallestShelter.repository.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
//@NoArgsConstructor
@RequestMapping("/animal")
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalRepository animalRepository;

    //@ResponseBody
    @PostMapping("/join")
    public JoinAnimalRes joinAnimal(@RequestBody JoinAnimalReq joinAnimalReq){

        Animal joinAnimal = new Animal();

        joinAnimal.setName(joinAnimalReq.getName());
        joinAnimal.setAge(new Age(joinAnimalReq.getYear(), joinAnimalReq.getMonth(), joinAnimalReq.isGuessed()));
        joinAnimal.setGender(joinAnimalReq.getGender());
        joinAnimal.setSpecies(joinAnimalReq.getSpecies());
        joinAnimal.setMainImgUrl(joinAnimalReq.getMainImgUrl());
        joinAnimal.setIsAdopted(joinAnimalReq.getIsAdopted());
        joinAnimal.setSocialization(joinAnimalReq.getSocialization());
        joinAnimal.setSeparation(joinAnimalReq.getSeparation());
        joinAnimal.setToilet(joinAnimalReq.getToilet());
        joinAnimal.setBark(joinAnimalReq.getBark());
        joinAnimal.setBite(joinAnimalReq.getBite());

        OrganizationMember findOrganizationMember = animalRepository.findOrganizationMember(joinAnimalReq.getUserIdx());
        joinAnimal.modifyOrganizationMember(findOrganizationMember);

        List<String> illnessList = joinAnimalReq.getIllnessList();

        Long saveAnimalIdx = animalService.saveAnimal(joinAnimal, illnessList);

        return new JoinAnimalRes(saveAnimalIdx);
    }

    @GetMapping("/animals")
    //@ResponseBody
    public GetAnimalRes getAnimals(@RequestParam int page) {

        GetAnimalRes getAnimalRes = new GetAnimalRes();

        GetAnimalRes animalRes = animalService.getAnimals(page, getAnimalRes);

        return animalRes;
    }

    @DeleteMapping("/{anmIdx}")
    public void deleteAnimal(@PathVariable Long anmIdx) {
        animalRepository.deleteAnimal(anmIdx);
    }

    @GetMapping("/{anmIdx}")
    //@ResponseBody
    public GetAnimalDetailRes getAnimalDetail(@PathVariable Long anmIdx) {

        GetAnimalDetailRes getAnimalDetailRes = new GetAnimalDetailRes();

        Animal animal = animalService.getAnimal(anmIdx);
        getAnimalDetailRes.setName(animal.getName());
        getAnimalDetailRes.setMainImgUrl(animal.getMainImgUrl());
        getAnimalDetailRes.setSpecies(animal.getSpecies());
        getAnimalDetailRes.setAge(animal.getAge());
        getAnimalDetailRes.setGender(animal.getGender());
        getAnimalDetailRes.setIsAdopted(animal.getIsAdopted());
        getAnimalDetailRes.setOrganizationName(animal.getOrganizationMember().getOrganizationName());
        getAnimalDetailRes.setPhoneNumber(animal.getOrganizationMember().getPhoneNumber());
        getAnimalDetailRes.setAddress(animal.getOrganizationMember().getAddress());

        List<Illness> illnessList = animal.getIllnessList();

        for (Illness illness : illnessList) {
            IllnessDto illnessDto = new IllnessDto();
            illnessDto.setIllnessName(illness.getName());
            getAnimalDetailRes.getIllness().add(illnessDto);
        }

        List<Post> postList = animalService.getAnimalPost(anmIdx);
        for (Post post : postList) {
            PostDto postDto = new PostDto();
            postDto.setPostIdx(post.getIdx());
            postDto.setPostImgUrl(post.getImgUrl());
            getAnimalDetailRes.getPost().add(postDto);
        }

        List<RecommandAnimalDto> recommendAnimals = animalRepository.getRecommendAnimals(anmIdx);
        getAnimalDetailRes.setRecommandAnimal(recommendAnimals);

//        System.out.println("authentication = " + authentication);

//        List<Animal> recommandAnimalList = animalService.getRecommandAnimal(anmIdx);

        return getAnimalDetailRes;
    }

    @PostMapping("/search")
    public GetAnimalRes searchAnimal(@RequestParam int page, @RequestBody SearchAnimalReq searchAnimalReq) {
        GetAnimalRes getAnimalRes = animalRepository.searchAnimal(page, searchAnimalReq, new GetAnimalRes());
        return getAnimalRes;
    }

    @PatchMapping("/adopt")
    public AdoptAnimalRes adoptAnimal(@RequestParam Long anmIdx) {
        AdoptAnimalRes adoptAnimalRes = animalService.adoptAnimal(anmIdx);

        return adoptAnimalRes;
    }

}
