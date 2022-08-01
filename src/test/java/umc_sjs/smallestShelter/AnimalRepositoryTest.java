package umc_sjs.smallestShelter;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.domain.Illness;
import umc_sjs.smallestShelter.dto.getAnimalDetailDto.RecommandAnimalDto;
import umc_sjs.smallestShelter.repository.AnimalRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AnimalRepositoryTest {

    @Autowired
    AnimalRepository animalRepository;

    @Test
    public void findAnimalById() throws Exception{
        //given

        Long anmIdx = 20L;

        //when

        Animal findAnimal = animalRepository.findAnimalById(anmIdx);
        List<Illness> illnessList = findAnimal.getIllnessList();
        for (Illness illness : illnessList) {
            System.out.println("illness = " + illness.getName());

        }

        //then

        Assertions.assertThat(findAnimal.getIllnessList().size()).isEqualTo(3);

    }

    @Test
    public void getRecommandAnimals() throws Exception{
        //given
        Long anmIdx = 3L;
        //when
        List<RecommandAnimalDto> recommendAnimals = animalRepository.getRecommendAnimals(anmIdx);
        //then

        Assertions.assertThat(recommendAnimals)

    }


}