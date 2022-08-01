package umc_sjs.smallestShelter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.domain.Post;
import umc_sjs.smallestShelter.dto.getAnimalDto.GetAnimalDto;
import umc_sjs.smallestShelter.dto.getAnimalDto.GetAnimalRes;
import umc_sjs.smallestShelter.repository.AnimalRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;

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
        List<Post> postList = animalRepository.findPostById(anmIdx);
        return postList;
    }


}
