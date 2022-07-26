package umc_sjs.smallestShelter.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class OrganizationMember {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organizationMember_idx")
    private Long idx;

    private String password;
    private String name;
    private String userName;

    @Enumerated(EnumType.STRING)
    private OrganizationName organizationName;

    private String phoneNumber;
    private String profileImgUrl;
    private String email;
    private String address;

    @OneToMany(mappedBy = "organizationMember")
    private List<Animal> animalList = new ArrayList<>();

    @CreationTimestamp
    private Timestamp createDate;

    @Enumerated(EnumType.STRING)
    private Role role;
}
