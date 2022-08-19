package umc_sjs.smallestShelter.animal;

import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import umc_sjs.smallestShelter.post.PostService;
import umc_sjs.smallestShelter.animal.animalDto.*;
import umc_sjs.smallestShelter.animal.animalDto.getAnimalDetailDto.GetAnimalDetailRes;
import umc_sjs.smallestShelter.animal.animalDto.getAnimalDetailDto.IllnessDto;
import umc_sjs.smallestShelter.animal.animalDto.getAnimalDetailDto.PostDto;
import umc_sjs.smallestShelter.animal.animalDto.getAnimalDetailDto.RecommandAnimalDto;
import umc_sjs.smallestShelter.animal.animalDto.getAnimalDto.GetAnimalRes;
import umc_sjs.smallestShelter.config.auth.PrincipalDetails;
import umc_sjs.smallestShelter.domain.*;
import umc_sjs.smallestShelter.response.BaseException;
import umc_sjs.smallestShelter.response.BaseResponse;
import umc_sjs.smallestShelter.user.UserRepository;
import umc_sjs.smallestShelter.user.UserService;

import java.util.List;

import static umc_sjs.smallestShelter.response.BaseResponseStatus.*;
import static umc_sjs.smallestShelter.response.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalRepository animalRepository;
    private final PostService postService;

    //유저 레포지토리
    private final UserService userService;

    /**
     * 동물 등록 API
     * @param joinAnimalReq
     * @return JoinAnimalRes
     */
    //@ResponseBody
    @PostMapping("/auth/organization/animal/join")
    public BaseResponse<JoinAnimalRes> joinAnimal(@RequestBody JoinAnimalReq joinAnimalReq) {

        if (joinAnimalReq.getUserIdx() == null) {
            return new BaseResponse<>(REQUEST_ERROR);
        }
        if (joinAnimalReq.getName().isEmpty()) {
            return new BaseResponse<>(ANIMAL_EMPTY_NAME);
        }
        if (joinAnimalReq.getYear() == null) {
            return new BaseResponse<>(ANIMAL_EMPTY_YEAR);
        }
        if (joinAnimalReq.getMonth() == null) {
            return new BaseResponse<>(ANIMAL_EMPTY_MONTH);
        }
        if (joinAnimalReq.getGender() == null) {
            return new BaseResponse<>(ANIMAL_EMPTY_GENDER);
        }
        if (joinAnimalReq.getSpecies() == null) {
            return new BaseResponse<>(ANIMAL_EMPTY_SPECIES);
        }
        if (joinAnimalReq.getMainImgUrl() == null) {
            return new BaseResponse<>(ANIMAL_EMPTY_MAINIMG);
        }
        if (joinAnimalReq.getSocialization() == null) {
            return new BaseResponse<>(ANIMAL_EMPTY_SOCIALIZATION);
        }
        if (joinAnimalReq.getSeparation() == null) {
            return new BaseResponse<>(ANIMAL_EMPTY_SEPARATION);
        }
        if (joinAnimalReq.getToilet() == null) {
            return new BaseResponse<>(ANIMAL_EMPTY_TOILET);
        }
        if (joinAnimalReq.getBark() == null) {
            return new BaseResponse<>(ANIMAL_EMPTY_BARK);
        }
        if (joinAnimalReq.getBite() == null) {
            return new BaseResponse<>(ANIMAL_EMPTY_BITE);
        }

        Animal joinAnimal = new Animal();
        joinAnimal.setName(joinAnimalReq.getName());
        joinAnimal.setAge(new Age(joinAnimalReq.getYear(), joinAnimalReq.getMonth(), joinAnimalReq.getIsGuessed()));
        joinAnimal.setGender(joinAnimalReq.getGender());
        joinAnimal.setSpecies(joinAnimalReq.getSpecies());
        joinAnimal.setMainImgUrl(joinAnimalReq.getMainImgUrl());
        joinAnimal.setSocialization(joinAnimalReq.getSocialization());
        joinAnimal.setSeparation(joinAnimalReq.getSeparation());
        joinAnimal.setToilet(joinAnimalReq.getToilet());
        joinAnimal.setBark(joinAnimalReq.getBark());
        joinAnimal.setBite(joinAnimalReq.getBite());
        try {
            User findUser = animalService.findUser(joinAnimalReq.getUserIdx());
            //userRepository.findById(joinAnimalReq.getUserIdx())
            joinAnimal.modifyUploadUser(findUser);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
        List<String> illnessList = joinAnimalReq.getIllnessList();
        Long saveAnimalIdx = animalService.saveAnimal(joinAnimal, illnessList);

        return new BaseResponse<>(new JoinAnimalRes(saveAnimalIdx));
    }

    /**
     * 동물 수정 버튼 API
     * @param anmIdx
     * @return
     */
    @GetMapping("/auth/organization/animal/{animal_id}")
    public BaseResponse<ModifyAnimalFormRes> modifyAnimalForm(@PathVariable(name = "animal_id") Long anmIdx) {

        Animal findAnimal;

        try {
            findAnimal = animalService.getAnimal(anmIdx);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        ModifyAnimalFormRes modifyAnimalFormRes = new ModifyAnimalFormRes();

        modifyAnimalFormRes.setName(findAnimal.getName());
        modifyAnimalFormRes.setYear(findAnimal.getAge().getYear());
        modifyAnimalFormRes.setMonth(findAnimal.getAge().getMonth());
        modifyAnimalFormRes.setGuessed(findAnimal.getAge().getIsGuessed());
        modifyAnimalFormRes.setGender(findAnimal.getGender());
        modifyAnimalFormRes.setMainImgUrl(findAnimal.getMainImgUrl());
        modifyAnimalFormRes.setSocialization(findAnimal.getSocialization());
        modifyAnimalFormRes.setSeparation(findAnimal.getSeparation());
        modifyAnimalFormRes.setToilet(findAnimal.getToilet());
        modifyAnimalFormRes.setBark(findAnimal.getBark());
        modifyAnimalFormRes.setBite(findAnimal.getBite());
        List<Illness> illnessList = findAnimal.getIllnessList();
        for (Illness illness : illnessList) {
            modifyAnimalFormRes.getIllnessList().add(illness.getName());
        }

        return new BaseResponse<>(modifyAnimalFormRes);
    }

    /**
     * 동물 수정 API
     * @param anmIdx
     * @param modifyAnimalReq
     * @return
     */
    @PatchMapping("/auth/organization/animal/{animal_id}")
    public BaseResponse<String> modifyAnimal(@PathVariable(name = "animal_id") Long anmIdx, @RequestBody(required = false) ModifyAnimalReq modifyAnimalReq) {

        if (modifyAnimalReq.getUserIdx() == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }

        System.out.println("modifyAnimalReq = " + modifyAnimalReq.getYear());




        Long resultAnmIdx = animalService.modifyAnimal(anmIdx, modifyAnimalReq);
        return new BaseResponse<>("anmIdx : " + resultAnmIdx);

    }

    /**
     * 동물 리스트 조회 API
     * @param page
     * @return GetAnimalRes
     */
    @GetMapping("/animals")
    //@ResponseBody
    public BaseResponse<GetAnimalRes> getAnimals(@RequestParam Integer page) {
        if (page == null) {
            return new BaseResponse<>(EMPTY_URL_VALUE);
        }

        try {
            GetAnimalRes getAnimalRes = new GetAnimalRes();
            GetAnimalRes animalRes = animalService.getAnimals(page, getAnimalRes);
            return new BaseResponse<>(animalRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 동물 삭제 API
     * @param animal_id
     */
    @DeleteMapping("/auth/organization/animal/{animal_id}")
    public BaseResponse<String> deleteAnimal(@PathVariable Long animal_id, Authentication authentication) {

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        /*try {
            principalDetails = (PrincipalDetails) authentication.getPrincipal();
            principalDetails.
        } catch (Exception e) {
            return new BaseResponse<>(INVALID_JWT);
        }*/

        if (principalDetails.getUser().getRole() == Role.PRIVATE) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        if (principalDetails.getUser().getRole() == Role.PRIVATE) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        try {
            animalService.deleteAnimal(animal_id);
            String result = animal_id + " 번 동물이 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        } catch (TokenExpiredException e) {
            return new BaseResponse<>(INVALID_JWT);
        }
    }

    /**
     * 동물 상세 조회 API
     * @param animal_id
     * @return GetAnimalDetailRes
     */
    @GetMapping("/animal/{animal_id}")
    public BaseResponse<GetAnimalDetailRes> getAnimalDetail(@PathVariable Long animal_id) {

        try {
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
            getAnimalDetailRes.setSocialization(animal.getSocialization());
            getAnimalDetailRes.setSeparation(animal.getSeparation());
            getAnimalDetailRes.setToilet(animal.getToilet());
            getAnimalDetailRes.setBark(animal.getBark());
            getAnimalDetailRes.setBite(animal.getBite());

            List<Illness> illnessList = animal.getIllnessList();

            for (Illness illness : illnessList) {
                IllnessDto illnessDto = new IllnessDto();
                illnessDto.setIllnessName(illness.getName());
                getAnimalDetailRes.getIllness().add(illnessDto);
            }

            List<Post> postList = postService.getAnimalPost(animal_id);
            for (Post post : postList) {
                PostDto postDto = new PostDto();
                postDto.setPostIdx(post.getIdx());
                postDto.setPostImgUrl(post.getImgUrl());
                getAnimalDetailRes.getPost().add(postDto);
            }

            List<RecommandAnimalDto> recommendAnimals = animalRepository.getRecommendAnimals(animal_id);
            getAnimalDetailRes.setRecommandAnimal(recommendAnimals);

            return new BaseResponse<>(getAnimalDetailRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 동물 검색 API
     * @param page
     * @param searchAnimalReq
     * @return GetAnimalRes
     */
    @PostMapping("/animal/search")
    public BaseResponse<GetAnimalRes> searchAnimal(@RequestParam Integer page, @RequestBody SearchAnimalReq searchAnimalReq) {

        try {
            GetAnimalRes getAnimalRes = animalService.searchAnimal(page, searchAnimalReq, new GetAnimalRes());
            return new BaseResponse<>(getAnimalRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        } catch (Exception e) {
            return new BaseResponse<>(EMPTY_URL_VALUE);
        }
    }

    /**
     * 입양 버튼 API
     * @param animal_id
     * @return AdoptAnimalRes
     */
    @PatchMapping("/auth/organization/animal/adopt")
    public BaseResponse<AdoptAnimalRes> adoptAnimal(@RequestParam(value = "animal_id") Long animal_id) {

        if (animal_id == null) {
            return new BaseResponse<>(EMPTY_URL_VALUE);
        }

        try {
            AdoptAnimalRes adoptAnimalRes = animalService.adoptAnimal(animal_id);
            return new BaseResponse<>(adoptAnimalRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
}
    }

/**
 * 관심 동물 - 즐겨찾기 API
 * @param user_id
 * @param animal_id
 * @return LikeAnimalRes
 */
@PatchMapping("/auth/private/animal/like")
public BaseResponse<LikeAnimalRes> likeAnimal(@RequestParam(value = "user_id") Long user_id, @RequestParam(value = "animal_id") Long animal_id) {

        LikeAnimalRes likeAnimalRes = animalService.likeAnimal(user_id, animal_id, new LikeAnimalRes());
        return new BaseResponse<>(likeAnimalRes);
    }
}
