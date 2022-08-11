package umc_sjs.smallestShelter.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long idx;

    @Column(nullable = false, unique = true)
    private String username;
    private String name;
    private String password;
    private String phoneNumber;
    private String profileImgUrl;
    private String email;
    private String address;

    @Enumerated(EnumType.STRING)
    @Nullable
    private OrganizationName organizationName;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    @OneToMany(mappedBy = "uploadUser")
    private List<Animal> uploadAnimalList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "likeUser")
    private List<FavoriteAnimal> favoriteAnimalList = new ArrayList<>();

    @CreationTimestamp
    private Timestamp createDate;

}
