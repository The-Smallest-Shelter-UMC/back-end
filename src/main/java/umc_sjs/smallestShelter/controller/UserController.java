package umc_sjs.smallestShelter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import umc_sjs.smallestShelter.config.auth.PrincipalDetails;
import umc_sjs.smallestShelter.dto.animal.GetAnimalsRes;
import umc_sjs.smallestShelter.dto.user.*;
import umc_sjs.smallestShelter.response.BaseException;
import umc_sjs.smallestShelter.response.BaseResponse;
import umc_sjs.smallestShelter.service.UserService;

import static umc_sjs.smallestShelter.response.BaseResponseStatus.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join") // 회원가입
    public BaseResponse<String> join(@RequestBody JoinDto joinDto) throws IOException {
        if(joinDto.getName() == null){
            return new BaseResponse<>(USERS_EMPTY_NAME);
        }
        if(joinDto.getUsername() == null){
            return new BaseResponse<>(USERS_EMPTY_USERNAME);
        }
        if(joinDto.getEmail() == null){
            return new BaseResponse<>(USERS_EMPTY_EMAIL);
        }
        if(joinDto.getPassword() == null){
            return new BaseResponse<>(USERS_EMPTY_PASSWORD);
        }
        if(joinDto.getPhoneNumber() == null){
            return new BaseResponse<>(USERS_EMPTY_PHONENUMBER);
        }
        if(joinDto.getProfileImgUrl() == null){
            return new BaseResponse<>(USERS_EMPTY_PROFILEIMG);
        }
        if(joinDto.getAddress() == null){
            return new BaseResponse<>(USERS_EMPTY_ADDRESS);
        }
        if(joinDto.getRole() == null) {
            return new BaseResponse<>(USERS_EMPTY_ROLE);
        }
        if((joinDto.getRole().toString() != "ORGANIZATION") && (joinDto.getRole().toString() != "PRIVATE")) {
            System.out.println("role" + joinDto.getRole().toString());
            return new BaseResponse<>(INVALID_ENUM_VALUE);
        }
        if (joinDto.getRole().toString() == "ORGANIZATION") {
            if (joinDto.getOrganizationName() == null) {
                return new BaseResponse<>(USERS_EMPTY_ORGANIZATIONNAME);
            }
        }

        // 정규 표현
//        if(!isRegexEmail(postUserReq.getEmail())){
//            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
//        }
//        if(!isRegexPassword(postUserReq.getPassword())){
//            return new BaseResponse<>(POST_USERS_INVALID_PASSWORD);
//        }
//        if(!isRegexName(postUserReq.getName())){
//            return new BaseResponse<>(POST_USERS_INVALID_NAME);
//        }
//        if(!isRegexNickName(postUserReq.getNickName())){
//            return new BaseResponse<>(POST_USERS_INVALID_NICKNAME);
//        }

        try{
            userService.join(joinDto);
            return new BaseResponse<>("회원가입 완료");
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/auth/private/{userIdx}") // 마이페이지 - 개인
    public BaseResponse<GetPrivatePageRes> privatePage(@PathVariable Long userIdx, Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        if (principalDetails.getUser().getIdx() != userIdx) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        try{
            GetPrivatePageRes getPrivatePageRes = userService.privatePage(userIdx);
            return new BaseResponse<>(getPrivatePageRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/auth/private/animals/{userIdx}") // 마이페이지 관심동물 - 개인
    public BaseResponse<GetAnimalsRes> privateAnimals(@RequestParam int page, @PathVariable Long userIdx, Authentication authentication) throws IOException {

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();


        if (principalDetails.getUser().getIdx() != userIdx) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        try{
            GetAnimalsRes getAnimalsRes = userService.privateAnimals(page, userIdx);
            return new BaseResponse<>(getAnimalsRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/auth/organization/{userIdx}") // 마이페이지 - 단체
    public BaseResponse<GetOrganizationPageRes> organizationPage(@PathVariable Long userIdx, Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        if (principalDetails.getUser().getIdx() != userIdx) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        try{
            GetOrganizationPageRes getOrganizationPageRes = userService.organizationPage(userIdx);
            return new BaseResponse<>(getOrganizationPageRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/auth/organization/animals/{userIdx}") // 마이페이지 등록동물 - 단체
    public BaseResponse<GetAnimalsRes> organizationAnimals(@RequestParam int page, @PathVariable Long userIdx, Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        if (principalDetails.getUser().getIdx() != userIdx) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        try{
            GetAnimalsRes getAnimalsRes = userService.organizationAnimals(page, userIdx);
            return new BaseResponse<>(getAnimalsRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PatchMapping("/auth/private/{userIdx}") // 회원정보수정 - 개인
    public BaseResponse<String> updatePrivate(@PathVariable Long userIdx, @RequestBody PatchUserReq patchUserReq, Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        if (principalDetails.getUser().getIdx() != userIdx) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        try{
            Long userId = userService.updatePrivate(userIdx, patchUserReq);
            return new BaseResponse<>("userIdx : " + userId);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PatchMapping("/auth/organization/{userIdx}") // 회원정보수정 - 단체
    public BaseResponse<String> updateOrganization(@PathVariable Long userIdx, @RequestBody PatchUserReq patchUserReq, Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        if (principalDetails.getUser().getIdx() != userIdx) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        try{
            Long userId = userService.updateOrganization(userIdx, patchUserReq);
            return new BaseResponse<>("userIdx : " + userId);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @DeleteMapping("/auth/out/{userIdx}") // 회원탈퇴
    public BaseResponse<String> outUser(@PathVariable Long userIdx, Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        if (principalDetails.getUser().getIdx() != userIdx) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        try{
            userService.outUser(userIdx);
            return new BaseResponse<>("회원 탈퇴가 완료되었습니다.");
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
