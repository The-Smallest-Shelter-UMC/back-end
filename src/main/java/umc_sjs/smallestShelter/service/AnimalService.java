package umc_sjs.smallestShelter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.domain.Post;
import umc_sjs.smallestShelter.dto.animal.AdoptAnimalRes;
import umc_sjs.smallestShelter.dto.animal.LikeAnimalRes;
import umc_sjs.smallestShelter.dto.animal.getAnimalDto.GetAnimalRes;
import umc_sjs.smallestShelter.repository.AnimalRepository;
import umc_sjs.smallestShelter.repository.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final PostRepository postRepository;

    public Long saveAnimal(Animal animal, List<String> illnessNameList) {

        Long animalIdx = animalRepository.saveAnimal(animal, illnessNameList);
        return animalIdx;
    }

    public GetAnimalRes getAnimals(int page, GetAnimalRes getAnimalRes) {
        GetAnimalRes animalRes = animalRepository.getAnimals(page, getAnimalRes);
        return animalRes;
    }


    public Animal getAnimal(Long anmIdx) {
        Animal findAnimal = animalRepository.findAnimalById(anmIdx);
        return findAnimal;
    }

    public List<Post> getAnimalPost(Long anmIdx) {
        List<Post> postList = postRepository.findPostById(anmIdx);
        return postList;
    }


    public AdoptAnimalRes adoptAnimal(Long anmIdx) {

        AdoptAnimalRes adoptAnimalRes = animalRepository.setIsAdopt(anmIdx);
        return adoptAnimalRes;

    }

    public LikeAnimalRes likeAnimal(Long userIdx, Long animalIdx, LikeAnimalRes likeAnimalRes) {

        LikeAnimalRes returnLikeAnimalRes = animalRepository.setFavoriteAnimal(userIdx, animalIdx, likeAnimalRes);

        return returnLikeAnimalRes;
    }
}
