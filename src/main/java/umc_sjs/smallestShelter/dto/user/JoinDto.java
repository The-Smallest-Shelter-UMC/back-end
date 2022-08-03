package umc_sjs.smallestShelter.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;
import umc_sjs.smallestShelter.domain.OrganizationName;
import umc_sjs.smallestShelter.domain.Role;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
public class JoinDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String password;
    private String name;
    private String username;
    private String phoneNumber;
    private String profileImgUrl;
    private String email;
    private String address;
    @Nullable
    private OrganizationName organizationName;
    private Role role;
    @CreationTimestamp
    private Timestamp createDate;

}
