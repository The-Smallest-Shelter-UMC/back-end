package umc_sjs.smallestShelter.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class OrganizationMember {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organizationMember_idx")
    private Long idx;

    private String name;
    private String userName;
    private String password;

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
