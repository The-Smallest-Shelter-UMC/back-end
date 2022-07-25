package umc_sjs.smallestShelter.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Organization {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organization_idx")
    private Long idx;

    private String password;
    private String name;
    private String userName;
    private OrganizationName organizationName;
    private String phoneNumber;
    private String profileImgUrl;
    private String email;
    private String address;

    @OneToMany(mappedBy = "organization")
    private List<Animal> animalList;

    private Timestamp createDate;
    private Role role;
}
