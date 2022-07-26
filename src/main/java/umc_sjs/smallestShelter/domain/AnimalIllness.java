package umc_sjs.smallestShelter.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class AnimalIllness {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animalIllness_idx")
    private Long idx;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "animal_idx")
    private Animal animal;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "illness_idx")
    private Illness illness;

    public AnimalIllness(Illness illness) {
        this.illness = illness;
    }

    //연관관계 편의 메소드
    public void modifyAnimal(Animal animal) {
        this.setAnimal(animal);
        animal.getAnimalIllnessList().add(this);
    }
}
