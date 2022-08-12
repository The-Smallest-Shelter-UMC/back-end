package umc_sjs.smallestShelter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.domain.*;
import umc_sjs.smallestShelter.dto.animal.AdoptAnimalRes;
import umc_sjs.smallestShelter.dto.animal.LikeAnimalRes;
import umc_sjs.smallestShelter.dto.animal.ModifyAnimalReq;
import umc_sjs.smallestShelter.dto.animal.SearchAnimalReq;
import umc_sjs.smallestShelter.dto.animal.getAnimalDto.GetAnimalRes;
import umc_sjs.smallestShelter.repository.AnimalRepository;
import umc_sjs.smallestShelter.repository.PostRepository;
import umc_sjs.smallestShelter.response.BaseException;
import umc_sjs.smallestShelter.response.BaseResponse;
import umc_sjs.smallestShelter.response.BaseResponseStatus;

import javax.persistence.NoResultException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final PostRepository postRepository;

    public Long saveAnimal(Animal animal, List<String> illnessNameList) {

        Long animalIdx = animalRepository.saveAnimal(animal, illnessNameList);
        return animalIdx;
    }

    public GetAnimalRes getAnimals(int page, GetAnimalRes getAnimalRes) throws BaseException{
        try {
            GetAnimalRes animalRes = animalRepository.getAnimals(page, getAnimalRes);
            return animalRes;
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }


    public Animal getAnimal(Long anmIdx) throws BaseException{
        try {
            Animal findAnimal = animalRepository.findAnimalById(anmIdx);
            return findAnimal;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(BaseResponseStatus.NON_EXISTING_ANIMAL);
        }
    }

    public AdoptAnimalRes adoptAnimal(Long anmIdx) throws BaseException{

        try {
            AdoptAnimalRes adoptAnimalRes = animalRepository.setIsAdopt(anmIdx);
            return adoptAnimalRes;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(BaseResponseStatus.NON_EXISTING_ANIMAL);
        }

    }

    public LikeAnimalRes likeAnimal(Long userIdx, Long animalIdx, LikeAnimalRes likeAnimalRes) {

        LikeAnimalRes returnLikeAnimalRes = animalRepository.setFavoriteAnimal(userIdx, animalIdx, likeAnimalRes);

        return returnLikeAnimalRes;
    }

    public void deleteAnimal(Long animal_id) throws BaseException {
        try {
            Animal findAnimal = getAnimal(animal_id);
            animalRepository.deleteAnimal(findAnimal);
        } catch (NoResultException e) {
            throw new BaseException(BaseResponseStatus.NON_EXISTING_ANIMAL);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    //회의 후 User쪽 service와 repository로 바꿀 것
    public User findUser(Long userIdx) throws BaseException{
        try {
            User findUser = animalRepository.findUser(userIdx);
            if (findUser == null) {
                throw new BaseException(BaseResponseStatus.NON_EXISTING_USER);
            }
            return findUser;
        } catch (BaseException e) {
            throw new BaseException(e.getStatus());
        }catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public GetAnimalRes searchAnimal(Integer page, SearchAnimalReq searchAnimalReq, GetAnimalRes getAnimalRes) throws BaseException{
        try {
            GetAnimalRes returnGetAnimalRes = animalRepository.searchAnimal(page, searchAnimalReq, getAnimalRes);
            return returnGetAnimalRes;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public Long modifyAnimal(Long anmIdx, ModifyAnimalReq modifyAnimalReq) {

        Animal findAnimal = animalRepository.findAnimalById(anmIdx);

        if (modifyAnimalReq.getName() != null) {
            findAnimal.setName(modifyAnimalReq.getName());
        }
        if (modifyAnimalReq.getYear() != null) {
            findAnimal.getAge().setYear(modifyAnimalReq.getYear());
        }
        if (modifyAnimalReq.getMonth() != null) {
            findAnimal.getAge().setMonth(modifyAnimalReq.getMonth());
        }
        if (modifyAnimalReq.getGender() != null) {
            findAnimal.setGender(modifyAnimalReq.getGender());
        }
        if (modifyAnimalReq.getSpecies() != null) {
            findAnimal.setSpecies(modifyAnimalReq.getSpecies());
        }
        if (modifyAnimalReq.getMainImgUrl() != null) {
            findAnimal.setMainImgUrl(modifyAnimalReq.getMainImgUrl());
        }
        if (modifyAnimalReq.getSocialization() != null) {
            findAnimal.setSocialization(modifyAnimalReq.getSocialization());
        }
        if (modifyAnimalReq.getSeparation() != null) {
            findAnimal.setSeparation(modifyAnimalReq.getSeparation());
        }
        if (modifyAnimalReq.getToilet() != null) {
            findAnimal.setToilet(modifyAnimalReq.getToilet());
        }
        if (modifyAnimalReq.getBark() != null) {
            findAnimal.setBark(modifyAnimalReq.getBark());
        }
        if (modifyAnimalReq.getBite() != null) {
            findAnimal.setBite(modifyAnimalReq.getBite());
        }
        if (modifyAnimalReq.getIllnessList() != null) {
            List<String> illnessList = modifyAnimalReq.getIllnessList();
            for (String illnessName : illnessList) {
                Illness illness = new Illness(illnessName);
                illness.modifyAnimal(findAnimal);
            }
        }

        System.out.println("modifyAnimalReq = " + modifyAnimalReq.getIsGuessed());
        findAnimal.getAge().setIsGuessed(modifyAnimalReq.getIsGuessed());

        return findAnimal.getIdx();
    }
}
