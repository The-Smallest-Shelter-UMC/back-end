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
}
