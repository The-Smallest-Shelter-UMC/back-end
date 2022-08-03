package umc_sjs.smallestShelter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import umc_sjs.smallestShelter.config.auth.PrincipalDetails;
import umc_sjs.smallestShelter.dto.user.*;
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

    @GetMapping("/auth/private/animals/{userIdx}") // 마이페이지 관심동물 - 개인
    public GetAnimalsRes privateAnimals(@RequestParam int page, @PathVariable Long userIdx, Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        if (principalDetails.getUser().getIdx() == userIdx) {
            return userService.privateAnimals(page, userIdx);
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

    @GetMapping("/auth/organization/animals/{userIdx}") // 마이페이지 등록동물 - 단체
    public GetAnimalsRes organizationAnimals(@RequestParam int page, @PathVariable Long userIdx, Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        if (principalDetails.getUser().getIdx() == userIdx) {
            return userService.organizationAnimals(page, userIdx);
        } else {
            return null;
        }
    }

    @PatchMapping("/auth/private/{userIdx}") // 회원정보수정 - 개인
    public String updatePrivate(@PathVariable Long userIdx, @RequestBody PatchUserReq patchUserReq, Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        if (principalDetails.getUser().getIdx() == userIdx) {
            return "userIdx : " + userService.updatePrivate(userIdx, patchUserReq);
        } else {
            return null;
        }
    }

    @PatchMapping("/auth/organization/{userIdx}") // 회원정보수정 - 단체
    public String updateOrganization(@PathVariable Long userIdx, @RequestBody PatchUserReq patchUserReq, Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        if (principalDetails.getUser().getIdx() == userIdx) {
            return "userIdx : " + userService.updateOrganization(userIdx, patchUserReq);
        } else {
            return null;
        }
    }

    @DeleteMapping("/auth/out/{userIdx}") // 회원탈퇴
    public String outUser(@PathVariable Long userIdx, Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        if (principalDetails.getUser().getIdx() == userIdx) {
            userService.outUser(userIdx);
            return "회원 탈퇴가 완료되었습니다.";
        } else {
            return null;
        }
    }

}
