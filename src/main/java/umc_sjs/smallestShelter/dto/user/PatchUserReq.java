package umc_sjs.smallestShelter.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchUserReq {

    private String name;
    private String phoneNumber;
    private String address;
    private String email;

}
