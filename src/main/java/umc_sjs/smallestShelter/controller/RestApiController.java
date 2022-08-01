package umc_sjs.smallestShelter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import umc_sjs.smallestShelter.dto.JoinDto;
import umc_sjs.smallestShelter.service.OrganizationService;
import umc_sjs.smallestShelter.service.PrivateMemberService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final PrivateMemberService privateMemberService;
    private final OrganizationService organizationService;

    @PostMapping("join")
    public String join(JoinDto joinDto) throws IOException {
        if (joinDto.getOrganizationName()==null) {
            privateMemberService.join(joinDto);
        } else {
            organizationService.join(joinDto);
        }
        return "회원가입 완료";
    }
}
