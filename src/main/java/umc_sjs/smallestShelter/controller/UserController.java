package umc_sjs.smallestShelter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import umc_sjs.smallestShelter.config.auth.PrincipalDetails;
import umc_sjs.smallestShelter.dto.user.GetOrganizationPageRes;
import umc_sjs.smallestShelter.dto.user.GetPrivatePageRes;
import umc_sjs.smallestShelter.dto.user.JoinDto;
import umc_sjs.smallestShelter.service.UserService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public String join(@RequestBody JoinDto joinDto) throws IOException {
        userService.join(joinDto);
        return "회원가입 완료";
    }

    @GetMapping("/auth/private/{userIdx}") // 마이페이지 - 개인
    public GetPrivatePageRes privatePage(@PathVariable Long userIdx, Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        if (principalDetails.getUser().getIdx() == userIdx) {
            return userService.privatePage(userIdx);
        } else {
            return null;
        }
    }

    @GetMapping("/auth/organization/{userIdx}") // 마이페이지 - 단체
    public GetOrganizationPageRes organizationPage(@PathVariable Long userIdx, Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        if (principalDetails.getUser().getIdx() == userIdx) {
            return userService.organizationPage(userIdx);
        } else {
            return null;
        }
    }
}
