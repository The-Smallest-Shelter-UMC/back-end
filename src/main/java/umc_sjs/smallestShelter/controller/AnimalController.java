package umc_sjs.smallestShelter.controller;

import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import umc_sjs.smallestShelter.domain.*;
import umc_sjs.smallestShelter.dto.animal.*;
import umc_sjs.smallestShelter.dto.animal.getAnimalDetailDto.GetAnimalDetailRes;
import umc_sjs.smallestShelter.dto.animal.getAnimalDetailDto.IllnessDto;
import umc_sjs.smallestShelter.dto.animal.getAnimalDetailDto.PostDto;
import umc_sjs.smallestShelter.dto.animal.getAnimalDetailDto.RecommandAnimalDto;
import umc_sjs.smallestShelter.dto.animal.getAnimalDto.GetAnimalRes;
import umc_sjs.smallestShelter.service.*;
import umc_sjs.smallestShelter.repository.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
//@NoArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalRepository animalRepository;

    /**
     * 동물 등록 API
     * @param joinAnimalReq
     * @return JoinAnimalRes
     */
    //@ResponseBody
    @PostMapping("/auth/organization/animal/join")
    public JoinAnimalRes joinAnimal(@RequestBody JoinAnimalReq joinAnimalReq) {

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

        User findUser = animalRepository.findUser(joinAnimalReq.getUserIdx());
        joinAnimal.modifyUploadUser(findUser);

        List<String> illnessList = joinAnimalReq.getIllnessList();

        Long saveAnimalIdx = animalService.saveAnimal(joinAnimal, illnessList);

        return new JoinAnimalRes(saveAnimalIdx);
    }

    /**
     * 동물 리스트 조회 API
     * @param page
     * @return GetAnimalRes
     */
    @GetMapping("/animals")
    //@ResponseBody
    public GetAnimalRes getAnimals(@RequestParam int page) {

        GetAnimalRes getAnimalRes = new GetAnimalRes();

        GetAnimalRes animalRes = animalService.getAnimals(page, getAnimalRes);

        return animalRes;
    }

    /**
     * 동물 삭제 API
     * @param animal_id
     */
    @DeleteMapping("/auth/organization/animal/{animal_id}")
    public void deleteAnimal(@PathVariable Long animal_id) {
        animalRepository.deleteAnimal(animal_id);
    }

    /**
     * 동물 상세 조회 API
     * @param animal_id
     * @return GetAnimalDetailRes
     */
    @GetMapping("/animals/{animal_id}")
    //@ResponseBody
    public GetAnimalDetailRes getAnimalDetail(@PathVariable Long animal_id) {

        GetAnimalDetailRes getAnimalDetailRes = new GetAnimalDetailRes();

        Animal animal = animalService.getAnimal(animal_id);
        getAnimalDetailRes.setName(animal.getName());
        getAnimalDetailRes.setMainImgUrl(animal.getMainImgUrl());
        getAnimalDetailRes.setSpecies(animal.getSpecies());
        getAnimalDetailRes.setAge(animal.getAge());
        getAnimalDetailRes.setGender(animal.getGender());
        getAnimalDetailRes.setIsAdopted(animal.getIsAdopted());
        getAnimalDetailRes.setOrganizationName(animal.getUploadUser().getOrganizationName());
        getAnimalDetailRes.setOrganizationMemberId(animal.getUploadUser().getUsername());
        getAnimalDetailRes.setOrganizationMemberImgUrl(animal.getUploadUser().getProfileImgUrl());
        getAnimalDetailRes.setPhoneNumber(animal.getUploadUser().getPhoneNumber());
        getAnimalDetailRes.setAddress(animal.getUploadUser().getAddress());

        List<Illness> illnessList = animal.getIllnessList();

        for (Illness illness : illnessList) {
            IllnessDto illnessDto = new IllnessDto();
            illnessDto.setIllnessName(illness.getName());
            getAnimalDetailRes.getIllness().add(illnessDto);
        }

        List<Post> postList = animalService.getAnimalPost(animal_id);
        for (Post post : postList) {
            PostDto postDto = new PostDto();
            postDto.setPostIdx(post.getIdx());
            postDto.setPostImgUrl(post.getImgUrl());
            getAnimalDetailRes.getPost().add(postDto);
        }

        List<RecommandAnimalDto> recommendAnimals = animalRepository.getRecommendAnimals(animal_id);
        getAnimalDetailRes.setRecommandAnimal(recommendAnimals);

//        System.out.println("authentication = " + authentication);

//        List<Animal> recommandAnimalList = animalService.getRecommandAnimal(anmIdx);

        return getAnimalDetailRes;
    }

    /**
     * 동물 검색 API
     * @param page
     * @param searchAnimalReq
     * @return GetAnimalRes
     */
    @PostMapping("/search")
    public GetAnimalRes searchAnimal(@RequestParam int page, @RequestBody SearchAnimalReq searchAnimalReq) {
        GetAnimalRes getAnimalRes = animalRepository.searchAnimal(page, searchAnimalReq, new GetAnimalRes());
        return getAnimalRes;
    }

    /**
     * 입양 버튼 API
     * @param animal_id
     * @return AdoptAnimalRes
     */
    @PatchMapping("/auth/organization/adopt")
    public AdoptAnimalRes adoptAnimal(@RequestParam Long animal_id) {
        AdoptAnimalRes adoptAnimalRes = animalService.adoptAnimal(animal_id);

        return adoptAnimalRes;
    }

    /**
     * 관심 동물 - 즐겨찾기 API
     * @param user_id
     * @param animal_id
     * @return LikeAnimalRes
     */
    @PatchMapping("/auth/private/like")
    public LikeAnimalRes likeAnimal(@RequestParam Long user_id, @RequestParam Long animal_id) {

        LikeAnimalRes likeAnimalRes = animalService.likeAnimal(user_id, animal_id, new LikeAnimalRes());
        return likeAnimalRes;
    }
}
