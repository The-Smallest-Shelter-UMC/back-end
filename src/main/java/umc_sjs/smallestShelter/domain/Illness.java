package umc_sjs.smallestShelter.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Illness {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "illness_idx")
    private Long idx;

    private String name;

    @ManyToOne
    @JoinColumn(name = "animal_idx")
    private Animal animal;

    public Illness(String name) {
        this.name = name;
    }

    //연관관계 편의 메서드
    public void modifyAnimal(Animal animal) {
        this.animal = animal;
        animal.getIllnessList().add(this);
    }
}
