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
public class FavoriteAnimal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favoriteAnimal_idx")
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "privateMember_idx")
    private PrivateMember privateMember;

    @ManyToOne
    @JoinColumn(name = "animal_idx")
    private Animal animal;
}
