package umc_sjs.smallestShelter.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

import static javax.persistence.FetchType.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Animal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_idx")
    private Long idx;

    private String name;
    private String age;
    private Gender gender;
    private Species species;
    private String mainImgUrl;
    private Boolean isAdopted;
    private Status socialization;
    private Status separation;
    private Status toilet;
    private Status bark;
    private Status bite;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "organization_idx")
    private Organization organization;

    @OneToMany(mappedBy = "animal")
    private List<AnimalIllness> animalIllnessList;

    @OneToMany(mappedBy = "animal")
    private List<Post> postList;

    @CreationTimestamp
    private Timestamp createDate;
}
