package umc_sjs.smallestShelter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.domain.Illness;
import umc_sjs.smallestShelter.domain.Post;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;

    public Long saveAnimal(Animal animal, List<String> illnessNameList) {

        Long animalIdx = animalRepository.saveAnimal(animal, illnessNameList);
        return animalIdx;
    }

    public List<Animal> getAnimals(int page) {
        List<Animal> animalList = animalRepository.getAnimals(page);
        return animalList;
    }


    public Animal getAnimal(Long anmIdx) {
        Animal findAnimal = animalRepository.findAnimalById(anmIdx);
        return findAnimal;
    }

    public List<Post> getAnimalPost(Long anmIdx) {
        List<Post> postList = animalRepository.findPostById(anmIdx);
        return postList;
    }

//    public List<Animal> getRecommandAnimal(Long anmIdx) {
//
//
//    }


}
