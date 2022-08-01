package umc_sjs.smallestShelter.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class PrivateMember {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "privateMember_idx")
    private Long idx;

    private String password;
    private String name;
    private String userName;
    private String phoneNumber;
    private String profileImgUrl;
    private String email;
    private String address;

    @OneToMany(mappedBy = "privateMember")
    private List<FavoriteAnimal> favoriteAnimalList;

    @CreationTimestamp
    private Timestamp createDate;

    private Role role;


}
