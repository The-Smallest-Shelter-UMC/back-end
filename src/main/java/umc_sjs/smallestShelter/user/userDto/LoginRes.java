package umc_sjs.smallestShelter.user.userDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import umc_sjs.smallestShelter.domain.OrganizationName;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRes {

    private Long userIdx;
    private String name;
    private String role;
    @Enumerated(EnumType.STRING)
    private OrganizationName organizationName;
    private Integer profileImgUrl;

}
